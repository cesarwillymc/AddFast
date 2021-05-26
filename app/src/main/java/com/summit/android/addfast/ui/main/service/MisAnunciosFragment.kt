package com.summit.android.addfast.ui.main.service

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.lifecycle.Observer
import androidx.navigation.NavDirections
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.summit.android.addfast.R
import com.summit.android.addfast.base.BaseFragment
import com.summit.android.addfast.repo.model.Anuncios
import com.summit.android.addfast.ui.camera.TipeSearchGalleryAdapter
import com.summit.android.addfast.ui.main.MainViewModel
import com.summit.android.addfast.utils.lifeData.Status
import com.summit.android.addfast.utils.system.SharedPreferencsTemp.Companion.clearAllTempShared
import kotlinx.android.synthetic.main.fragment_mis_anuncios.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class MisAnunciosFragment : BaseFragment(),TipeSearchGalleryAdapter.TipeSearchGalleryListener ,AnunciosAdapter.Listener{
    val viewModel: MainViewModel by viewModel()
    lateinit var adaptadorView:TipeSearchGalleryAdapter
    lateinit var adapterAnuncios:AnunciosAdapter
    override fun getLayout()=R.layout.fragment_mis_anuncios
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        clearAllTempShared()
        viewModel.anuncioCreate.postValue(null)
        adaptadorView = TipeSearchGalleryAdapter(this)
        mis_anuncio_options.apply {
            layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL,false)
            adapter = adaptadorView
        }
        adapterAnuncios = AnunciosAdapter(this)
        mis_anuncio_rv.apply {
            layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL,false)
            adapter = adapterAnuncios
        }
        loadBarraSuperior()
        crearanuncio_button.setOnClickListener {
            MisAnunciosFragmentDirections.actionNavAnunciosToCrearAnuncioFragment().toLoad()
        }
        loadDataPaginas()
    }

    private fun loadDataPaginas() {
        val user= viewModel.getStaticDataUser()
        viewModel.getMisAnuncios(user._id).observe(viewLifecycleOwner, Observer {
            when (it.status) {
                Status.LOADING -> {
                }
                Status.SUCCESS -> {
                    mis_anuncio_shimmer.hide()
                    mis_anuncio_rv.show()
                    adapterAnuncios.updateData(it.data as MutableList<Anuncios>)

                }
                Status.ERROR -> {
                    snakBar(it.exception!!.message!!)
                    Log.e("ErrorProfile",it.exception!!.message!!)
                }
            }
        })
    }

    fun NavDirections.toLoad(){
        try{
            findNavController().navigate(this)
        }catch (e:IllegalStateException){

        }
    }
    private fun loadBarraSuperior() {
        adaptadorView.updateData(listOf(
            TipeSearchGalleryAdapter.GalleryDocs("TODOS","TODOS"),
                TipeSearchGalleryAdapter.GalleryDocs("PENDIENTE","PENDIENTE"),
            TipeSearchGalleryAdapter.GalleryDocs("PUBLICADO","PUBLICADO"), TipeSearchGalleryAdapter.GalleryDocs("FINALIZADO","FINALIZADO")))
    }

    override fun onclickGallery(dato: String, position: Int) {
        adaptadorView.setearPosition(position)
        adapterAnuncios.searchBy(dato)
    }

    override fun onclick(anuncios: Anuncios, position: Int) {
        findNavController().navigate(MisAnunciosFragmentDirections.actionNavAnunciosToMiAnuncioPreviewFragment(anuncios))
    }
}