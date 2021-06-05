package com.summit.home


import com.summit.android.addfast.app.MyApp
import com.summit.commons.ui.base.BaseFragment
import com.summit.home.databinding.FragmentHomeBinding
import com.summit.home.di.DaggerHomeComponent
import com.summit.home.di.HomeModule


class HomeFragment : BaseFragment<FragmentHomeBinding, HomeViewModel>(
    layoutId = R.layout.fragment_home
) {



    override fun onInitDataBinding() {
        viewBinding.viewModel = viewModel
    }

    override fun onInitDependencyInjection() {
        DaggerHomeComponent
            .builder()
            .coreComponent(MyApp.coreComponent(requireContext()))
            .homeModule(HomeModule(this))
            .build()
            .inject(this)
    }
}