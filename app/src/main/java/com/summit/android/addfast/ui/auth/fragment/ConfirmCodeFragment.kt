package com.summit.android.addfast.ui.auth.fragment

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavDirections
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.summit.android.addfast.R
import com.summit.android.addfast.base.BaseFragment
import com.summit.android.addfast.ui.auth.AuthViewModel
import com.summit.android.addfast.ui.auth.AuthViewModelFactory
import com.summit.android.addfast.ui.main.MainActivity
import com.summit.android.addfast.ui.main.admin.AdminActivity
import com.summit.android.addfast.utils.lifeData.Status
import com.summit.android.addfast.utils.setOnSingleClickListener
import kotlinx.android.synthetic.main.fragment_confirm_code.*
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.kodein
import org.kodein.di.generic.instance


class ConfirmCodeFragment : BaseFragment(),KodeinAware {
    //Instance Data Kodein
    override val kodein: Kodein by kodein()
    lateinit var viewModel: AuthViewModel
    private val factory: AuthViewModelFactory by instance()

    //Argumentos
    private val args:ConfirmCodeFragmentArgs by navArgs()

    //Layout Base fragment
    override fun getLayout()= R.layout.fragment_confirm_code

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel= run{
            ViewModelProvider(this,factory).get(AuthViewModel::class.java)
        }
        bideoDatos()
        btn_validate_number.setOnSingleClickListener{
            val numero=et_cellphone__validate.text.toString().trim()
            if (comprobarDatos(numero)){
                verificarCodigoAccess(numero)
            }
        }
        observeDataLogged()

    }

    private fun bideoDatos() {
        comfirm_code_peru.text= "+"+args.codeCountry
        comfirm_code_number_peru.text= args.numberPhone
    }

    //Observa si hay cambios en la db
    private fun observeDataLogged(){
        viewModel.getDataDB().observe(viewLifecycleOwner, Observer {
            if (it!=null){
                if(it.admin!!){
                    navigateToActivity(Intent(requireContext(), AdminActivity::class.java))
                }else{
                    navigateToActivity(Intent(requireContext(), MainActivity::class.java))
                }

            }
        })
    }
    private fun comprobarDatos(numero: String):Boolean{
        if(et_cellphone__validate.isEmptyLbl()){
            return false
        }
        if (numero.length!=6){
            et_cellphone__validate.requestFocus()
            et_cellphone__validate.error="Codigo invalido"
            return false
        }
        return true
    }

    //Verifica codigos de acceso
    private fun verificarCodigoAccess(codigo:String){
        viewModel.verifyMessageLogged(args.codeCountry,args.numberPhone,codigo).observe(viewLifecycleOwner, Observer {
            when(it.status){
                Status.LOADING ->{
                    hideKeyboard()
                    snakBar("Cargando")
                }
                Status.SUCCESS ->{
                    if(it.data!=null){
                        ConfirmCodeFragmentDirections.actionConfirmCodeFragmentToRegisterInfoFragment(it.data!!,args.numberPhone).load()
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
}