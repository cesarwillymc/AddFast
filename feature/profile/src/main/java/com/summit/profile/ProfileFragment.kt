package com.summit.profile

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupActionBarWithNavController
import com.airbnb.lottie.LottieAnimationView
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
        Log.e("onViewCreated","entro aca profile")
        viewBinding.viewUserSignin.signinButton.setOnClickListener {
           try{
               findNavController().navigate(ProfileFragmentDirections.actionNavProfileToNavAuthentificationGraph())
           }catch (e:Exception){}
        }
    }


    override fun onDetach() {
        Log.e("onDetach","entro aca profile")
        try{
            viewBinding.viewUserSignin.lottieAnimationView.cancelAnimation()
            viewBinding.viewUserSignin.lottieAnimationView.clearAnimation()
        }catch (e:Exception){

        }
        super.onDetach()
    }
    override fun onAttach(context: Context) {
        Log.e("onAttach","entro aca profile")
        super.onAttach(context)

    }



    override fun onCreate(savedInstanceState: Bundle?) {
        Log.e("onCreate","entro aca profile")
        super.onCreate(savedInstanceState)
    }
    override fun onStart() {
        Log.e("onStart","entro aca profile")
        super.onStart()

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        Log.e("onActivityCreated","entro aca profile")
        super.onActivityCreated(savedInstanceState)

    }

    override fun onResume() {
        Log.e("onResume","entro aca profile")
        super.onResume()

    }



    override fun onDestroy() {
        Log.e("onDestroy","entro aca profile")
        super.onDestroy()

    }

    override fun onDestroyView() {
        Log.e("onDestroyView","entro aca profile")
        super.onDestroyView()
    }

    override fun onStop() {
        Log.e("onStop","entro aca profile")
        super.onStop()

    }
}