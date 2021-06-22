package com.summit.authentification.register

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.summit.android.addfast.app.MyApp
import com.summit.authentification.R
import com.summit.authentification.databinding.FragmentRegisterBinding
import com.summit.authentification.register.di.DaggerRegisterComponent
import com.summit.authentification.register.di.RegisterModule
import com.summit.commons.ui.base.BaseFragment
import java.io.File


class RegisterFragment : BaseFragment<FragmentRegisterBinding, RegisterViewModel>(
    layoutId = R.layout.fragment_register
) {
    private val args: RegisterFragmentArgs by navArgs()
    override fun onInitDependencyInjection() {
        DaggerRegisterComponent.builder().coreComponent(MyApp.coreComponent(requireContext()))
            .registerModule(RegisterModule(this)).build().inject(this)
    }

    override fun onInitDataBinding() {
        viewBinding.viewModel = viewModel
        viewModel.idArgs = args.id
        viewModel.phoneArgs = args.phone
    }

    private val keyPhoto = "KEY_PHOTO_REGISTER"

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        listenFragmentData()
        setupBinding()
        listenStateOnClick()
    }
    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        super.onViewStateRestored(savedInstanceState)

        listenFragmentData()
    }
    private fun listenFragmentData(){
        requireActivity().supportFragmentManager.setFragmentResultListener(
            keyPhoto, viewLifecycleOwner
        ) { key, result ->
            if(keyPhoto== key){
                if (!result.getString("data").isNullOrEmpty()) {
                    viewModel.photoDirection.postValue(File(result.getString("data")!!))
                }
            }
        }
    }
    private fun listenStateOnClick() {
        if (viewModel.stateRegister.value == null) {
            viewModel.stateRegister.observe(viewLifecycleOwner) {
                when (it) {
                    RegisterViewState.Loading -> {
                        hideKeyboard()
                    }
                    RegisterViewState.Complete -> {
                        findNavController().navigate(RegisterFragmentDirections.actionRegisterFragmentToNavInicioGraph())
                    }
                    RegisterViewState.Error -> {
                        toast("Sucedio un error al registrar tu cuenta")
                    }
                    else -> {
                    }
                }
            }
        }
    }

    private fun setupBinding() {
        viewBinding.formView.cargarfoto.setOnClickListener {
            onClickPhoto()
        }
        viewBinding.formView.imgRiderFr.setOnClickListener {
            onClickPhoto()
        }
    }

    private fun onClickPhoto() {

        findNavController().navigate(RegisterFragmentDirections.actionRegisterFragmentToNavCameraxGraph())
    }
}