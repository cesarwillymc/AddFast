package com.summit.postulate.listPostulate


import android.os.Bundle
import android.view.View
import com.summit.android.addfast.app.MyApp
import com.summit.postulate.R
import com.summit.postulate.databinding.FragmentPostulateBinding
import com.summit.commons.ui.base.BaseFragment
import com.summit.postulate.listPostulate.di.DaggerListPostulateComponent
import com.summit.postulate.listPostulate.di.ListPostulateModule

class PostulateFragment : BaseFragment<FragmentPostulateBinding, PostulateViewModel>(
    layoutId = R.layout.fragment_postulate
) {
    override fun onInitDependencyInjection() {
        DaggerListPostulateComponent.builder().coreComponent(MyApp.coreComponent(requireContext())).listPostulateModule(
            ListPostulateModule(this)
        ).build().inject(this)
    }

    override fun onInitDataBinding() {
        viewBinding.viewModel=viewModel
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }
}