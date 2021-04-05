package com.summit.android.addfast.ui.main.service

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.summit.android.addfast.R
import com.summit.android.addfast.base.BaseFragment
import com.summit.android.addfast.repo.model.Anuncios
import com.summit.android.addfast.repo.model.Postulacion
import com.summit.android.addfast.ui.main.MainViewModel
import com.summit.android.addfast.ui.main.MainViewModelFactory
import com.summit.android.addfast.utils.lifeData.Status
import kotlinx.android.synthetic.main.fragment_postulaciones_anuncios.*
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.kodein
import org.kodein.di.generic.instance
import org.koin.androidx.viewmodel.ext.android.viewModel

class PostulacionesAnunciosFragment : BaseFragment(),VerPostulacionesAnuncioAdapter.Listener {
    val viewModel: MainViewModel by viewModel()
    override fun getLayout()=R.layout.fragment_postulaciones_anuncios
    lateinit var adapterPostulaciones:VerPostulacionesAnuncioAdapter
    val args:PostulacionesAnunciosFragmentArgs by navArgs()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapterPostulaciones = VerPostulacionesAnuncioAdapter(this)
        postulacion_anuncio_rv.apply {
            layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL,false)
            adapter = adapterPostulaciones
        }
        cargarDatos()
    }

    private fun cargarDatos() {
        viewModel.verPostulacionesdemiAnuncio((args.modelo as Anuncios).id ).observe(viewLifecycleOwner, Observer {
            when (it.status) {
                Status.LOADING -> {
                    toast("Cargando")
                }
                Status.SUCCESS -> {
                    postulacion_anuncio_shimmer.hide()
                    postulacion_anuncio_rv.show()
                    adapterPostulaciones.updateData(it.data as MutableList<Postulacion>)

                }
                Status.ERROR -> {
                    snakBar(it.exception!!.message!!)
                    Log.e("ErrorProfile",it.exception!!.message!!)
                }
            }
        })
    }

    override fun onclick(anuncios: Postulacion, position: Int) {
        findNavController().navigate(PostulacionesAnunciosFragmentDirections.actionPostulacionesAnunciosFragmentToPreviewPostulacionFragment(anuncios))
    }

}