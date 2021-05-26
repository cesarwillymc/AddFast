package com.summit.android.addfast.ui.main.user.profile

import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.View
import androidx.appcompat.app.AlertDialog
import com.bumptech.glide.Glide
import com.summit.android.addfast.R
import com.summit.android.addfast.base.BaseFragment
import com.summit.android.addfast.repo.model.Usuario
import com.summit.android.addfast.ui.auth.AuthActivity
import com.summit.android.addfast.ui.main.MainActivity
import com.summit.android.addfast.ui.main.MainViewModel
import com.summit.android.addfast.utils.setOnSingleClickListener
import com.summit.android.addfast.utils.system.SharedPreferencsManager.Companion.clearAllManagerShared
import kotlinx.android.synthetic.main.fragment_profile.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class Profile : BaseFragment() {

    val viewModel: MainViewModel by viewModel()
    private var usuario: Usuario? = null
    override fun getLayout(): Int = R.layout.fragment_profile


    lateinit var dialog: ProgressDialog

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        dialog = ProgressDialog(requireContext())
        dialog.setMessage("Cerrando sesion")


        /*viewodel= requireActivity().run {
            ViewModelProvider(this).get(ViewModelMain::class.java)
        }
        viewodel.imageSelect.observe(viewLifecycleOwner, androidx.lifecycle.Observer {
            if (it!=""){
                setImageView( File(it) )
            }
        })*/
        profile_image.setOnSingleClickListener {
            // imageClick()
        }
        changeImage.setOnSingleClickListener {
            //imageClick()
        }
        viewModel.getUserData.observe(viewLifecycleOwner, androidx.lifecycle.Observer {
            if (it != null) {
                lbl_logout.text = "CERRAR SESION"
                Log.e("usuario", it.toString())
                usuario = it
                changeData()
            } else {
                lbl_logout.text = "INICIAR SESION"
            }
        })
        lbl_logout.setOnSingleClickListener {
            if(usuario==null){
               /* viewModel.crearAnunciodata("lima","lima").observe(viewLifecycleOwner, androidx.lifecycle.Observer {
                    when(it.status){
                        Status.LOADING ->{
                            snakBar("Enviado")
                        }
                        Status.SUCCESS ->{

                            Log.e("profile","complerado")
                            snakBar("Completado")
                        }
                        Status.ERROR ->{
                            snakBar(it.exception!!.message!!)
                            Log.e("profile",it.exception!!.message!!)
                        }
                    }
                })*/
                startActivity(Intent(requireContext(),AuthActivity::class.java))
            }else{
                cerrarsesion()
            }
        }

    }

    private fun cerrarsesion() {
        dialog.show()
        Handler()
            .postDelayed({
                viewModel.deleteUser()
                clearAllManagerShared()
                startActivity(Intent(requireContext(), MainActivity::class.java))
                requireActivity().finish()
                dialog.dismiss()
            },2000)
    }

    /* private fun setImageView(data: File) {
         viewModel.changeImage(getSomeStringValue(PREF_ID_USER)!!,data,"profileImg" ).observe(viewLifecycleOwner, androidx.lifecycle.Observer {
             when(it){
                 is Resource.Loading->{
                     snakBar("Cargando")
                 }
                 is Resource.Success->{
                     viewodel.imageSelect.postValue("")

                     usuario!!.profile= it.data.message

                         snakBar("Los datos se cargaron correctamente")
                      viewModel.updateUser(usuario!!)




                 }
                 is Resource.Failure->{
                     viewodel.imageSelect.postValue("")

                     Log.e("photo",it.exception.message!!)
                     toast(it.exception.message!!)
                 }
             }
         })
     }*/

    private fun imageClick() {
        val opciones =
            arrayOf<CharSequence>("Tomar Foto", "Cargar Imagen")
        val alertOpciones =
            AlertDialog.Builder(requireContext())

        alertOpciones.setTitle("Seleccione una opción:")
        alertOpciones.setItems(
            opciones
        ) { dialogInterface, i ->
            try {
                if (opciones[i] == "Tomar Foto") {
                    //findNavController().navigate(ProfileDirections.actionNavPerfilToCameraFragment())
                } else {
                    //findNavController().navigate(ProfileDirections.actionNavPerfilToGalleryFragment2())
                }
            } catch (e: Exception) {
                Log.e("errorcamera", e.message!!)
            }
        }

        alertOpciones.show()

    }

    private fun changeData() {
        Glide.with(requireContext()).load(usuario!!.uriImgPerfil).into(profile_image)
        phone_lbl.text = usuario!!.phone
        name_lbl.text = usuario!!.name
        if (usuario!!.ruc == "") {
            ruc_lbl.hide()
            nameEmpresa_lbl.hide()
        } else {
            ruc_lbl.text = usuario!!.ruc
            nameEmpresa_lbl.text = usuario!!.nameEmpresa
        }

    }


}
