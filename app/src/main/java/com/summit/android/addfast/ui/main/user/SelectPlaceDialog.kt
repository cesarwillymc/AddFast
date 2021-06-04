/*
package com.summit.android.addfast.ui.main.user

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.summit.android.addfast.R
import com.summit.android.addfast.repo.model.departamento.UbicacionModel
import com.summit.android.addfast.ui.main.MainViewModel
import com.summit.android.addfast.ui.main.MainViewModelFactory
import com.summit.android.addfast.utils.SpinnerAdapter
import kotlinx.android.synthetic.main.dialog_select_place.*
import org.jetbrains.anko.backgroundColor
import org.jetbrains.anko.support.v4.toast
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.kodein
import org.kodein.di.generic.instance
import org.koin.androidx.viewmodel.ext.android.viewModel


class SelectPlaceDialog : DialogFragment() {
    val viewModel: MainViewModel by viewModel()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {


        return inflater.inflate(R.layout.dialog_select_place, container, false)
    }
    lateinit var spinnerAdapterDepartamento: SpinnerAdapter
    lateinit var spinnerAdapterProvincia: SpinnerAdapter
    var vista:View?=null
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        vista=view
        spinnerAdapterDepartamento= SpinnerAdapter(requireContext())
        spionner_departamento.apply {
            adapter=spinnerAdapterDepartamento
        }
        spinnerAdapterProvincia= SpinnerAdapter(requireContext())
        spionner_provincia.apply {
            adapter=spinnerAdapterProvincia
        }
        loadDepartamentos()

        categorias_search_dialog.setOnClickListener {
            if (posicionProvincia!=null){
                viewModel.updateUbicacionAppDb(UbicacionModel(spinnerAdapterDepartamento.lisProducts?.get(posicionDepartamento!!)!!.name,
                        spinnerAdapterProvincia.lisProducts?.get(posicionProvincia!!)!!.name,0))
                dismiss()
            }else{
                toast("No cargaron los datos")
            }
        }
    }
    var posicionDepartamento:Int?=null
    var posicionProvincia:Int?=null
    private fun loadDepartamentos(){
        viewModel.getDepartamentos().observe(this, Observer {
            if(it.isNotEmpty()){
                posicionDepartamento=0
                spinnerAdapterDepartamento.updateData(it)
                loadProvincias(it[0].id)
                activateListen()
            }
        })
    }
    private fun loadProvincias(id:String){
        viewModel.getProvincias(id).observe(this, Observer {
            if(it.isNotEmpty()){
                posicionProvincia=0
                spinnerAdapterProvincia.updateData(it)

            }
        })
    }


    private fun activateListen(){
        spionner_provincia.onItemSelectedListener= object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {}
            override fun onItemSelected(
                    parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                    posicionProvincia=position
            }
        }
        spionner_departamento.onItemSelectedListener= object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {}
            override fun onItemSelected(
                    parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                posicionDepartamento=position
                loadProvincias(spinnerAdapterDepartamento.lisProducts?.get(posicionDepartamento!!)!!.id)

            }
        }
    }
    override fun onStart() {
        super.onStart()
        dialog?.window?.setLayout(ConstraintLayout.LayoutParams.MATCH_PARENT, ConstraintLayout.LayoutParams.WRAP_CONTENT)
    }
    private var positionAdapter=0

}
 */