package com.summit.android.addfast.ui.main.user.anuncios

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.core.view.ViewCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavDirections
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.summit.android.addfast.R
import com.summit.android.addfast.base.BaseFragment
import com.summit.android.addfast.repo.model.Anuncios
import com.summit.android.addfast.repo.model.CategoriasModel
import com.summit.android.addfast.repo.model.ListaAnuncios
import com.summit.android.addfast.repo.model.Promociones
import com.summit.android.addfast.repo.model.departamento.UbicacionModel
import com.summit.android.addfast.ui.main.MainViewModel
import com.summit.android.addfast.ui.main.MainViewModelFactory
import com.summit.android.addfast.ui.main.user.carrusel.SlideAdapter
import com.summit.android.addfast.utils.autoScroll
import com.summit.android.addfast.utils.lifeData.RsrProgress
import com.summit.android.addfast.utils.lifeData.Status
import com.xwray.groupie.ExpandableGroup
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.GroupieViewHolder
import com.xwray.groupie.Section
import kotlinx.android.synthetic.main.fragment_anuncios.*
import kotlinx.coroutines.flow.cancellable
import kotlinx.coroutines.flow.collect
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.kodein
import org.kodein.di.generic.instance
import org.koin.androidx.viewmodel.ext.android.viewModel


class Categorias : BaseFragment(), SlideAdapter.onCLickListenerPromo,CategoriasProductosListener,CategoriasListener{
    val viewModel: MainViewModel by viewModel()
    private lateinit var categoriasAdaper: CategoriasAdaper
    // private lateinit var categoriasAdaper: CategoriasAdaper
    //private lateinit var productosAdapter: AdapterProductosCategoria
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val callback: OnBackPressedCallback = object : OnBackPressedCallback(true /* enabled by default */) {
            override fun handleOnBackPressed() {
                requireActivity().finish()
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, callback)


        viewModel.getUbicacion().observe(viewLifecycleOwner, Observer {
            if(it!=null){
                Log.e("ubicaciom data","${it.departamento}")
                 loadAnunciosReload()
                loadPromocionesReload()
            }else{
                viewModel.saveUbicacion(UbicacionModel("Puno","Puno",0))
            }

        })
        //Rv Promociones
        slideAdapter =
                SlideAdapter(this)
        viewPager?.let{
            it.apply {
            adapter = slideAdapter
        }

            //Categorias Rv
            categoriasAdaper = CategoriasAdaper(this)
            //LinearLayoutManager(requireContext(),
            //                LinearLayoutManager.HORIZONTAL,false)
            categorias_rv.apply {
                layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL,false)
                setHasFixedSize(true)
                adapter = categoriasAdaper
            }
            loadService()
            //Rv Productos
            // productosAdapter =
            //   AdapterProductosCategoria(this)



            //Obtener productosd con categorias

            viewModel.getCategorias().observe(viewLifecycleOwner, Observer {
                when (it.status) {
                    Status.LOADING -> {
                    }
                    Status.SUCCESS -> {
                        categoriasAdaper.updateData(it.data as MutableList<CategoriasModel>)
                    }
                    Status.ERROR -> {
                        Log.e("getCategorias",it.exception!!.message!!)
                        toast(it.exception!!.message!!)
                    }

                }
            })
        }


    }

    private fun navigationError(nav: NavDirections) {
        try {
            findNavController().navigate(nav)
        }catch (e: IllegalStateException){
            Log.e("ilegal nav", e.message!!)
        }
    }

    private lateinit var slideAdapter: SlideAdapter

    private var positionImg:Int=0
    private fun loadService() {

        //Obtener lista de Promociones
        viewModel.promocionesData.observe(viewLifecycleOwner, Observer {
            Log.e("anunciosData","aa $it")
            if(it.isNotEmpty()){
                viewPager.show()
                inizializarDatos(it)
            }else{
                viewPager.hide()
                text_services_ofert.text = "Productos"
                loadPromocionesReload()
            }
        })


        viewModel.anunciosData.observe(viewLifecycleOwner, Observer {
            Log.e("anunciosData","aa $it")
            if(it.isNotEmpty()){
                inicializarDatosRV(it)
            }else{
                loadAnunciosReload()
            }
        })





    }
    private fun loadPromocionesReload(){
        viewModel.getPromocionesUpdate().observe(viewLifecycleOwner, Observer {
            when(it.status){
                Status.LOADING->{
                }
                Status.SUCCESS->{


                }
                Status.ERROR->{
                    Log.e("getPromocionesUpdate",it.exception!!.message!!)
                    toast(it.exception!!.message!!)
                }
            }
        })
    }
    private fun loadAnunciosReload(){
        viewModel.getAnunciosByCategorias().observe(viewLifecycleOwner, Observer {
            when (it.status) {
                Status.LOADING -> {
                }
                Status.SUCCESS -> {

                }
                Status.ERROR -> {
                    Log.e("getAnunciosByCategorias",it.exception!!.message!!)
                    //toast(it.exception!!.message!!)

                }

            }
        })
    }
    val adapterGrupie = GroupAdapter<GroupieViewHolder>().apply {
        spanCount=1
    }
    private fun inicializarDatosRV(data: List<ListaAnuncios>) {

        val linearLayoutManager: LinearLayoutManager =
                object : LinearLayoutManager(requireContext(), VERTICAL, false) {
                    override fun canScrollHorizontally(): Boolean {
                        return false
                    }
                }
        linearLayoutManager.initialPrefetchItemCount= data.size
        linearLayoutManager.isItemPrefetchEnabled=true

        //GridLayoutManager(requireContext(),2).apply {
        //                spanSizeLookup= adapterGrupie.spanSizeLookup
        //                initialPrefetchItemCount= data.size
        //                isItemPrefetchEnabled=true
        //            }
        adapterGrupie.clear()
        rv_services_producr.apply {
            layoutManager = linearLayoutManager
            setHasFixedSize(true)
            adapter = adapterGrupie
        }
        for (variables in data){
            val section = Section()
            val modelo = AdapterProductosCategoria(this,variables)

            // section.setHeader(modelo)

            section.add(modelo)
            adapterGrupie.add(section)


        }
        // inicilizarScroll()
        // ViewCompat.setNestedScrollingEnabled(rv_services_producr, false)
        //  productosAdapter.updateData(it.data)
    }

    private fun inizializarDatos(datos: List<Promociones>) {


        text_services_ofert.text= "Ofertas"
        slideAdapter.setDataImage(datos as MutableList)
        if (datos.isNotEmpty()&& datos.size>1){
            viewPager!!.autoScroll(5000L)
        }

    }



    override fun getLayout(): Int = R.layout.fragment_anuncios



    override fun onClickIzquierda(position: Int, total: Int) {
        if (position==0){
            viewPager.setCurrentItem(total - 1, true)
        }else{
            viewPager.setCurrentItem((position - 1), true)
        }
    }

    override fun onClickDerecha(position: Int, total: Int) {
        if (position==(total-1)){
            viewPager.setCurrentItem((0), true)
        }else{
            viewPager.setCurrentItem((position + 1), true)
        }

    }

    override fun onCLickItem(item: Promociones, position: Int) {
        viewModel.getAnuncioId(item.idanuncio).observe(viewLifecycleOwner, Observer {
            when(it.status){
                Status.LOADING->{
                    toast("Cargando promocion")
                }
                Status.SUCCESS->{
                    findNavController().navigate(CategoriasDirections.actionNavInicioToVerAnuncioFragment(it.data!!))
                }
                Status.ERROR->{
                    Log.e("getPromocionesUpdate",it.exception!!.message!!)
                    toast(it.exception!!.message!!)
                }
            }
        })


        //navigationError(CategoriasDirections.actionNavServicioToProductPreview(item.productId))
    }

    override fun onClickVerMas(dato: ListaAnuncios, position: Int) {
        findNavController().navigate(CategoriasDirections.actionNavInicioToVerCategoriasFragment2(dato.id))
      /*  navigationError(
                CategoriasDirections.actionNavServicioToProducts(
                        dato.service._id,
                        dato.service.categoriaId
                )
        )*/
    }

    override fun onCLickItem(dato: Anuncios, position: Int) {
        findNavController().navigate(CategoriasDirections.actionNavInicioToVerAnuncioFragment(dato))
        //avigationError(CategoriasDirections.actionNavServicioToProductPreview(dato._id))
    }

    override fun listener(position: Int, datos: CategoriasModel) {
        try {
            if (datos.id!="Todos"){
                findNavController().navigate(CategoriasDirections.actionNavInicioToVerCategoriasFragment2(datos.id))
            }
        }catch (e: KotlinNullPointerException){
        }
    }
}
