package com.summit.android.addfast.ui.main.user.postulaciones

import android.app.Activity
import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.summit.android.addfast.R
import com.summit.android.addfast.base.BaseFragment
import com.summit.android.addfast.repo.model.Postulacion
import com.summit.android.addfast.ui.main.MainViewModel
import com.summit.android.addfast.ui.main.MainViewModelFactory
import com.summit.android.addfast.utils.lifeData.Status
import com.summit.android.addfast.utils.shared.FileUtils
import kotlinx.android.synthetic.main.fragment_crear_postulacion.*
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.kodein
import org.kodein.di.generic.instance
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.io.File
import java.util.*

class CrearPostulacionFragment : BaseFragment() {
    val viewModel: MainViewModel by viewModel()
    private var pdfUri: File?=null
    val args:CrearPostulacionFragmentArgs by navArgs()
    override fun getLayout()=R.layout.fragment_crear_postulacion

    //Progress bar
    lateinit var progressDialog: ProgressDialog


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //Porgress dialog
        progressDialog= ProgressDialog(requireContext())
        progressDialog.setTitle("Loading")
        progressDialog.max = 100
        progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL)
        //Setear titulo
        crear_postulacion_titulo.text=args.modelo.titulo
        crear_postulacion_cargar.setOnClickListener {
            val mRequestFileIntent = Intent(Intent.ACTION_GET_CONTENT)
            mRequestFileIntent.type = "*/*"
            startActivityForResult(Intent.createChooser(mRequestFileIntent,"Seleccione una app para elegir el pdf"), 20)
        }
        crear_postulacion_postular.setOnClickListener {
            if(comprobarDatos()){
                val user=viewModel.getStaticDataUser()
                val modelo=Postulacion(
                        crear_postulacion_diferencia.text.toString(),
                        "ENVIADO",
                        Date().time,
                        "",
                        args.modelo.id,
                        args.modelo.idempresa,
                        user._id,
                        args.modelo.titulo,
                        args.modelo.descripcion,
                        user.name!!,
                        crear_postulacion_phone.text.toString(),
                        crear_postulacion_cv.text.toString(),
                        "",
                        user.uriImgPerfil!!,
                        crear_postulacion_especialidad.text.toString(),
                        args.modelo.img
                )
                enviarPDFPostulacion(modelo)
            }
        }
    }

    private fun enviarPDFPostulacion(modelo: Postulacion) {
        viewModel.subirCurriculumPostulacion(pdfUri!!).observe(viewLifecycleOwner, androidx.lifecycle.Observer {
            when (it.status) {
                Status.LOADING -> {
                    progressDialog.show()
                    progressDialog.progress = (it.progreso!!*100).toInt()
                }
                Status.SUCCESS -> {

                    crearPostulacion(it.data!!,modelo)
                }
                Status.ERROR -> {
                    progressDialog.dismiss()
                    snakBar(it.exception!!.message!!)
                    Log.e("ErrorProfile",it.exception!!.message!!)
                }
            }
        })
    }
    private fun crearPostulacion(path:String,modelo: Postulacion) {
        viewModel.crearPostulacion(modelo,path).observe(viewLifecycleOwner, androidx.lifecycle.Observer {
            when (it.status) {
                Status.LOADING -> {
                    toast("Enviando")
                }
                Status.SUCCESS -> {
                    progressDialog.dismiss()
                    toast("Creado correctamente")
                    Handler().postDelayed({
                        findNavController().navigateUp()
                    },500L)
                }
                Status.ERROR -> {
                    progressDialog.dismiss()
                    snakBar(it.exception!!.message!!)
                    Log.e("ErrorProfile",it.exception!!.message!!)
                }
            }
        })
    }

    private fun comprobarDatos(): Boolean {
        if(crear_postulacion_cv.isEmptyLbl()){
            return false
        }
        if(crear_postulacion_diferencia.isEmptyLbl()){
            return false
        }
        if(crear_postulacion_especialidad.isEmptyLbl()){
            return false
        }
        if(crear_postulacion_phone.isEmptyLbl()){
            return false
        }
        if (crear_postulacion_phone.text.toString().length!=9){
            crear_postulacion_phone.error="Numero no valido"
            crear_postulacion_phone.requestFocus()
            return false
        }
        if (pdfUri==null){
            toast("No ingresaste tu curriculum, es necesario que lo hagas")
        }
        return true
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            when (requestCode) {
                20-> try{
                    val datos= FileUtils().getPath(requireContext(), data!!.data!!)!!
                    Handler().postDelayed({
                        pdfUri= File(datos)
                        Glide.with(requireContext()).load(R.drawable.loadcomplete).into(crear_postulacion_img)
                    },1000L)
                }catch (e:Exception){
                    Log.e("errorPdf", e.message!!)
                    snakBar("Elige otro gestor de archivos para elegir el documento")
                }
            }
        }

    }
}