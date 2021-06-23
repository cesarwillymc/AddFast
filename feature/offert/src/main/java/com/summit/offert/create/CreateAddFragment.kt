package com.summit.offert.create

import com.summit.android.addfast.app.MyApp
import com.summit.commons.ui.base.BaseFragment
import com.summit.offert.R
import com.summit.offert.create.di.CreateAddModule
import com.summit.offert.create.di.DaggerCreateAddComponent
import com.summit.offert.databinding.FragmentCreateAddBinding

class CreateAddFragment:BaseFragment<FragmentCreateAddBinding,CreateAddViewModel>(
    layoutId = R.layout.fragment_create_add
) {
    override fun onInitDependencyInjection() {
        DaggerCreateAddComponent.builder().coreComponent(MyApp.coreComponent(requireContext()))
            .createAddModule(CreateAddModule(this)).build().inject(this)

    }

    override fun onInitDataBinding() {
        viewBinding.viewModel=viewModel
    }
}