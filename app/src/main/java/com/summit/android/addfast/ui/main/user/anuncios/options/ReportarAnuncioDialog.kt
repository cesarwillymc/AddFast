package com.summit.android.addfast.ui.main.user.anuncios.options

import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.Observer
import com.summit.android.addfast.R
import com.summit.android.addfast.repo.model.Anuncios
import com.summit.android.addfast.ui.main.MainViewModel
import com.summit.android.addfast.utils.lifeData.Status
import kotlinx.android.synthetic.main.dialog_reportar_anuncio.*
import org.jetbrains.anko.support.v4.toast
import org.koin.androidx.viewmodel.ext.android.viewModel


class ReportarAnuncioDialog : DialogFragment() {
    val viewModel: MainViewModel by viewModel()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {


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