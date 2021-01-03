package com.summit.android.addfast.ui.main.service

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.summit.android.addfast.R
import com.summit.android.addfast.base.BaseFragment
import com.summit.android.addfast.ui.main.MainViewModel
import com.summit.android.addfast.ui.main.MainViewModelFactory
import com.summit.android.addfast.utils.callNumber
import com.summit.android.addfast.utils.lifeData.Status
import com.summit.android.addfast.utils.sendMessageWhatsApp
import kotlinx.android.synthetic.main.fragment_preview_postulacion.*
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.kodein
import org.kodein.di.generic.instance


class PreviewPostulacionFragment : BaseFragment(), KodeinAware {
    private lateinit var viewModel: MainViewModel
    override val kodein by kodein()
    private val factory: MainViewModelFactory by instance()
    override fun getLayout()=R.layout.fragment_preview_postulacion
    val args:PreviewPostulacionFragmentArgs by navArgs()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = requireActivity().run {
            ViewModelProvider(this, factory).get(MainViewModel::class.java)
        }
        if(args.isRevision){
            loadCambiarEstado()
        }else{
            preview_postulacion_message.hide()
            preview_postulacion_call.hide()
        }

        bindeodeDatos()
    }

    private fun bindeodeDatos() {
        Glide.with(requireContext()).load(args.modelo.imgpostulante).into(preview_postulacion_img)
        preview_postulacion_call.setOnClickListener {
            requireContext().callNumber(args.modelo.phonepostulante)
        }
        preview_postulacion_message.setOnClickListener {
            val user= viewModel.getStaticDataUser()
            requireContext().sendMessageWhatsApp(args.modelo.phonepostulante, "Hola soy de ${user.nameEmpresa} ")
        }
        preview_postulacion_ver_cv.setOnClickListener {
            val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(args.modelo.archivopdf))
            startActivity(browserIntent)
        }
        preview_postulacion_diferencio.text=args.modelo.diferencia
        preview_postulacion_cv.text=args.modelo.rcv
        preview_postulacion_especialidad.text=args.modelo.respecialidad
        preview_postulacion_name.text=args.modelo.name

    }

    private fun loadCambiarEstado() {
        viewModel.cambiarEstadoPostulacion(args.modelo.id, "LEIDO").observe(viewLifecycleOwner, Observer {
            when (it.status) {
                Status.LOADING -> {
                }
                Status.SUCCESS -> {
                }
                Status.ERROR -> {
                }
            }
        })
    }

}
