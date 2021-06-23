package com.summit.offert.myadds

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.summit.android.addfast.app.MyApp
import com.summit.commons.ui.base.BaseFragment
import com.summit.offert.R
import com.summit.offert.databinding.FragmentMyAddsBinding
import com.summit.offert.myadds.adapter.MyAddAdapter
import com.summit.offert.myadds.adapter.MyAddOptionsAdapter
import com.summit.offert.myadds.di.DaggerMyAddComponent
import com.summit.offert.myadds.di.MyAddModule

class MyAddsFragment : BaseFragment<FragmentMyAddsBinding,MyAddViewModel>(
    layoutId = R.layout.fragment_my_adds
){

    private lateinit var adaptadorView:MyAddOptionsAdapter
    private lateinit var adapterAnuncios:MyAddAdapter
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRvOptionsMyAdds()
        setupRvAdapterList()
        loadBarraSuperior()

        loadDataPaginas()

        viewBinding.viewListadds.crearanuncioButton.setOnClickListener {
          findNavController().navigate(MyAddsFragmentDirections.actionNavMyAddToNavCreateAdd())
        }
    }

    private fun setupRvAdapterList() {
        adapterAnuncios = MyAddAdapter { anuncios, _ ->

        }
        viewBinding.viewListadds.misAnuncioRv.apply {
            layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL,false)
            adapter = adapterAnuncios
        }
    }

    private fun setupRvOptionsMyAdds() {
        adaptadorView = MyAddOptionsAdapter { type,position->
            adaptadorView.setearPosition(position)
            adapterAnuncios.searchBy(type)
        }
        viewBinding.viewListadds.misAnuncioOptions.apply {
            layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL,false)
            adapter = adaptadorView
        }
    }

    private fun loadDataPaginas() {
        viewModel.getUserInfo()?.let {user->
            viewModel.verMisPostulaciones(user._id)
            viewModel.dataListAdds.observe(viewLifecycleOwner) { data ->
                data?.let { list ->
                    adapterAnuncios.updateData(list.toMutableList())
                }
            }

        }

    }


    private fun loadBarraSuperior() {
        adaptadorView.updateData(listOf("TODOS","PENDIENTE","PUBLICADO","FINALIZADO"))
    }





    override fun onInitDependencyInjection() {
        DaggerMyAddComponent.builder().coreComponent(MyApp.coreComponent(requireContext())).myAddModule(
            MyAddModule(this)
        ).build().inject(this)
    }

    override fun onInitDataBinding() {
        viewBinding.viewModel=viewModel
    }

}
