/*
package com.summit.android.addfast.ui.auth.fragment

import android.app.AlertDialog
import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.huawei.agconnect.auth.AGConnectAuth
import com.summit.android.addfast.R
import com.summit.android.addfast.base.BaseFragment
import com.summit.android.addfast.repo.model.Usuario
import com.summit.android.addfast.ui.auth.AuthViewModel
import com.summit.android.addfast.ui.auth.AuthViewModelFactory
import com.summit.android.addfast.ui.camera.CameraViewModel
import com.summit.android.addfast.ui.main.MainActivity
import com.summit.android.addfast.ui.main.admin.AdminViewModel
import com.summit.android.addfast.utils.lifeData.Status
import com.summit.android.addfast.utils.setOnSingleClickListener
import kotlinx.android.synthetic.main.fragment_register_info.*
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.kodein
import org.kodein.di.generic.instance
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.io.File


class RegisterInfoFragment : BaseFragment() {
    //Instance Data Kodein
    val viewModel: AuthViewModel by viewModel()

    //ViewModel prueba
    lateinit var cameraViewModel: CameraViewModel
    //Argumentos
    private val args:RegisterInfoFragmentArgs by navArgs()

    //Layout Base fragment
    override fun getLayout() = R.layout.fragment_register_info

    //File Image Profile
    private var imagen: File? = null


    //Progress Dialog
    lateinit var progressDialog: ProgressDialog
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        cameraViewModel= requireActivity().run{
            ViewModelProvider(this).get(CameraViewModel::class.java)
        }
        cameraViewModel.imageSelect.observe(viewLifecycleOwner, Observer {
            if(it!=""){
                Log.e("imagen","datos $it")
                imagen= File(it)
                Glide.with(requireContext()).load(imagen).into(img_rider_fr)
            }
        })
        //Progresss Dialog
        progressDialog= ProgressDialog(requireContext())
        progressDialog.setTitle("Loading")
        progressDialog.max = 100
        progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL)


        btn_register_send.setOnClickListener {
            val nombres = et_register_fullname_person.text.toString().trim()
            val dni = et_register_dni_person.text.toString().trim()
            val  ruc= et_register_ruc_company.text.toString().trim()
            val empresaname = et_register_name_company.text.toString().trim()
            if (comprobarDatos()) {
                uploadImageProfile(Usuario(nombres,dni,args.phone,empresaname,ruc,_id = args.id?:""))
            }

        }
        observeDataLogged()
        cb_register_check.setOnCheckedChangeListener { compoundButton, b ->
            if (b){
                et_register_name_company.show()
                et_register_ruc_company.show()
            }else{
                et_register_name_company.hide()
                et_register_ruc_company.hide()
            }
        }

        cargarfoto.setOnSingleClickListener {
            openFoto()
        }
        img_rider_fr.setOnSingleClickListener {
            openFoto()
        }
        //
        verifyLogged()
    }

    private fun verifyLogged() {
        if (AGConnectAuth.getInstance().currentUser != null) {
            Log.e("Usuario","LOGEADOOOOO")
            return
        }else{
            Log.e("Usuario","SINNNN   LOGEADOOOOO")
        }
    }

    private fun openFoto(){
        val opciones =
            arrayOf<CharSequence>("Tomar Foto", "Cargar Imagen")
        val alertOpciones =
            AlertDialog.Builder(requireContext())

        alertOpciones.setTitle("Seleccione una opción:")
        alertOpciones.setItems(
            opciones
        ) { dialogInterface, i ->
            try {
                when {
                    opciones[i] == "Tomar Foto" -> {
                       /* val version = readEMUIVersion()
                        Log.e("version","data $version")
                        if(version.toDouble()<10.0){

                        }else{
                            findNavController().navigate(RegisterInfoFragmentDirections.actionRegisterInfoFragmentToCameraKitHdrCaptureActivity("register"))
                        }*/
                        findNavController().navigate(RegisterInfoFragmentDirections.actionRegisterInfoFragmentToCameraFragment("register"))

                    }
                    opciones[i] == "Cargar Imagen" -> {
                        findNavController().navigate(RegisterInfoFragmentDirections.actionRegisterInfoFragmentToGalleryFragment())
                    }

                }
            } catch (e: Exception) {
                Log.e("errorcamera", e.message!!)
            }
        }

        alertOpciones.show()

    }
    private fun uploadImageProfile(usuario: Usuario) {
        viewModel.crearFotoProfileUser(imagen!!).observe(viewLifecycleOwner, Observer {
            when (it.status) {
                Status.LOADING -> {
                    progressDialog.show()
                    progressDialog.progress = (it.progreso!!).toInt()
                }
                Status.SUCCESS -> {
                    progressDialog.dismiss()
                    usuario.uriImgPerfil = it.data
                    Log.e("dataRecogida",it.data!!)
                    usuarioNuevo(usuario)
                }
                Status.ERROR -> {
                    progressDialog.dismiss()
                    snakBar(it.exception!!.message!!)
                    Log.e("ErrorProfile",it.exception!!.message!!)
                }
            }
        })
    }

    //Crear usuario en caso no exista
    private fun usuarioNuevo(usuario: Usuario) {
        viewModel.createDataUser(usuario).observe(viewLifecycleOwner, Observer {
            when (it.status) {
                Status.LOADING -> {
                    snakBar("Terminando registro")
                }
                Status.SUCCESS -> {
                    navigateToActivity(Intent(requireContext(), MainActivity::class.java))
                }
                Status.ERROR -> {
                    snakBar(it.exception!!.message!!)
                    Log.e("ErrorProfile",it.exception!!.message!!)
                }
            }
        })
    }

    private fun comprobarDatos(): Boolean {

        if(et_register_fullname_person.isEmptyLbl()){
            return false
        }
        if(et_register_dni_person.isEmptyLbl()){
            return false
        }
        if(cb_register_check.isChecked){
            if(et_register_ruc_company.isEmptyLbl()){
                return false
            }
            if(et_register_name_company.isEmptyLbl()){
                return false
            }
        }
        if (imagen==null){
            snakBar("Inserta una imagen")
            return false
        }
        return true
    }

    private fun observeDataLogged() {
        viewModel.getDataDB().observe(viewLifecycleOwner, Observer {
            if (it != null) {
                navigateToActivity(Intent(requireContext(), MainActivity::class.java))
            }
        })
    }
}
 */