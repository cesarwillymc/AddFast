package com.summit.dynamicfeatures.navhost.dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.DialogFragment
import com.summit.android.addfast.app.MyApp
import com.summit.core.network.model.departamento.UbicacionModel
import com.summit.dynamicfeatures.navhost.NavHostViewModel
import com.summit.dynamicfeatures.navhost.databinding.DialogSelectPlaceBinding
import com.summit.dynamicfeatures.navhost.dialog.di.DaggerPlaceComponent
import com.summit.dynamicfeatures.navhost.dialog.di.DialogSelectPlaceModule
import javax.inject.Inject


class SelectPlaceDialog : DialogFragment() {
    lateinit var spinnerAdapterDepartamento: SpinnerAdapter
    lateinit var spinnerAdapterProvincia: SpinnerAdapter
    lateinit var binding: DialogSelectPlaceBinding

    var posicionDepartamento: Int? = null
    var posicionProvincia: Int? = null

    @Inject
    lateinit var viewModel: NavHostViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DialogSelectPlaceBinding.inflate(inflater, container, false)
        onInitDependencyInjection()
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        spinnerAdapterDepartamento = SpinnerAdapter(requireContext())
        binding.spionnerDepartamento.apply {
            adapter = spinnerAdapterDepartamento
        }
        spinnerAdapterProvincia = SpinnerAdapter(requireContext())
        binding.spionnerProvincia.apply {
            adapter = spinnerAdapterProvincia
        }
        loadDepartamentos()

        binding.categoriasSearchDialog.setOnClickListener {
            if (posicionProvincia != null) {
                updateUbicationRoom()
                dismiss()
            } else {
            }
        }
    }

    private fun updateUbicationRoom() {
        viewModel.updateUbicacionAppDb(
            UbicacionModel(
                departamento = spinnerAdapterDepartamento.lisProducts?.get(posicionDepartamento!!)!!.name,
                provincia = spinnerAdapterProvincia.lisProducts?.get(posicionProvincia!!)!!.name,
                _id = 0
            )
        )
    }

    private fun onInitDependencyInjection() {
        DaggerPlaceComponent
            .builder()
            .coreComponent(MyApp.coreComponent(requireContext()))
            .dialogSelectPlaceModule(DialogSelectPlaceModule(this))
            .build()
            .inject(this)
    }

    private fun onInitDataBinding() {
        //binding.viewModel = viewModel
    }

    private fun loadDepartamentos() {
        viewModel.getDepartamentos().observe(this) {
            if (it.isNotEmpty()) {
                posicionDepartamento = 0
                spinnerAdapterDepartamento.updateData(it)
                loadProvincias(it[0].id)
                activateListen()
            }
        }
    }

    private fun loadProvincias(id: String) {
        viewModel.getProvincias(id).observe(this) {
            if (it.isNotEmpty()) {
                posicionProvincia = 0
                spinnerAdapterProvincia.updateData(it)

            }
        }
    }


    private fun activateListen() {
        binding.spionnerProvincia.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {}
            override fun onItemSelected(
                parent: AdapterView<*>?, view: View?, position: Int, id: Long
            ) {
                posicionProvincia = position
            }
        }
        binding.spionnerDepartamento.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {}
            override fun onItemSelected(
                parent: AdapterView<*>?, view: View?, position: Int, id: Long
            ) {
                posicionDepartamento = position
                loadProvincias(spinnerAdapterDepartamento.lisProducts?.get(posicionDepartamento!!)!!.id)

            }
        }
    }

    override fun onStart() {
        super.onStart()
        dialog?.window?.setLayout(
            ConstraintLayout.LayoutParams.MATCH_PARENT,
            ConstraintLayout.LayoutParams.WRAP_CONTENT
        )
    }


}