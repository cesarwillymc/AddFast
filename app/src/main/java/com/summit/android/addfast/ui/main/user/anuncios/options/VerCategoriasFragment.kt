package com.summit.android.addfast.ui.main.user.anuncios.options

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.summit.android.addfast.R
import com.summit.android.addfast.base.BaseFragment
import com.summit.android.addfast.repo.model.Anuncios
import com.summit.android.addfast.ui.auth.fragment.InicioFragmentDirections
import com.summit.android.addfast.ui.main.MainActivity
import com.summit.android.addfast.ui.main.MainViewModel
import com.summit.android.addfast.ui.main.MainViewModelFactory
import com.summit.android.addfast.ui.main.user.anuncios.CategoriasAdaper
import com.summit.android.addfast.utils.lifeData.Status
import kotlinx.android.synthetic.main.fragment_ver_categorias.*
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.kodein
import org.kodein.di.generic.instance

class VerCategoriasFragment : BaseFragment(), KodeinAware,VerCateegoriasAdapter.Listener {
    private lateinit var viewModel: MainViewModel
    override val kodein by kodein()
    private val factory: MainViewModelFactory by instance()
    override fun getLayout()=R.layout.fragment_ver_categorias
    val args:VerCategoriasFragmentArgs by navArgs()
    private lateinit var categoriasAdaper: VerCateegoriasAdapter
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = requireActivity().run {
            ViewModelProvider(this, factory).get(MainViewModel::class.java)
        }
        categoriasAdaper = VerCateegoriasAdapter(this)
        //LinearLayoutManager(requireContext(),
        //                LinearLayoutManager.HORIZONTAL,false)
        categorias_ver_rv.apply {
            layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL,false)
            setHasFixedSize(true)
            adapter = categoriasAdaper
        }
        loadData()
    }

    private fun loadData() {
        viewModel.getAllAnunciosByCategorias(args.idCategoria).observe(viewLifecycleOwner, Observer {
            when(it.status){
                Status.LOADING ->{
                    snakBar("Enviado")
                    hideKeyboard()
                }
                Status.SUCCESS ->{
                    categorias_ver_rv.show()
                    categorias_ver_shimmer.hide()
                    categoriasAdaper.updateData(it.data as MutableList<Anuncios> )
                }
                Status.ERROR ->{
                    snakBar(it.exception!!.message!!)
                    Log.e("TAG",it.exception!!.message!!)
                }
            }
        })
    }

    override fun onclick(anuncios: Anuncios, position: Int) {
        findNavController().navigate(VerCategoriasFragmentDirections.actionVerCategoriasFragment2ToVerAnuncioFragment(anuncios))
    }

}