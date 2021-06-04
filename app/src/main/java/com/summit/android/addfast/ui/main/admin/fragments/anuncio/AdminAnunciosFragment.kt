/*
package com.summit.android.addfast.ui.main.admin.fragments.anuncio

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
import com.summit.android.addfast.repo.model.Anuncios
import com.summit.android.addfast.ui.main.admin.AdminViewModel
import com.summit.android.addfast.ui.main.admin.AdminViewModelFactory
import com.summit.android.addfast.ui.main.user.anuncios.options.VerCateegoriasAdapter
import com.summit.android.addfast.utils.lifeData.Status
import com.summit.android.addfast.utils.setOnSingleClickListener
import kotlinx.android.synthetic.main.fragment_admin_anuncios.*
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.kodein
import org.kodein.di.generic.instance
import org.koin.androidx.viewmodel.ext.android.viewModel


class AdminAnunciosFragment : BaseFragment(),VerCateegoriasAdapter.Listener {
    val viewModel: AdminViewModel by viewModel()
    private lateinit var anunciosAdaper: VerCateegoriasAdapter
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        anunciosAdaper = VerCateegoriasAdapter(this)
        //LinearLayoutManager(requireContext(),
        //                LinearLayoutManager.HORIZONTAL,false)
        anuncios_ver_rv_admin.apply {
            layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL,false)
            setHasFixedSize(true)
            adapter = anunciosAdaper
        }
        editText_admin.addTextChangedListener {
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
        feid_edtxt_search.setOnSingleClickListener {
            val text=editText_admin.text.toString().trim()
            if (text.isEmpty()){
                loadDataPalabra("")
            }else{
                loadDataPalabra(text)
            }
        }

        loadData()

    }
    private fun loadData() {
        viewModel.getAllAnuncios().observe(viewLifecycleOwner, Observer {
            when(it.status){
                Status.LOADING ->{
                    hideKeyboard()
                }
                Status.SUCCESS ->{
                    anuncios_ver_rv_admin.show()
                    anuncios_ver_shimmer_admin.hide()
                    anunciosAdaper.updateData(it.data as MutableList<Anuncios> )
                }
                Status.ERROR ->{
                    snakBar(it.exception!!.message!!)
                    Log.e("TAG",it.exception!!.message!!)
                }
            }
        })
    }
    private fun loadDataPalabra(palabra:String) {
        if(anunciosAdaper.preciosinicial.isEmpty()){
            viewModel.getAllAnuncios().observe(viewLifecycleOwner, Observer {
                when(it.status){
                    Status.LOADING ->{
                        hideKeyboard()
                    }
                    Status.SUCCESS ->{
                        anuncios_ver_rv_admin.show()
                        anuncios_ver_shimmer_admin.hide()
                        anunciosAdaper.updateData(it.data as MutableList<Anuncios> )
                    }
                    Status.ERROR ->{
                        snakBar(it.exception!!.message!!)
                        Log.e("TAG",it.exception!!.message!!)
                    }
                }
            })
        }else{
            anunciosAdaper.searchData(palabra)
        }

    }

    override fun onclick(anuncios: Anuncios, position: Int) {
        findNavController().navigate(AdminAnunciosFragmentDirections.actionNavAdminAnunciosToAnuncioPreviewAdminFragment(anuncios))
    }
    override fun getLayout() = R.layout.fragment_admin_anuncios
}
 */