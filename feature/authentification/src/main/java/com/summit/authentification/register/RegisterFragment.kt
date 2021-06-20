package com.summit.authentification.register

import android.os.Bundle
import android.view.View
import com.summit.android.addfast.app.MyApp
import com.summit.authentification.R
import com.summit.authentification.databinding.FragmentRegisterBinding
import com.summit.authentification.register.di.DaggerRegisterComponent
import com.summit.authentification.register.di.RegisterModule
import com.summit.commons.ui.base.BaseFragment

class RegisterFragment:BaseFragment<FragmentRegisterBinding,RegisterViewModel>(
    layoutId = R.layout.fragment_register
) {
    override fun onInitDependencyInjection() {
        DaggerRegisterComponent.builder().coreComponent(MyApp.coreComponent(requireContext()))
            .registerModule(RegisterModule(this)).build().inject(this)
    }

    override fun onInitDataBinding() {
        viewBinding.viewModel = viewModel
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }
}