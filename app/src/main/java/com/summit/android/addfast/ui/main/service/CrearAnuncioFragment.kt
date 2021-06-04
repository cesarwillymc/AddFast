/*
package com.summit.android.addfast.ui.main.service

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
import com.summit.android.addfast.repo.model.Anuncios
import com.summit.android.addfast.repo.model.departamento.UbiModel
import com.summit.android.addfast.ui.camera.CameraViewModel
import com.summit.android.addfast.ui.main.MainViewModel
import com.summit.android.addfast.ui.main.MainViewModelFactory
import com.summit.android.addfast.utils.SpinnerAdapter
import com.summit.android.addfast.utils.lifeData.Status
import com.summit.android.addfast.utils.system.SharedPreferencsTemp.Companion.getTempIntValue
import com.summit.android.addfast.utils.system.SharedPreferencsTemp.Companion.setTempIntValue
import kotlinx.android.synthetic.main.dialog_select_place.*
import kotlinx.android.synthetic.main.fragment_crear_anuncio.*
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.kodein
import org.kodein.di.generic.instance
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.io.File
import java.util.*

class CrearAnuncioFragment : BaseFragment() {
    val viewModel: MainViewModel by viewModel()
    override fun getLayout()=R.layout.fragment_crear_anuncio
    lateinit var spinnerAdapterDepartamento: SpinnerAdapter
    lateinit var spinnerAdapterProvincia: SpinnerAdapter


    //ViewModel prueba
    lateinit var spinnerAdapterCategoria: SpinnerAdapter
    lateinit var cameraViewModel: CameraViewModel
    var imagen:File?=null

    var posicionActual:UbiModel?=null
    var departamento=0
    var provincia=0
    var tipospinner=0
    //Progress bar
    lateinit var progressDialog: ProgressDialog
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        iniciarDatosSpinner()
        //ViewModel
        cameraViewModel= requireActivity().run{
            ViewModelProvider(this).get(CameraViewModel::class.java)
        }
        cameraViewModel.imageSelect.observe(viewLifecycleOwner, Observer {
            if(it!=""){
                Log.e("imagen","datos $it")
                imagen= File(it)
                Glide.with(requireContext()).load(imagen).into(crear_anuncio_img)
            }
        })
        //Porgress bar
        //Progresss Dialog
        progressDialog= ProgressDialog(requireContext())
        progressDialog.setTitle("Loading")
        progressDialog.max = 100
        progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL)
        //Listen data  LatLong and address
        viewModel.anuncioCreate.observe(viewLifecycleOwner, Observer {
            if(it!=null){
                crear_anuncio_direccion.setText(it.phone)
                posicionActual=it.ubicacion
            }
        })
        //Spinner
        spinnerAdapterDepartamento= SpinnerAdapter(requireContext())
        crear_anuncio_departamento.apply {
            adapter=spinnerAdapterDepartamento
        }
        spinnerAdapterProvincia= SpinnerAdapter(requireContext())
        crear_anuncio_provincia.apply {
            adapter=spinnerAdapterProvincia
        }

        spinnerAdapterCategoria= SpinnerAdapter(requireContext())
        crear_anuncio_tipo_anuncio.apply {
            adapter=spinnerAdapterCategoria
        }
        loadCategorias()
        loadDepartamentos()
        button.setOnClickListener {
            findNavController().navigate(CrearAnuncioFragmentDirections.actionCrearAnuncioFragmentToUbication())
        }
        crear_anuncio_img.setOnClickListener {
            findNavController().navigate(CrearAnuncioFragmentDirections.actionCrearAnuncioFragmentToGalleryFragment2())
        }
        crear_anuncio_cargar_img.setOnClickListener {
            findNavController().navigate(CrearAnuncioFragmentDirections.actionCrearAnuncioFragmentToGalleryFragment2())
        }
        crear_anuncio_crear.setOnClickListener {
            if(comprobarDatos()){
                val usuario= viewModel.getStaticDataUser()
                val modelo= Anuncios(crear_anuncio_resumen.text.toString(), Date().time,Date().time.toString(),usuario._id,usuario._id,"",usuario.phone!!,
                posicionActual!!, spinnerAdapterCategoria.lisProducts!![tipospinner].name, spinnerAdapterCategoria.lisProducts!![tipospinner].id,
                    crear_anuncio_titulo.text.toString(),
                    listOf(),0,0,"PENDIENTE" )
                subirImgAnuncio(modelo)
            }

        }
    }

    private fun iniciarDatosSpinner() {
        departamento=getTempIntValue("departamento")?:0
        provincia=getTempIntValue("provincia")?:0
        tipospinner=getTempIntValue("tipo")?:0
    }

    private fun subirImgAnuncio(modelo: Anuncios) {
        viewModel.subirImagenAnuncio(imagen!!).observe(viewLifecycleOwner, Observer {
            when (it.status) {
                Status.LOADING -> {
                    progressDialog.show()
                    progressDialog.progress = (it.progreso!!*100).toInt()
                }
                Status.SUCCESS -> {
                    crearAnuncio(it.data!!,modelo)
                }
                Status.ERROR -> {
                    progressDialog.dismiss()
                    snakBar(it.exception!!.message!!)
                    Log.e("ErrorProfile",it.exception!!.message!!)
                }
            }
        })
    }

    private fun crearAnuncio(data: String, modelo: Anuncios) {
        viewModel.crearAnuncio(modelo,data, spinnerAdapterDepartamento.lisProducts!![departamento].name, spinnerAdapterProvincia.lisProducts!![provincia].name).observe(viewLifecycleOwner, Observer {
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
                    Log.e("ErrorProfile",it.exception!!.message!!)
                }
            }
        })
    }

    private fun comprobarDatos(): Boolean {
        if(posicionActual==null){
            toast("Agrega una direccion")
            return false
        }
        if(imagen==null){
            toast("Agrega una foto")
            return false
        }
        if(crear_anuncio_titulo.isEmptyLbl()){
            return false
        }
        if(crear_anuncio_resumen.isEmptyLbl()){
            return false
        }
        return true
    }

    private fun loadCategorias() {
        viewModel.getCategorias().observe(viewLifecycleOwner, Observer {
            when (it.status) {
                Status.LOADING -> {
                }
                Status.SUCCESS -> {
                    spinnerAdapterCategoria.updateDataCategoria(it.data)

                    listenCategorias()
                }
                Status.ERROR -> {
                    Log.e("getAnunciosByCategorias",it.exception!!.message!!)
                    toast(it.exception!!.message!!)

                }

            }
        })
    }

    private fun listenCategorias() {
        crear_anuncio_tipo_anuncio.setSelection(tipospinner)
        crear_anuncio_tipo_anuncio.onItemSelectedListener= object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {}
            override fun onItemSelected(
                parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                tipospinner=position
                setTempIntValue("tipo",position)
            }
        }
    }


    private fun loadDepartamentos(){
        viewModel.getDepartamentos().observe(viewLifecycleOwner, Observer {
            if(it.isNotEmpty()){

                spinnerAdapterDepartamento.updateData(it)

                loadProvincias(it[0].id)
                Handler().postDelayed({
                    activateListen()
                },1000L)
            }
        })
    }
    private fun loadProvincias(id:String){
        viewModel.getProvincias(id).observe(viewLifecycleOwner, Observer {
            if(it.isNotEmpty()){

                spinnerAdapterProvincia.updateData(it)

            }
        })
    }

    private fun activateListen(){
            crear_anuncio_provincia.setSelection(provincia)
             crear_anuncio_departamento.setSelection(departamento)
            crear_anuncio_provincia.onItemSelectedListener= object : AdapterView.OnItemSelectedListener {
                override fun onNothingSelected(parent: AdapterView<*>?) {}
                override fun onItemSelected(
                    parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                    provincia=position
                    setTempIntValue("provincia",position)
                }
            }
            crear_anuncio_departamento.onItemSelectedListener= object : AdapterView.OnItemSelectedListener {
                override fun onNothingSelected(parent: AdapterView<*>?) {}
                override fun onItemSelected(
                    parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                    setTempIntValue("departamento",position)
                    departamento=position
                    loadProvincias(spinnerAdapterDepartamento.lisProducts?.get(departamento)!!.id)
                }
            }

    }
}
 */