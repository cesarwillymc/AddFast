package com.summit.home.home


import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.summit.android.addfast.app.MyApp
import com.summit.commons.ui.base.BaseFragment
import com.summit.commons.ui.extension.observe
import com.summit.core.network.model.Anuncios
import com.summit.core.network.model.CategoriasModel
import com.summit.core.network.model.ListaAnuncios
import com.summit.core.network.model.Promociones
import com.summit.core.network.model.departamento.UbicacionModel
import com.summit.home.R
import com.summit.home.databinding.FragmentHomeBinding
import com.summit.home.home.adapter.AdapterProductosCategoria
import com.summit.home.home.adapter.CategoriasAdaper
import com.summit.home.home.adapter.CategoriasProductosListener
import com.summit.home.home.adapter.SlideAdapter
import com.summit.home.home.di.DaggerHomeComponent
import com.summit.home.home.di.HomeModule
import com.summit.home.home.utils.autoScroll


class HomeFragment : BaseFragment<FragmentHomeBinding, HomeViewModel>(
    layoutId = R.layout.fragment_home
) {

    //Recycler view Binding
    private lateinit var offertAdapter: SlideAdapter
    private lateinit var categoriasAdaper: CategoriasAdaper
    private lateinit var adapterAdd: AdapterProductosCategoria

    override fun onInitDataBinding() {
        viewBinding.viewModel = viewModel
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observe(viewModel.getUbicacion, ::getUbicationModel)
        setupRvBindingAdd()
        setupRvBindingCategory()
        setupRvBindingOffert()
    }

    private fun getUbicationModel(ubicacionModel: UbicacionModel?) {

        ubicacionModel?.let {
            viewModel.ubicationLast?.let {
                if (ubicacionModel.departamento != viewModel.ubicationLast!!.departamento) {
                    viewModel.ubicationLast = ubicacionModel
                    setupRVs(isRefresh = true)
                }

            } ?: run {
                viewModel.ubicationLast = ubicacionModel
                setupRVs(isRefresh = false)

            }

        }
    }

    private fun setupRVs(isRefresh: Boolean) {
        loadAnunciosReload(isRefresh = isRefresh)
        loadPromocionesReload(isRefresh = isRefresh)
        loadCategoryReload(isRefresh = isRefresh)
    }

    private fun setupRvBindingOffert() {
        offertAdapter = SlideAdapter(object : SlideAdapter.OnCLickListenerPromo {
            override fun onCLickItem(item: Promociones, position: Int) {
                if (item.idanuncio.isNotEmpty()) {
                    Log.e("dataPromo", item.toString())
                    findNavController().navigate(HomeFragmentDirections.actionNavHomeToNavDetailAd(idAnuncio = item.idanuncio))
                }
            }
        })
        viewBinding.viewPager.let {
            it.apply {
                adapter = offertAdapter
            }
            it.autoScroll(5000L)
        }
        observe(viewModel.dataPromociones, ::updateAdapterPromociones)
    }

    private fun updateAdapterPromociones(promociones: List<Promociones>) =
        offertAdapter.setDataImage(promociones.toMutableList())

    private fun setupRvBindingCategory() {
        categoriasAdaper = CategoriasAdaper(object : CategoriasAdaper.CategoriasListener {
            override fun listener(position: Int, datos: CategoriasModel) {
                if (datos.id != "Todos") {
                    findNavController().navigate(HomeFragmentDirections.actionNavHomeToNavListCategory(idcategory = datos.id))
                }
            }

        })
        viewBinding.categoriasRv.apply {
            setHasFixedSize(true)
            adapter = categoriasAdaper
            layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        }
        observe(viewModel.dataCategorys, ::updateAdapterCategory)
    }

    private fun updateAdapterCategory(category: List<CategoriasModel>) =
        categoriasAdaper.updateData(category.toMutableList())

    private fun setupRvBindingAdd() {
        adapterAdd = AdapterProductosCategoria(object : CategoriasProductosListener {
            override fun onClickVerMas(dato: ListaAnuncios, position: Int) {
                findNavController().navigate(HomeFragmentDirections.actionNavHomeToNavListCategory(idcategory = dato.id))
            }

            override fun onCLickItem(dato: Anuncios, position: Int) {
                findNavController().navigate(HomeFragmentDirections.actionNavHomeToNavDetailAd(model = dato))
            }
        })
        viewBinding.includeList.rvServicesProducr.apply {
            layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            adapter = adapterAdd
        }
        observe(viewModel.dataAnuncios, ::updateAdapterAdd)

    }

    private fun updateAdapterAdd(anuncios: List<ListaAnuncios>) = adapterAdd.updateData(anuncios.toMutableList())

    private fun loadPromocionesReload(isRefresh: Boolean) {
        if (viewModel.dataPromociones.value == null || isRefresh) {
            viewModel.getPromocionesUpdate()
        }

    }

    private fun loadCategoryReload(isRefresh: Boolean) {
        if (viewModel.dataCategorys.value == null || isRefresh) {
            viewModel.getCategorias()
        }
    }

    private fun loadAnunciosReload(isRefresh: Boolean) {
        if (viewModel.dataAnuncios.value == null || isRefresh) {
            viewModel.getAnunciosByCategorias()
        }
    }

    override fun onInitDependencyInjection() {
        DaggerHomeComponent
            .builder()
            .coreComponent(MyApp.coreComponent(requireContext()))
            .homeModule(HomeModule(this))
            .build()
            .inject(this)
    }
}