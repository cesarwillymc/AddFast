package com.summit.authentification.confirm

import com.summit.android.addfast.app.MyApp
import com.summit.authentification.R
import com.summit.authentification.confirm.di.ConfirmCodeModule
import com.summit.authentification.confirm.di.DaggerConfirmCodeComponent
import com.summit.authentification.databinding.FragmentConfirmCodeBinding
import com.summit.commons.ui.base.BaseFragment

class ConfirmCodeFragment : BaseFragment<FragmentConfirmCodeBinding, ConfirmCodeViewModel>(
    layoutId = R.layout.fragment_confirm_code
) {
    override fun onInitDependencyInjection() {
        DaggerConfirmCodeComponent.builder().coreComponent(MyApp.coreComponent(requireContext()))
            .confirmCodeModule(ConfirmCodeModule(this)).build().inject(this)
    }

    override fun onInitDataBinding() {
        viewBinding.viewModel=viewModel
    }
}