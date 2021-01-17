package com.summit.android.addfast.ui.main.admin.fragments.promociones

import android.os.Bundle
import android.os.Handler
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.summit.android.addfast.R
import com.summit.android.addfast.base.BaseFragment
import com.summit.android.addfast.ui.main.admin.AdminViewModel
import com.summit.android.addfast.ui.main.admin.AdminViewModelFactory
import com.summit.android.addfast.utils.lifeData.Status
import kotlinx.android.synthetic.main.fragment_ver_promocion.*
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.kodein
import org.kodein.di.generic.instance
import org.ocpsoft.prettytime.PrettyTime
import java.util.*


class VerPromocionFragment: BaseFragment(), KodeinAware {

    override val kodein: Kodein by kodein()
    lateinit var viewModel: AdminViewModel
    val factory: AdminViewModelFactory by instance()
    val args:VerPromocionFragmentArgs by navArgs()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = requireActivity().run{
            ViewModelProvider(this,factory).get(AdminViewModel::class.java)
        }
        Glide.with(requireContext()).load(args.model.img).into(ver_promocion_img)
        ver_promocion_title.text=args.model.name
        val prettyTime = PrettyTime(Locale.getDefault())
        val ago: String = prettyTime.format(Date(args.model.fecha))
        ver_promocion_time.text=ago
        ver_promocion_quitar.setOnClickListener {
            loadQuitarPromo()
        }
    }

    private fun loadQuitarPromo() {
        viewModel.desactivarPromocion(args.model.id).observe(viewLifecycleOwner, androidx.lifecycle.Observer {
            when (it.status) {
                Status.LOADING -> {
                    toast("Cargando")
                }
                Status.SUCCESS -> {

                    toast("Completado satisfatoriamente")
                    Handler().postDelayed({
                        findNavController().navigateUp()
                    },500L)
                }
                Status.ERROR -> {

                    snakBar(it.exception!!.message!!)
                    Log.e("ErrorProfile",it.exception!!.message!!)
                }
            }
        })
    }

    override fun getLayout()= R.layout.fragment_ver_promocion
}