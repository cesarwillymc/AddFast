package com.summit.navhost.nav

import android.os.Bundle
import android.view.View
import androidx.navigation.ui.NavigationUI
import com.summit.android.addfast.app.MyApp
import com.summit.commons.ui.base.BaseFragment
import com.summit.commons.ui.extension.setupWithNavController
import com.summit.navhost.NavHostViewModel
import com.summit.navhost.R
import com.summit.navhost.databinding.FragmentNavBinding
import com.summit.navhost.di.DaggerNavComponent
import com.summit.navhost.di.NavModule


class NavFragment : BaseFragment<FragmentNavBinding, NavHostViewModel>(
    layoutId = R.layout.fragment_nav
) {


    private val navGraphIds = listOf(
        R.navigation.nav_consumer,
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
            viewModel.getMenuActual(it)
        }
    }

    private fun setupToolbar() {
        setHasOptionsMenu(true)
        requireCompatActivity().setSupportActionBar(viewBinding.tollbarMain)
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