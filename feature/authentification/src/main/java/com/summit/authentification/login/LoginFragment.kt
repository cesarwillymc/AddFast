package com.summit.authentification.login

import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.summit.android.addfast.app.MyApp
import com.summit.authentification.R
import com.summit.authentification.databinding.FragmentLoginBinding
import com.summit.authentification.login.di.DaggerLoginComponent
import com.summit.authentification.login.di.LoginModule
import com.summit.commons.ui.base.BaseFragment

class LoginFragment : BaseFragment<FragmentLoginBinding, LoginViewModel>(
    layoutId = R.layout.fragment_login
) {
    override fun onInitDependencyInjection() {
        DaggerLoginComponent.builder().coreComponent(MyApp.coreComponent(requireContext()))
            .loginModule(LoginModule(this)).build().inject(this)
    }

    override fun onInitDataBinding() {
        viewBinding.viewModel = viewModel
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewBinding.lblInicioSend.setOnClickListener {
            Toast.makeText(requireContext(),"CLicked",Toast.LENGTH_LONG).show()
        }
    }

}