package com.summit.android.addfast.ui.main.user.postulaciones

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.summit.android.addfast.R
import com.summit.android.addfast.base.BaseFragment
import com.summit.android.addfast.repo.model.Postulacion
import com.summit.android.addfast.ui.camera.TipeSearchGalleryAdapter
import com.summit.android.addfast.ui.main.MainViewModel
import com.summit.android.addfast.ui.main.MainViewModelFactory
import com.summit.android.addfast.utils.lifeData.Status
import kotlinx.android.synthetic.main.fragment_postulaciones.*
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.kodein
import org.kodein.di.generic.instance
import org.koin.androidx.viewmodel.ext.android.viewModel


class PostulacionesFragment : BaseFragment(),
    TipeSearchGalleryAdapter.TipeSearchGalleryListener,PostulacionesAdapter.Listener {
    val viewModel: MainViewModel by viewModel()
    override fun getLayout()=R.layout.fragment_postulaciones
    lateinit var adaptadorView:TipeSearchGalleryAdapter
    lateinit var adapterPostulacion:PostulacionesAdapter
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //Top
        adaptadorView = TipeSearchGalleryAdapter(this)
        postulaciones_options.apply {
            layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL,false)
            adapter = adaptadorView
        }
        //Postulaciones visible
        adapterPostulacion = PostulacionesAdapter(this)
        postulaciones_rv.apply {
            layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL,false)
            adapter = adapterPostulacion
        }
        loadBarraSuperior()
        loadPostulaciones()
    }

    private fun loadBarraSuperior() {
        adaptadorView.updateData(listOf(TipeSearchGalleryAdapter.GalleryDocs("TODOS","TODOS"),
            TipeSearchGalleryAdapter.GalleryDocs("ENVIADO","ENVIADO"), TipeSearchGalleryAdapter.GalleryDocs("LEIDO","LEIDO")))
    }

    private fun loadPostulaciones(){
        val usuario=viewModel.getStaticDataUser()
        viewModel.verMisPostulaciones(usuario._id).observe(viewLifecycleOwner, Observer {
            when (it.status) {
                Status.LOADING -> {
                }
                Status.SUCCESS -> {
                    postulaciones_shimmer.hide()
                    postulaciones_rv.show()
                    adapterPostulacion.updateData(it.data as MutableList<Postulacion>)
                }
                Status.ERROR -> {
                    Log.e("getAnunciosByCategorias",it.exception!!.message!!)
                    toast(it.exception!!.message!!)

                }

            }
        })
    }

    override fun onclickGallery(dato: String, position: Int) {
        adaptadorView.setearPosition(position)
        adapterPostulacion.searchBy(dato)
    }

    override fun onclick(anuncios: Postulacion, position: Int) {
        findNavController().navigate(PostulacionesFragmentDirections.actionNavPostulacionesToPreviewPostulacionFragment(anuncios,false))
    }

}