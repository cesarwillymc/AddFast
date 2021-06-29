package com.summit.postulate.listPostulate


import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.summit.android.addfast.app.MyApp
import com.summit.postulate.R
import com.summit.postulate.databinding.FragmentPostulateBinding
import com.summit.commons.ui.base.BaseFragment
import com.summit.postulate.listPostulate.adapter.PostulacionesAdapter
import com.summit.postulate.listPostulate.adapter.PostulateOptionsAdapter
import com.summit.postulate.listPostulate.di.DaggerListPostulateComponent
import com.summit.postulate.listPostulate.di.ListPostulateModule

class PostulateFragment : BaseFragment<FragmentPostulateBinding, PostulateViewModel>(
    layoutId = R.layout.fragment_postulate
){

    override fun onInitDependencyInjection() {
        DaggerListPostulateComponent.builder().coreComponent(MyApp.coreComponent(requireContext())).listPostulateModule(
            ListPostulateModule(this)
        ).build().inject(this)
    }

    override fun onInitDataBinding() {
        viewBinding.viewModel = viewModel
    }

    private lateinit var postulateItem: PostulateOptionsAdapter
    private lateinit var adapterPostulacion: PostulacionesAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //Top
        setupRvOptionsTop()
        setupRvPostulateItems()

        loadBarraSuperior()
        loadPostulaciones()
    }

    private fun loadBarraSuperior() {
        postulateItem.updateData(
            listOf("TODOS", "ENVIADO", "LEIDO")
        )
    }

    private fun loadPostulaciones() {
        viewModel.getUserInformation()?.let { user ->
            viewModel.verMisPostulaciones(user._id)
            viewModel.dataListPostulates.observe(viewLifecycleOwner) { data ->
                data?.let { list ->
                    adapterPostulacion.updateData(list.toMutableList())
                }
            }

        }


    }

    private fun setupRvPostulateItems() {
        adapterPostulacion = PostulacionesAdapter { postulacion, _ ->

        }
        viewBinding.viewListpostulate.postulacionesRv.apply {
            layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            adapter = adapterPostulacion
        }
    }

    private fun setupRvOptionsTop() {
        postulateItem = PostulateOptionsAdapter {dato,position->
            postulateItem.setearPosition(position)
            adapterPostulacion.searchBy(dato)
        }
        viewBinding.viewListpostulate.postulacionesOptions.apply {
            layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            adapter = postulateItem
        }
    }


}