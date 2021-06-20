package com.summit.authentification.login

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.summit.android.addfast.app.MyApp
import com.summit.authentification.R
import com.summit.authentification.databinding.FragmentLoginBinding
import com.summit.authentification.login.di.DaggerLoginComponent
import com.summit.authentification.login.di.LoginModule
import com.summit.commons.ui.base.BaseFragment
import com.summit.core.status.Status

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
        viewBinding.lblInicioSend.buttonSend.setOnClickListener {
            viewModel.sendMessageLogged()
            signInNumberPhone()
        }
    }

    private fun signInNumberPhone() {
        if (viewModel.stateLogin.value == null) {
            viewModel.stateLogin.observe(viewLifecycleOwner) {
                when (it) {
                    LoginViewState.Loading -> {
                        hideKeyboard()
                    }
                    LoginViewState.Complete -> {

                        try {
                            findNavController().navigate(
                                LoginFragmentDirections.actionNavLoginToConfirmCodeFragment(
                                    viewModel.dataCode,
                                    viewModel.dataPhone
                                )
                            )
                        } catch (e: Exception) {
                            Log.e("errornavi","$e")
                        }

                    }
                    LoginViewState.Error -> {
                        toast(requireContext().getString(R.string.error_exceptio))
                    }
                    else -> {
                    }
                }
            }
        }


    }

}