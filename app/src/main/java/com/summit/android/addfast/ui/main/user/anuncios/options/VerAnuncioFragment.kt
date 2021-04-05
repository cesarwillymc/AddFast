package com.summit.android.addfast.ui.main.user.anuncios.options

import android.content.Intent
import android.os.Bundle
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
import com.summit.android.addfast.ui.auth.AuthActivity
import com.summit.android.addfast.ui.main.MainViewModel
import com.summit.android.addfast.ui.main.MainViewModelFactory
import com.summit.android.addfast.ui.main.user.SelectPlaceDialog
import com.summit.android.addfast.utils.callNumber
import com.summit.android.addfast.utils.lifeData.Status
import com.summit.android.addfast.utils.sendMessageWhatsApp
import jp.wasabeef.glide.transformations.BlurTransformation
import kotlinx.android.synthetic.main.fragment_ver_anuncio.*
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.kodein
import org.kodein.di.generic.instance
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.ocpsoft.prettytime.PrettyTime
import java.util.*

class VerAnuncioFragment : BaseFragment() {
    val viewModel: MainViewModel by viewModel()
    override fun getLayout()=R.layout.fragment_ver_anuncio
    val args:VerAnuncioFragmentArgs by navArgs()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
 
        aumentarVisualizacion()
        loadData()
    }

    private fun aumentarVisualizacion() {
        viewModel.aumentarVisualizacionesAnuncios(args.modelo).observe(viewLifecycleOwner, androidx.lifecycle.Observer {
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

    private fun loadData(){

        Glide.with(requireContext()).load(args.modelo.img).into(ver_anuncio_img)
        Glide.with(requireContext()).load(args.modelo.img)
                .apply(RequestOptions.bitmapTransform(BlurTransformation(5, 2)))
                .error(R.drawable.grad_splash).into(ver_anuncio_img_blur)
        ver_anuncio_desc.text=args.modelo.descripcion
        val prettyTime = PrettyTime(Locale.getDefault())
        val ago: String = prettyTime.format(Date(args.modelo.fecha))
        ver_anuncio_time_ago.text=ago
        ver_anuncio_title.text=args.modelo.titulo
        Log.e("profile",args.modelo.type)
        if (args.modelo.typeID!="Y3bRcyksupn35UaVIzRr"){
            ver_anuncio_postular.hide()
        }


        ver_anuncio_call.setOnClickListener {
            requireContext().callNumber(args.modelo.phone)
        }
        ver_anuncio_send_message.setOnClickListener {
            requireContext().sendMessageWhatsApp(args.modelo.phone,"Buen Dia. \n ..........")
        }
        //Fragment
        ver_anuncio_postular.setOnClickListener {

            val usuario=viewModel.getStaticDataUser()
            if(usuario==null){
                startActivity(Intent(requireContext(), AuthActivity::class.java))
            }else{
                findNavController().navigate(VerAnuncioFragmentDirections.actionVerAnuncioFragmentToCrearPostulacionFragment(args.modelo))
            }

        }
        ver_anuncio_ubicacion.setOnClickListener {
            if(args.modelo.ubicacion.latitude!=0.0){
                findNavController().navigate(VerAnuncioFragmentDirections.actionVerAnuncioFragmentToUbicationPosition(args.modelo))
            }else{
                snakBar("No contiene una ubicacion valida")
            }

        }
        //Dialog
        ver_anuncio_report.setOnClickListener {
            val usuario=viewModel.getStaticDataUser()
            if(usuario==null){
                startActivity(Intent(requireContext(), AuthActivity::class.java))
            }else{
                val newFragment = ReportarAnuncioDialog()
                val arg=Bundle()
                arg.putParcelable("modelo",args.modelo)
                newFragment.isCancelable=true
                newFragment.arguments=arg
                newFragment!!.show(
                        requireActivity().supportFragmentManager.beginTransaction(),
                        "aa"
                )
            }

        }
    }

}