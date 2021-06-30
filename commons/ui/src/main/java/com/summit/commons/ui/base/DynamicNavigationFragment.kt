import android.os.Bundle
import androidx.annotation.IdRes
import androidx.annotation.LayoutRes
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import androidx.navigation.dynamicfeatures.DynamicExtras
import androidx.navigation.dynamicfeatures.DynamicInstallMonitor
import com.google.android.play.core.splitinstall.SplitInstallSessionState
import com.google.android.play.core.splitinstall.model.SplitInstallSessionStatus
import com.summit.commons.ui.base.BaseFragment

abstract class DynamicNavigationFragment<B : ViewDataBinding, M : ViewModel>(
    @LayoutRes
    private val layoutId: Int
) :
    BaseFragment<B, M>(layoutId) {

    private val installMonitor = DynamicInstallMonitor()

    fun navigateWithInastallMonitor(
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