package com.summit.commons.ui.base

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.IdRes
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import androidx.navigation.dynamicfeatures.DynamicExtras
import androidx.navigation.dynamicfeatures.DynamicInstallMonitor
import com.google.android.play.core.splitinstall.SplitInstallSessionState
import com.google.android.play.core.splitinstall.model.SplitInstallSessionStatus
import javax.inject.Inject

abstract class BaseFragment<B : ViewDataBinding, M : ViewModel>(
    @LayoutRes
    private val layoutId: Int
) : Fragment() {


    @Inject
    lateinit var viewModel: M

    lateinit var viewBinding: B



    abstract fun onInitDependencyInjection()
    abstract fun onInitDataBinding()


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewBinding = DataBindingUtil.inflate(inflater, layoutId, container, false)
        viewBinding.lifecycleOwner = viewLifecycleOwner
        return viewBinding.root
    }


    override fun onAttach(context: Context) {
        super.onAttach(context)
        onInitDependencyInjection()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        onInitDataBinding()
    }


    fun requireCompatActivity(): AppCompatActivity {
        requireActivity()
        val activity = requireActivity()
        if (activity is AppCompatActivity) {
            return activity
        } else {
            throw TypeCastException("Main activity should extend from 'AppCompatActivity'")
        }
    }

    private val installMonitor = DynamicInstallMonitor()

    fun navigateWithInstallMonitor(
        navController: NavController,
        @IdRes destinationId: Int,
        bundle: Bundle? = null
    ) {

        navController.navigate(
            destinationId,
            null,
            null,
            DynamicExtras(installMonitor)
        )

        println("DynamicInstallFragment isInstallRequired: ${installMonitor.isInstallRequired}")

        if (installMonitor.isInstallRequired) {

            installMonitor.status.observe(
                viewLifecycleOwner,
                object : Observer<SplitInstallSessionState> {

                    override fun onChanged(sessionState: SplitInstallSessionState) {

                        when (sessionState.status()) {

                            SplitInstallSessionStatus.INSTALLED -> {
                                // Call navigate again here or after user taps again in the UI:
                                navController.navigate(destinationId, null, null, null)
                            }
                            SplitInstallSessionStatus.REQUIRES_USER_CONFIRMATION -> {
                            }

                            // Handle all remaining states:
                            SplitInstallSessionStatus.FAILED -> {
                            }
                            SplitInstallSessionStatus.CANCELED -> {
                            }
                        }

                        if (sessionState.hasTerminalStatus()) {
                            installMonitor.status.removeObserver(this)
                        }
                    }
                }
            )
        }
    }
}
