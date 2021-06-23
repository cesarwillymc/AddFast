package com.summit.dynamicfeatures.navhost.nav

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import androidx.lifecycle.LiveData
import androidx.navigation.NavController
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
import javax.inject.Inject


class NavFragment : BaseFragment<FragmentNavBinding, NavHostViewModel>(
    layoutId = R.layout.fragment_nav
) {

    @Inject
    lateinit var themeUtils: ThemeUtils

    private val navGraphIds = listOf(
        R.navigation.nav_inicio_graph,
        R.navigation.nav_profile_graph,
        R.navigation.nav_postulate_graph,
        R.navigation.nav_my_add_graph,
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


    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        super.onViewStateRestored(savedInstanceState)

        viewModel.getUserData.value?.let {
            viewBinding.navView.menu.clear()
            viewBinding.navView.inflateMenu(viewModel.getMenuActual(it))
        }
        setupBottomNavigationBar()
    }

    private fun setupMenu() {
        if (viewModel.getUserData.value == null) {
            viewModel.getUserData.observe(viewLifecycleOwner) {
                viewBinding.navView.menu.clear()
                viewBinding.navView.inflateMenu(viewModel.getMenuActual(it))
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
        viewBinding.iconTheme.setOnClickListener {
            it?.let {
                if (it is ToggleThemeCheckBox) {
                    val checked = themeUtils.isDarkTheme(requireContext())
                    it.isChecked = checked
                    themeUtils.setNightMode(!checked, 1000L)
                }
            }

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

    private fun setupBottomNavigationBar() {

       val navController = viewBinding.navView.setupWithNavController(
            navGraphIds = navGraphIds,
            fragmentManager = childFragmentManager,
            containerId = R.id.am_fragment,
            intent = requireActivity().intent
        )
        navController?.let {
            it.observe(
                viewLifecycleOwner
            ) {nav->
                Log.e("setupBottomNav","entri")
                viewModel.navigationControllerChanged(nav)
                NavigationUI.setupActionBarWithNavController(requireCompatActivity(), nav)
            }
        }
    }

}