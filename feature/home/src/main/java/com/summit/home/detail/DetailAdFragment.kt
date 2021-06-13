package com.summit.home.detail

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.navigation.fragment.navArgs
import com.summit.android.addfast.app.MyApp
import com.summit.commons.ui.base.BaseFragment
import com.summit.home.R
import com.summit.home.databinding.FragmentDetailAdBinding
import com.summit.home.detail.di.DaggerDetailAdComponent
import com.summit.home.detail.di.DetailAdModule

class DetailAdFragment : BaseFragment<FragmentDetailAdBinding, DetailAdViewModel>(
    layoutId = R.layout.fragment_detail_ad
) {
    private val args:DetailAdFragmentArgs by navArgs()

    override fun onInitDependencyInjection() {
        DaggerDetailAdComponent.builder()
            .coreComponent(MyApp.coreComponent(requireContext()))
            .detailAdModule(DetailAdModule(this))
            .build()
            .inject(this)
    }

    override fun onInitDataBinding() {
        viewBinding.viewModel=viewModel
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        args.model?.let {
            viewModel.setAnuncioToView(it)
            viewModel.addViewsAd(it.id)
        }
        args.idAnuncio?.let {
            viewModel.getAnuncioId(it)
            viewModel.addViewsAd(it)
        }
        setupOnClickListener()
    }

    private fun setupOnClickListener() {
        viewBinding.includeListData.verAnuncioCall.setOnClickListener {

        }
        viewBinding.includeListData.verAnuncioPostular.setOnClickListener {

        }
        viewBinding.includeListData.verAnuncioSendMessage.setOnClickListener {

        }
        viewBinding.includeListData.verAnuncioReport.setOnClickListener {

        }
        viewBinding.includeListData.verAnuncioUbicacion.setOnClickListener {

        }
    }

}