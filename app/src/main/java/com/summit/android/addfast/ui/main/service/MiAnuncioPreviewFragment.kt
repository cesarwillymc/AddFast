package com.summit.android.addfast.ui.main.service

import android.os.Bundle
import android.os.Handler
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.summit.android.addfast.R
import com.summit.android.addfast.base.BaseFragment
import com.summit.android.addfast.ui.main.MainViewModel
import com.summit.android.addfast.ui.main.MainViewModelFactory
import com.summit.android.addfast.utils.lifeData.Status
import jp.wasabeef.glide.transformations.BlurTransformation
import kotlinx.android.synthetic.main.fragment_mi_anuncio_preview.*
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.kodein
import org.kodein.di.generic.instance
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.ocpsoft.prettytime.PrettyTime
import java.util.*

class MiAnuncioPreviewFragment : BaseFragment() {
    val viewModel: MainViewModel by viewModel()
    override fun getLayout()=R.layout.fragment_mi_anuncio_preview
    val args:MiAnuncioPreviewFragmentArgs by navArgs()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        loadBindign()
    }
    private fun loadBindign(){
        //esconder botones
        if(args.modelo.type!="Trabajos"){
            ver_anuncio_preview_postular.hide()
        }


        Glide.with(requireContext()).load(args.modelo.img).into(ver_anuncio_preview_img)
        Glide.with(requireContext()).load(args.modelo.img)
                .apply(RequestOptions.bitmapTransform(BlurTransformation(5, 2)))
                .error(R.drawable.grad_splash).into(ver_anuncio_preview_img_blur)
        val prettyTime = PrettyTime(Locale.getDefault())
        val ago: String = prettyTime.format(Date(args.modelo.fecha))
        ver_anuncio_preview_time_ago.text=ago

        ver_anuncio_preview_desc.text=args.modelo.descripcion
        ver_anuncio_preview_title.text=args.modelo.titulo
        //BUTTON
        ver_anuncio_preview_postular.setOnClickListener {
            findNavController().navigate(MiAnuncioPreviewFragmentDirections.actionMiAnuncioPreviewFragmentToPostulacionesAnunciosFragment(args.modelo))
        }
        ver_anuncio_preview_ubicacion.setOnClickListener {
            findNavController().navigate(MiAnuncioPreviewFragmentDirections.actionMiAnuncioPreviewFragmentToUbicationPosition(args.modelo))
        }
        //FLOATING BUTTON
        ver_anuncio_preview_edit.setOnClickListener {

        }
        ver_anuncio_preview_finish_message.setOnClickListener {

        }
        ver_anuncio_preview_close.setOnClickListener {
            terminarpublicacion()
        }

    }

    private fun terminarpublicacion() {
        viewModel.cambiarEstadoAnuncio(args.modelo.id,"FINALIZADO").observe(viewLifecycleOwner, androidx.lifecycle.Observer {
            when (it.status) {
                Status.LOADING -> {
                    toast("Terminando")
                }
                Status.SUCCESS -> {
                    toast("Publicacion Terminada")
                    Handler().postDelayed({
                        findNavController().navigateUp()
                    },700L)
                }
                Status.ERROR -> {
                    snakBar(it.exception!!.message!!)
                    Log.e("ErrorProfile",it.exception!!.message!!)
                }
            }
        })
    }
}