package com.summit.android.addfast.ui.main.admin.fragments.anuncio

import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.summit.android.addfast.R
import com.summit.android.addfast.base.BaseFragment
import com.summit.android.addfast.ui.main.admin.AdminViewModel
import com.summit.android.addfast.ui.main.admin.AdminViewModelFactory
import com.summit.android.addfast.utils.lifeData.Status
import jp.wasabeef.glide.transformations.BlurTransformation
import kotlinx.android.synthetic.main.fragment_anuncio_preview_admin.*
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.kodein
import org.kodein.di.generic.instance
import org.ocpsoft.prettytime.PrettyTime
import java.util.*

class AnuncioPreviewAdminFragment : BaseFragment(), KodeinAware {
    private lateinit var viewModel: AdminViewModel
    override val kodein by kodein()
    private val factory: AdminViewModelFactory by instance()
    override fun getLayout() = R.layout.fragment_anuncio_preview_admin
    val args: AnuncioPreviewAdminFragmentArgs by navArgs()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = requireActivity().run {
            ViewModelProvider(this, factory).get(AdminViewModel::class.java)
        }
        loadBindign()
    }

    private fun loadBindign() {



        Glide.with(requireContext()).load(args.model.img).into(ver_anuncio_admin_preview_img)
        Glide.with(requireContext()).load(args.model.img)
            .apply(RequestOptions.bitmapTransform(BlurTransformation(5, 2)))
            .error(R.drawable.grad_splash).into(ver_anuncio_admin_preview_img_blur)
        val prettyTime = PrettyTime(Locale.getDefault())
        val ago: String = prettyTime.format(Date(args.model.fecha))
        ver_anuncio_admin_preview_time_ago.text = ago

        ver_anuncio_admin_preview_desc.text = args.model.descripcion
        ver_anuncio_admin_preview_title.text = args.model.titulo
        //BUTTON
        ver_anuncio_admin_preview_postular.setOnClickListener {
            aceptarPublicacion()
        }
        ver_anuncio_admin_preview_ubicacion.setOnClickListener {
            findNavController().navigate(AnuncioPreviewAdminFragmentDirections.actionAnuncioPreviewAdminFragmentToUbicationPosition2(args.model))
        }
        //FLOATING BUTTON

        ver_anuncio_admin_preview_close.setOnClickListener {
            terminarpublicacion()
        }

    }

    private fun aceptarPublicacion() {
        viewModel.cambiarEstado(args.model.id, "PUBLICADO")
            .observe(viewLifecycleOwner, androidx.lifecycle.Observer {
                when (it.status) {
                    Status.LOADING -> {
                        toast("Terminando")
                    }
                    Status.SUCCESS -> {
                        toast("Publicacion Aceptada")
                        Handler().postDelayed({
                            findNavController().navigateUp()
                        }, 700L)
                    }
                    Status.ERROR -> {
                        snakBar(it.exception!!.message!!)
                        Log.e("ErrorProfile", it.exception!!.message!!)
                    }
                }
            })
    }

    private fun terminarpublicacion() {
        viewModel.cambiarEstado(args.model.id, "DENEGADO")
            .observe(viewLifecycleOwner, androidx.lifecycle.Observer {
                when (it.status) {
                    Status.LOADING -> {
                        toast("Terminando")
                    }
                    Status.SUCCESS -> {
                        toast("Publicacion Terminada")
                        Handler().postDelayed({
                            findNavController().navigateUp()
                        }, 700L)
                    }
                    Status.ERROR -> {
                        snakBar(it.exception!!.message!!)
                        Log.e("ErrorProfile", it.exception!!.message!!)
                    }
                }
            })
    }
}