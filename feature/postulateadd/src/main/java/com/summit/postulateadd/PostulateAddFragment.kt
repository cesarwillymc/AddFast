package com.summit.postulateadd

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.navigation.fragment.findNavController
import com.summit.android.addfast.app.MyApp
import com.summit.commons.ui.base.BaseFragment
import com.summit.commons.ui.extension.FileUtils
import com.summit.commons.ui.extension.observe
import com.summit.core.network.model.Anuncios
import com.summit.postulateadd.databinding.FragmentCreatePostulateBinding
import com.summit.postulateadd.di.DaggerPostulateAddComponent
import com.summit.postulateadd.di.PostulateAddModule
import java.io.File

class PostulateAddFragment : BaseFragment<FragmentCreatePostulateBinding, PostulateAddViewModel>(
    layoutId = R.layout.fragment_create_postulate
) {

    override fun onInitDependencyInjection() {
        DaggerPostulateAddComponent.builder().coreComponent(MyApp.coreComponent(requireContext()))
            .postulateAddModule(PostulateAddModule(this)).build().inject(this)
    }

    override fun onInitDataBinding() {
        viewBinding.viewModel = viewModel
        val argsAdd = arguments?.get("model") as Anuncios
        viewModel.anuncioArgs.postValue(argsAdd)
    }

    private lateinit var someActivityResultLauncher: ActivityResultLauncher<Intent>
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupToListenActivityResult()
        setupOnClickBinding()
    }

    private fun setupToListenActivityResult() {
        someActivityResultLauncher = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) {
            if (it.resultCode == Activity.RESULT_OK) {
                try {
                    val datos = FileUtils().getPath(requireContext(), it!!.data!!.data!!)!!
                    Handler(Looper.getMainLooper()).postDelayed({
                        viewModel.dataFile.postValue(File(datos))
                        viewBinding.formView.crearPostulacionImg.setImageDrawable(requireCompatActivity().getDrawable(R.drawable.loadcomplete))
                    }, 1000L)
                } catch (e: Exception) {
                    toast("Elige otro gestor de archivos para elegir el documento")
                }
            }
        }
    }

    private fun setupOnClickBinding() {
        viewBinding.formView.crearPostulacionPostular.setOnClickListener {
            viewModel.crearPostulacion()
            observe(viewModel.stateRegister, ::listenStateRegister)
        }
        viewBinding.formView.crearPostulacionCargar.setOnClickListener {
            val mRequestFileIntent = Intent(Intent.ACTION_GET_CONTENT)
            mRequestFileIntent.type = "application/pdf"
            someActivityResultLauncher.launch(
                Intent.createChooser(
                    mRequestFileIntent,
                    "Seleccione una app para elegir el pdf"
                )
            )
        }
    }

    private fun listenStateRegister(postulateAddViewState: PostulateAddViewState) {
        when (postulateAddViewState) {
            PostulateAddViewState.Loading -> {
                hideKeyboard()
            }
            PostulateAddViewState.Complete -> {
                toast("Postulacion completada.")
                findNavController().navigateUp()
            }
            PostulateAddViewState.Error -> {
                toast("Surgio un error al postular a esta oferta.")
            }
            else -> {

            }
        }
    }
}