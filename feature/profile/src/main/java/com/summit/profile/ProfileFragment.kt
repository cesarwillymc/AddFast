package com.summit.profile

import android.os.Bundle
import android.view.View
import com.summit.android.addfast.app.MyApp
import com.summit.commons.ui.base.BaseFragment
import com.summit.profile.databinding.FragmentProfileBinding
import com.summit.profile.di.DaggerProfileComponent
import com.summit.profile.di.ProfileModule

class ProfileFragment : BaseFragment<FragmentProfileBinding, ProfileViewModel>(
    layoutId = R.layout.fragment_profile
) {
    override fun onInitDependencyInjection() {
        DaggerProfileComponent.builder().coreComponent(MyApp.coreComponent(requireContext())).profileModule(
            ProfileModule(this)
        ).build().inject(this)
    }

    override fun onInitDataBinding() {
        viewBinding.viewModel=viewModel
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
       // viewBinding.viewUserSignin.lottieAnimationView.playAnimation()
    }

    override fun onDetach() {

        super.onDetach()
    }
}