package com.summit.android.addfast.ui.auth.fragment

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavDirections
import androidx.navigation.fragment.findNavController
import com.huawei.agconnect.auth.AGConnectAuth
import com.summit.android.addfast.R
import com.summit.android.addfast.base.BaseFragment
import com.summit.android.addfast.ui.auth.AuthViewModel
import com.summit.android.addfast.ui.auth.AuthViewModelFactory
import com.summit.android.addfast.ui.main.MainActivity
import com.summit.android.addfast.ui.main.admin.AdminViewModel
import com.summit.android.addfast.utils.lifeData.Status
import com.summit.android.addfast.utils.setOnSingleClickListener
import kotlinx.android.synthetic.main.fragment_inicio.*
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.kodein
import org.kodein.di.generic.instance
import org.koin.androidx.viewmodel.ext.android.viewModel


class InicioFragment : BaseFragment() {

    //Instance Data Kodein
    val viewModel: AuthViewModel by viewModel()

    //Layout Base fragment
    override fun getLayout() = R.layout.fragment_inicio



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


      /*  lbl_inicio_invitado.setOnSingleClickListener {
            loggedPrivateAnonimous()
        }*/
        lbl_inicio_send.setOnSingleClickListener {
            val numero= lbl_inicio_telefono.text.toString().trim()
            if (comprobarDatos(numero)){
                signInNumberPhone("51",numero)
            }
        }
        try{
            AGConnectAuth.getInstance().signOut()
        }catch (e:Exception){

        }

    }

    //Comprobar datos para el auth
    private fun comprobarDatos(numero: String):Boolean{
        if (lbl_inicio_telefono.isEmptyLbl()){
            return false
        }
        if(numero.length!=9){
            snakBar("Numero incorrecto")
            return false
        }
        return true
    }

    //Inicio de sesion mediante numero de celualr
    private fun signInNumberPhone(code:String,phoneNumber:String){
        viewModel.sendMessageLogged(code,phoneNumber).observe(viewLifecycleOwner, Observer {
            when(it.status){
                Status.LOADING ->{
                    snakBar("Enviado")
                    hideKeyboard()
                }
                Status.SUCCESS ->{
                    if(it.data!!){
                        navigateToActivity(Intent(requireContext(), MainActivity::class.java))
                    }else{
                        InicioFragmentDirections.actionInicioFragmentToConfirmCodeFragment(phoneNumber,code).load()
                    }

                }
                Status.ERROR ->{
                    snakBar(it.exception!!.message!!)
                    Log.e("TAG",it.exception!!.message!!)
                }
            }
        })

    }
    private fun NavDirections.load(){
        try{
            findNavController().navigate(this)
        }catch (e:Exception){
            Log.e("NavDirections","No puedo inicializar la ventana")
        }
    }
    //Inicio de sesion anonimamente
    private fun loggedPrivateAnonimous(){
        viewModel.loggedInvitado().observe(viewLifecycleOwner, Observer {
            when(it.status){
                Status.LOADING ->{
                    snakBar("Enviado")
                }
                Status.SUCCESS ->{
                    navigateToActivity(Intent(requireContext(), MainActivity::class.java))
                }
                Status.ERROR ->{
                    snakBar(it.exception!!.message!!)
                    Log.e("TAG",it.exception!!.message!!)
                }
            }
        })
    }




}