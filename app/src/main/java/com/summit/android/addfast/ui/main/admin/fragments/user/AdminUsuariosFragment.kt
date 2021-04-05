package com.summit.android.addfast.ui.main.admin.fragments.user

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.summit.android.addfast.R
import com.summit.android.addfast.base.BaseFragment
import com.summit.android.addfast.repo.model.Usuario
import com.summit.android.addfast.ui.main.admin.AdminViewModel
import com.summit.android.addfast.ui.main.admin.AdminViewModelFactory
import com.summit.android.addfast.utils.lifeData.Status
import com.summit.android.addfast.utils.setOnSingleClickListener
import kotlinx.android.synthetic.main.fragment_admin_anuncios.*
import kotlinx.android.synthetic.main.fragment_admin_usuarios.*
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.kodein
import org.kodein.di.generic.instance
import org.koin.androidx.viewmodel.ext.android.viewModel


class AdminUsuariosFragment : BaseFragment(), UsuariosAdapter.Listener {
    val viewModel: AdminViewModel by viewModel()
    lateinit var adapterPostulacion:UsuariosAdapter
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        adapterPostulacion = UsuariosAdapter(this)
        usuarios_ver_rv_admin.apply {
            layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL,false)
            adapter = adapterPostulacion
        }


        editText_admin_user.addTextChangedListener {
            try{
                val text=it.toString().trim()
                if (text.isEmpty()){
                    loadDataPalabra("")
                }else{
                    loadDataPalabra(text)
                }
            }catch (e: Exception){
                loadDataPalabra("")
            }
        }
        usuarios_search_admin.setOnSingleClickListener {
            val text=editText_admin_user.text.toString().trim()
            if (text.isEmpty()){
                loadDataPalabra("")
            }else{
                loadDataPalabra(text)
            }
        }

        loadData()
    }

    override fun getLayout()= R.layout.fragment_admin_usuarios
    private fun loadData() {
        viewModel.getAllPostulantes().observe(viewLifecycleOwner, Observer {
            when(it.status){
                Status.LOADING ->{
                    hideKeyboard()
                }
                Status.SUCCESS ->{
                    usuarios_ver_rv_admin.show()
                    usuarios_ver_shimmer_admin.hide()
                    adapterPostulacion.updateData(it.data as MutableList<Usuario> )
                }
                Status.ERROR ->{
                    snakBar(it.exception!!.message!!)
                    Log.e("TAG",it.exception!!.message!!)
                }
            }
        })
    }
    private fun loadDataPalabra(palabra:String) {
        if(adapterPostulacion.preciosinicial.isEmpty()){
            viewModel.getAllPostulantesByPalabra(palabra).observe(viewLifecycleOwner, Observer {
                when(it.status){
                    Status.LOADING ->{
                        hideKeyboard()
                    }
                    Status.SUCCESS ->{
                        usuarios_ver_rv_admin.show()
                        usuarios_ver_shimmer_admin.hide()
                        adapterPostulacion.updateData(it.data as MutableList<Usuario> )
                    }
                    Status.ERROR ->{
                        snakBar(it.exception!!.message!!)
                        Log.e("TAG",it.exception!!.message!!)
                    }
                }
            })

        }else{
            adapterPostulacion.searchData(palabra)
        }

    }

    override fun onclick(anuncios: Usuario, position: Int) {
        findNavController().navigate(AdminUsuariosFragmentDirections.actionNavAdminUsuariosToPreviewPostulacionAdminFragment(anuncios))
    }

}