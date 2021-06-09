package com.summit.dynamicfeatures.navhost.nav

import android.os.Bundle
import android.view.View
import androidx.navigation.ui.NavigationUI
import com.summit.android.addfast.app.MyApp
import com.summit.commons.ui.base.BaseFragment
import com.summit.commons.ui.extension.setupWithNavController
import com.summit.core.network.model.departamento.UbicacionModel
import com.summit.dynamicfeatures.navhost.NavHostViewModel
import com.summit.dynamicfeatures.navhost.R
import com.summit.dynamicfeatures.navhost.databinding.FragmentNavBinding
import com.summit.dynamicfeatures.navhost.dialog.SelectPlaceDialog
import com.summit.dynamicfeatures.navhost.nav.di.DaggerNavComponent
import com.summit.dynamicfeatures.navhost.nav.di.NavModule


class NavFragment : BaseFragment<FragmentNavBinding, NavHostViewModel>(
    layoutId = R.layout.fragment_nav
) {


    private val navGraphIds = listOf(
        R.navigation.nav_inicio_graph,
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
        super.onViewCreated(view, savedInstanceState)
        setupToolbar()
        if (savedInstanceState == null) {
            setupMenu()
            setupBottomNavigationBar()

        }
    }

    private fun setupMenu() {
        viewModel.getUserData.observe(viewLifecycleOwner) {
            viewBinding.navView.menu.clear()
            viewBinding.navView.inflateMenu(viewModel.getMenuActual(it))
        }
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
    private fun initDialogSelectPlace(){
        try {
            val newFragment = SelectPlaceDialog()
            newFragment.isCancelable=true
            newFragment.show(
                requireActivity().supportFragmentManager.beginTransaction(),
                "dialog"
            )
        }catch (e: IllegalStateException){
        }
    }
    private fun setupBottomNavigationBar() {
        val navController = viewBinding.navView.setupWithNavController(
            navGraphIds = navGraphIds,
            fragmentManager = childFragmentManager,
            containerId = R.id.am_fragment,
            intent = requireActivity().intent
        )

        navController.observe(
            viewLifecycleOwner
        ) {
            viewModel.navigationControllerChanged(it)
            NavigationUI.setupActionBarWithNavController(requireCompatActivity(), it)
        }
    }

}