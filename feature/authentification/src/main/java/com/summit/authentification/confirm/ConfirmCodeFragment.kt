package com.summit.authentification.confirm

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.summit.android.addfast.app.MyApp
import com.summit.authentification.R
import com.summit.authentification.confirm.di.ConfirmCodeModule
import com.summit.authentification.confirm.di.DaggerConfirmCodeComponent
import com.summit.authentification.databinding.FragmentConfirmCodeBinding
import com.summit.commons.ui.base.BaseFragment

class ConfirmCodeFragment : BaseFragment<FragmentConfirmCodeBinding, ConfirmCodeViewModel>(
    layoutId = R.layout.fragment_confirm_code
) {
    private val args: ConfirmCodeFragmentArgs by navArgs()
    override fun onInitDependencyInjection() {
        DaggerConfirmCodeComponent.builder().coreComponent(MyApp.coreComponent(requireContext()))
            .confirmCodeModule(ConfirmCodeModule(this)).build().inject(this)
    }

    override fun onInitDataBinding() {
        viewBinding.viewModel = viewModel
        viewModel.codeArgs = "+${args.code}"
        viewModel.phoneArgs = args.phone
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewBinding.lblInicioSend.buttonSend.setOnClickListener {
            viewModel.sendCodeConfirmAuth()
            listenStateSendCode()
        }
    }


    override fun onDestroyView() {
        viewModel.stopViewModel()
        super.onDestroyView()
    }

    @SuppressLint("RestrictedApi")
    private fun listenStateSendCode() {

        if (viewModel.stateConfirmCode.value == null) {
            viewModel.stateConfirmCode.observe(viewLifecycleOwner) {
                when (it) {
                    ConfirmCodeViewState.Loading -> {
                        hideKeyboard()
                    }
                    ConfirmCodeViewState.Complete -> {
                        findNavController().backStack.clear()
                    }
                    ConfirmCodeViewState.InComplete -> {
                        findNavController().navigate(
                            ConfirmCodeFragmentDirections.actionConfirmCodeFragmentToRegisterFragment(
                                id = viewModel.identificador,
                                phone = viewModel.phoneArgs
                            )
                        )
                    }
                    ConfirmCodeViewState.Error -> {
                        toast(requireContext().getString(R.string.error_exceptio))
                    }
                    else -> {
                    }
                }
            }
        }
    }


}