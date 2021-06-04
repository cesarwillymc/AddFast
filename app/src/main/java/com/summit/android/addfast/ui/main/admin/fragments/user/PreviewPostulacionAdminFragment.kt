/*
package com.summit.android.addfast.ui.main.admin.fragments.user

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.summit.android.addfast.R
import com.summit.android.addfast.base.BaseFragment
import com.summit.android.addfast.ui.main.admin.AdminViewModel
import com.summit.android.addfast.ui.main.admin.AdminViewModelFactory
import com.summit.android.addfast.utils.callNumber
import com.summit.android.addfast.utils.lifeData.Status
import com.summit.android.addfast.utils.sendMessageWhatsApp
import kotlinx.android.synthetic.main.fragment_preview_postulacion_admin.*
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.kodein
import org.kodein.di.generic.instance
import org.koin.androidx.viewmodel.ext.android.viewModel


class PreviewPostulacionAdminFragment : BaseFragment() {
    val viewModel: AdminViewModel by viewModel()
    override fun getLayout()=R.layout.fragment_preview_postulacion_admin
    val args:PreviewPostulacionAdminFragmentArgs by navArgs()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)



        bindeodeDatos()
    }
    var isAcctiveCCount:Boolean=false
    var isAdmin:Boolean=false
    private fun bindeodeDatos() {
        Glide.with(requireContext()).load(args.model.uriImgPerfil).into(profile_admin_img)
        profile_admin_call.setOnClickListener {
            requireContext().callNumber(args.model.phone.toString())
        }
        profile_admin_message.setOnClickListener {
            val user= viewModel.getStaticDataUser()
            requireContext().sendMessageWhatsApp(args.model.phone.toString(), "Hola soy de ${user.name} ")
        }

        profile_admin_name.text=args.model.name
        profile_admin_reportes.text="${args.model.reportes} reportes"
        profile_admin_estado.text= if(args.model.accountactivate){"Cuenta activada"} else {"Cuenta desactivada"}
        profile_admin_empresa.text=if(args.model.nameEmpresa==""){"No es una empresa"} else {args.model.nameEmpresa}
        isAcctiveCCount=args.model.accountactivate
        isAdmin=args.model.admin!!
        if(args.model.accountactivate){
            profile_admin_account.text="DESACTIVAR CUENTA"
        }else{
            profile_admin_account.text="ACTIVAR CUENTA"
        }
        if(args.model.admin!!){
            profile_admin_hacer_admin.text="QUITAR ADMIN"
        }else{
            profile_admin_hacer_admin.text="PONER ADMIN"
        }
        profile_admin_account.setOnClickListener {
            if(isAcctiveCCount){
                disableAccount()
            }else{
                activeAccount()
            }
        }
        profile_admin_hacer_admin.setOnClickListener {
            if(isAdmin){
                disableAdmin()
            }else{
                addAdmin()
            }
        }
    }

    private fun disableAdmin() {
        viewModel.disableAdmin(args.model._id).observe(viewLifecycleOwner, Observer {
            when (it.status) {
                Status.LOADING -> {
                }
                Status.SUCCESS -> {
                    profile_admin_hacer_admin.text="PONER ADMIN"
                    isAdmin=!isAdmin
                    toast("Se cambio el estado")
                }
                Status.ERROR -> {
                    toast(it.exception!!.message!!)
                }
            }
        })
    }
    private fun disableAccount() {
        viewModel.disableAccount(args.model._id).observe(viewLifecycleOwner, Observer {
            when (it.status) {
                Status.LOADING -> {
                }
                Status.SUCCESS -> {
                    profile_admin_account.text="ACTIVAR CUENTA"
                    isAcctiveCCount=!isAcctiveCCount
                    toast("Se cambio el estado")
                }
                Status.ERROR -> {
                    toast(it.exception!!.message!!)
                }
            }
        })
    }
    private fun addAdmin() {
        viewModel.addAdmin(args.model._id).observe(viewLifecycleOwner, Observer {
            when (it.status) {
                Status.LOADING -> {
                }
                Status.SUCCESS -> {
                    profile_admin_hacer_admin.text="QUITAR ADMIN"
                    isAdmin=!isAdmin
                    toast("Se cambio el estado")
                }
                Status.ERROR -> {
                    toast(it.exception!!.message!!)
                }
            }
        })
    }
    private fun activeAccount() {
        viewModel.activeAccount(args.model._id).observe(viewLifecycleOwner, Observer {
            when (it.status) {
                Status.LOADING -> {
                }
                Status.SUCCESS -> {
                    profile_admin_account.text="DESACTIVAR CUENTA"
                    isAcctiveCCount=!isAcctiveCCount
                    toast("Se cambio el estado")
                }
                Status.ERROR -> {
                    toast(it.exception!!.message!!)
                }
            }
        })
    }

}

 */