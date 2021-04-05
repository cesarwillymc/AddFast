package com.summit.android.addfast.ui.main.admin.fragments.promociones

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.summit.android.addfast.R
import com.summit.android.addfast.base.BaseFragment
import com.summit.android.addfast.repo.model.Promociones
import com.summit.android.addfast.ui.main.admin.AdminViewModel
import com.summit.android.addfast.ui.main.admin.AdminViewModelFactory
import com.summit.android.addfast.utils.lifeData.Status
import com.summit.android.addfast.utils.system.SharedPreferencsTemp
import kotlinx.android.synthetic.main.fragment_promociones.*
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.kodein
import org.kodein.di.generic.instance
import org.koin.androidx.viewmodel.ext.android.viewModel

class PromocionesFragment : BaseFragment(),PromocionesAdminAdapter.Listener {

    val viewModel: AdminViewModel by viewModel()
    private lateinit var adapterPostulacion:PromocionesAdminAdapter
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        SharedPreferencsTemp.clearAllTempShared()
        adapterPostulacion = PromocionesAdminAdapter(this)
        promociones_ver_rv_admin.apply {
            layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL,false)
            adapter = adapterPostulacion
        }
        loadData()
        floatingActionButton_add_promo.setOnClickListener {
            findNavController().navigate(PromocionesFragmentDirections.actionPromocionesFragmentToCrearPromocion())
        }
    }
    private fun loadData() {
        viewModel.getAllPromociones().observe(viewLifecycleOwner, Observer {
            when(it.status){
                Status.LOADING ->{

                    hideKeyboard()
                }
                Status.SUCCESS ->{
                    promociones_ver_rv_admin.show()
                    promociones_ver_shimmer_admin.hide()
                    adapterPostulacion.updateData(it.data as MutableList<Promociones> )
                }
                Status.ERROR ->{
                    snakBar(it.exception!!.message!!)
                    Log.e("TAG",it.exception.message!!)
                }
            }
        })
    }
    override fun getLayout() = R.layout.fragment_promociones
    override fun onclick(anuncios: Promociones, position: Int) {
        findNavController().navigate(PromocionesFragmentDirections.actionPromocionesFragmentToVerPromocionFragment(anuncios))
    }
}