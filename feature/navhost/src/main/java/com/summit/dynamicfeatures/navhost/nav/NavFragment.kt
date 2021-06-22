package com.summit.dynamicfeatures.navhost.nav

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.LiveData
import androidx.lifecycle.coroutineScope
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.NavigationUI
import com.summit.android.addfast.app.MyApp
import com.summit.commons.ui.base.BaseFragment
import com.summit.commons.ui.extension.setupWithNavController
import com.summit.core.network.model.departamento.UbicacionModel
import com.summit.core.style.ThemeUtils
import com.summit.dynamicfeatures.navhost.NavHostViewModel
import com.summit.dynamicfeatures.navhost.R
import com.summit.dynamicfeatures.navhost.databinding.FragmentNavBinding
import com.summit.dynamicfeatures.navhost.dialog.SelectPlaceDialog
import com.summit.dynamicfeatures.navhost.menu.ToggleThemeCheckBox
import com.summit.dynamicfeatures.navhost.nav.di.DaggerNavComponent
import com.summit.dynamicfeatures.navhost.nav.di.NavModule
import kotlinx.coroutines.cancel
import javax.inject.Inject


class NavFragment : BaseFragment<FragmentNavBinding, NavHostViewModel>(
    layoutId = R.layout.fragment_nav
) {

    override fun onAttach(context: Context) {
        Log.e("onAttach", "entro aca")
        super.onAttach(context)

    }


    override fun onCreate(savedInstanceState: Bundle?) {
        Log.e("onCreate", "entro aca")
        super.onCreate(savedInstanceState)
    }

    override fun onStart() {
        Log.e("onStart", "entro aca")
        super.onStart()

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        Log.e("onActivityCreated", "entro aca")
        super.onActivityCreated(savedInstanceState)

    }

    override fun onResume() {
        Log.e("onResume", "entro aca")
        super.onResume()

    }

    override fun onDetach() {
        Log.e("onDetach", "entro aca")

        super.onDetach()

    }

    override fun onDestroy() {
        Log.e("onDestroy", "entro aca")
        super.onDestroy()

    }

    override fun onDestroyView() {
        Log.e("onDestroyView", "entro aca")
        super.onDestroyView()
    }

    override fun onStop() {
        Log.e("onStop", "entro aca")
        super.onStop()

    }

    override fun onPause() {
        Log.e("onPause", "entro aca")
        super.onPause()

    }


    private val delayThemeUtils = 0L

    private val navGraphIds = listOf(
        R.navigation.nav_inicio_graph,
        R.navigation.nav_profile_graph,
        R.navigation.nav_postulate_graph
    )

    override fun onInitDependencyInjection() {
        DaggerNavComponent
            .builder()
            .coreComponent(MyApp.coreComponent(requireContext()))
            .navModule(NavModule(this))
            .build()
            .inject(this)
    }

    override fun onInitDataBinding() {
        viewBinding.viewModel = viewModel
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        Log.e("onViewCreated", "entro aca")
        super.onViewCreated(view, savedInstanceState)
        setupToolbar()
        if (savedInstanceState == null) {
            setupMenu()
        }


    }

    override fun onSaveInstanceState(outState: Bundle) {
        Log.e("onSaveInstanceState", "entro aca")
        super.onSaveInstanceState(outState)
    }

    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        Log.e("onViewStateRestored", "entro aca")
        super.onViewStateRestored(savedInstanceState)
        setupMenu()

    }

    private fun setupMenu() {
        if (viewModel.getUserData.value == null) {
            viewModel.getUserData.observe(viewLifecycleOwner) {
                viewBinding.navView.menu.clear()
                viewBinding.navView.inflateMenu(viewModel.getMenuActual(it))
                navController = null
                setupBottomNavigationBar()
            }
        }

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                requireActivity().onBackPressed()
                return false
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun setupToolbar() {
        setHasOptionsMenu(true)
        requireCompatActivity().setSupportActionBar(viewBinding.tollbarMain)
        setupBindingToolbar()
    }

    private fun setupBindingToolbar() {
        viewModel.getUbicacion.observe(this) {
            if (it == null) {
                viewModel.saveUbicacion(UbicacionModel("Puno", "Puno", 0))
            }
        }

        viewBinding.imgIconGps.setOnClickListener {
            initDialogSelectPlace()
        }
        viewBinding.mainUbicacion.setOnClickListener {
            initDialogSelectPlace()
        }
    }

    private fun initDialogSelectPlace() {
        try {
            val newFragment = SelectPlaceDialog()
            newFragment.isCancelable = true
            newFragment.show(
                requireActivity().supportFragmentManager.beginTransaction(),
                "dialog"
            )
        } catch (e: IllegalStateException) {
        }
    }

    private var navController: LiveData<NavController>? = null
    private fun setupBottomNavigationBar() {

        navController = viewBinding.navView.setupWithNavController(
            navGraphIds = navGraphIds,
            fragmentManager = childFragmentManager,
            containerId = R.id.am_fragment,
            intent = requireActivity().intent
        )
        navController?.let {
            it.observe(
                viewLifecycleOwner
            ) {nav->
                viewModel.navigationControllerChanged(nav)
                NavigationUI.setupActionBarWithNavController(requireCompatActivity(), nav)
            }
        }
    }

}