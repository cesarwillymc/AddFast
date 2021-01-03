package com.summit.android.addfast.ui.main.user.anuncios.options

import android.os.Bundle
import android.os.Handler
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
import com.summit.android.addfast.repo.model.Anuncios
import com.summit.android.addfast.repo.model.departamento.UbicacionModel
import com.summit.android.addfast.ui.main.MainViewModel
import com.summit.android.addfast.ui.main.MainViewModelFactory
import com.summit.android.addfast.utils.SpinnerAdapter
import com.summit.android.addfast.utils.lifeData.Status
import kotlinx.android.synthetic.main.dialog_reportar_anuncio.*
import kotlinx.android.synthetic.main.dialog_select_place.*
import org.jetbrains.anko.backgroundColor
import org.jetbrains.anko.support.v4.toast
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.kodein
import org.kodein.di.generic.instance


class ReportarAnuncioDialog : DialogFragment(), KodeinAware {
    private lateinit var viewModel: MainViewModel
    override val kodein: Kodein by kodein()
    private val factory: MainViewModelFactory by instance()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        viewModel = requireActivity().run {
            ViewModelProvider(this, factory).get(MainViewModel::class.java)
        }

        return inflater.inflate(R.layout.dialog_reportar_anuncio, container, false)
    }

    var vista: View? = null
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        vista = view

        dialog_report_send.setOnClickListener {
            if (dialog_report_text.text.toString().trim().isNotEmpty()) {
                enviarreporte(requireArguments().getParcelable<Anuncios>("modelo")?:Anuncios(),dialog_report_text.text.toString().trim())
            } else {
                dialog_report_text.error = "No puede estar vacio"
                dialog_report_text.requestFocus()
            }

        }
    }

    private fun enviarreporte(id: Anuncios,reporte:String) {
        viewModel.reportarAnuncio(id,reporte).observe(this, Observer {
            when (it.status) {
                Status.LOADING -> {
                    toast("Enviando reporte")
                }
                Status.SUCCESS -> {
                    toast("Reporte enviado")
                    Handler().postDelayed({
                        dismiss()
                    },700L)
                }
                Status.ERROR -> {
                    Log.e("enviarreporte",it.exception!!.message!!)
                }
            }
        })
    }

    override fun onStart() {
        super.onStart()
        dialog?.window?.setLayout(ConstraintLayout.LayoutParams.MATCH_PARENT, ConstraintLayout.LayoutParams.WRAP_CONTENT)
    }

    private var positionAdapter = 0

}