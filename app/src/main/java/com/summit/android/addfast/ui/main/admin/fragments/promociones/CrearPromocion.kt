/*
package com.summit.android.addfast.ui.main.admin.fragments.promociones

import android.app.ProgressDialog
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.View
import android.widget.AdapterView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.summit.android.addfast.R
import com.summit.android.addfast.base.BaseFragment
import com.summit.android.addfast.repo.model.Promociones
import com.summit.android.addfast.ui.camera.CameraViewModel
import com.summit.android.addfast.ui.main.admin.AdminViewModel
import com.summit.android.addfast.ui.main.admin.AdminViewModelFactory
import com.summit.android.addfast.utils.lifeData.Status
import com.summit.android.addfast.utils.system.SharedPreferencsTemp
import com.summit.android.addfast.utils.system.SharedPreferencsTemp.Companion.setTempIntValue
import kotlinx.android.synthetic.main.fragment_crear_promocion.*
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.kodein
import org.kodein.di.generic.instance
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.io.File
import java.util.*


class CrearPromocion : BaseFragment() {

    val viewModel: AdminViewModel by viewModel()
    //ViewModel prueba
    lateinit var spinnerAdapterCategoria: SpinnerAnunciosAdapter
    lateinit var cameraViewModel: CameraViewModel
    var imagen:File?=null
    var posicionSpinner=0

    //Progress bar
    lateinit var progressDialog: ProgressDialog
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        cameraViewModel= requireActivity().run{
            ViewModelProvider(this).get(CameraViewModel::class.java)
        }
        //Porgress bar
        //Progresss Dialog
        progressDialog= ProgressDialog(requireContext())
        progressDialog.setTitle("Loading")
        progressDialog.max = 100
        progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL)
        posicionSpinner= SharedPreferencsTemp.getTempIntValue("anunciossspiner") ?:0
        cameraViewModel.imageSelect.observe(viewLifecycleOwner, Observer {
            if(it!=""){
                Log.e("imagen","datos $it")
                imagen= File(it)
                Glide.with(requireContext()).load(imagen).into(crear_promo_img)
            }
        })
        spinnerAdapterCategoria= SpinnerAnunciosAdapter(requireContext())
        crear_promo_spinner.apply {
            adapter=spinnerAdapterCategoria
        }
        loadSpinner()

    }
    private fun listenCategorias() {
        crear_promo_spinner.setSelection(posicionSpinner)
        crear_promo_spinner.onItemSelectedListener= object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {}
            override fun onItemSelected(
                parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                posicionSpinner=position
                setTempIntValue("anunciossspiner",position)
            }
        }
    }
    private fun loadSpinner() {
        viewModel.getAllAnunciosPost().observe(viewLifecycleOwner, Observer {
            when(it.status){
                Status.LOADING ->{
                    hideKeyboard()
                }
                Status.SUCCESS ->{
                    spinnerAdapterCategoria.updateData(it.data)
                    listenCategorias()
                    habiliarBotoner()
                }
                Status.ERROR ->{
                    snakBar(it.exception!!.message!!)
                    Log.e("TAG",it.exception!!.message!!)
                }
            }
        })
    }

    private fun habiliarBotoner() {
        crear_promo_btn.setOnClickListener {
            if(imagen!=null){
                val model= Promociones("",true,spinnerAdapterCategoria.getData(posicionSpinner)!!.id,"",spinnerAdapterCategoria.getData(posicionSpinner)!!.titulo,Date().time)
                subirImgAnuncio(model)
            }else{
                toast("Es necesario una imagen")
            }
        }
        crear_promo_img.setOnClickListener {
            findNavController().navigate(CrearPromocionDirections.actionCrearPromocionToGalleryFragment3())
        }
    }
    private fun subirImgAnuncio(modelo: Promociones) {
        viewModel.subirImagenPromocion(imagen!!).observe(viewLifecycleOwner, Observer {
            when (it.status) {
                Status.LOADING -> {
                    progressDialog.show()
                    progressDialog.progress = (it.progreso!!*100).toInt()
                }
                Status.SUCCESS -> {
                    crearPromocion(it.data!!,modelo)
                }
                Status.ERROR -> {
                    progressDialog.dismiss()
                    snakBar(it.exception!!.message!!)
                    Log.e("ErrorProfile",it.exception!!.message!!)
                }
            }
        })
    }
    private fun crearPromocion(
        data: String,
        modelo: Promociones
    ) {
        viewModel.crearPromocion(modelo,data).observe(viewLifecycleOwner, Observer {
            when (it.status) {
                Status.LOADING -> {
                    toast("Cargando")
                }
                Status.SUCCESS -> {

                    progressDialog.dismiss()
                    toast("Completado satisfatoriamente")
                    Handler().postDelayed({
                        findNavController().navigateUp()
                    },1000L)
                }
                Status.ERROR -> {
                    progressDialog.dismiss()
                    snakBar(it.exception!!.message!!)
                    Log.e("ErrorProfile",it.exception.message!!)
                }
            }
        })
    }

    override fun getLayout()= R.layout.fragment_crear_promocion
}
 */