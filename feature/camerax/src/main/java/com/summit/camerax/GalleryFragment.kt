package com.summit.camerax


import android.Manifest
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.summit.android.addfast.app.MyApp
import com.summit.camerax.adapter.GalleryAdapter
import com.summit.camerax.adapter.GalleryOptionsAdapter
import com.summit.camerax.databinding.FragmentGalleryBinding
import com.summit.camerax.di.DaggerGalleryComponent
import com.summit.camerax.di.GalleryModule
import com.summit.commons.ui.base.BaseFragment
import java.io.File


class GalleryFragment internal constructor() : BaseFragment<FragmentGalleryBinding, GalleryViewModel>(
    layoutId = R.layout.fragment_gallery
), GalleryAdapter.clickListener, GalleryOptionsAdapter.GalleryOptionsAdapterListener {

    private val keyPhoto = "KEY_PHOTO_REGISTER"
    lateinit var adaptadorView: GalleryOptionsAdapter
    var listaimg = listOf<File>()

    private lateinit var adaptador: GalleryAdapter


    @RequiresApi(Build.VERSION_CODES.Q)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val permission =
            registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { permissions ->
                if (permissions.containsValue(true)) {
                    setupConfigOnviewCreated()
                } else {
                    toast("Debes de aceptar el permiso, para poder elegir una foto")
                }

            }

        permission.launch(
            arrayOf(
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
            )
        )
    }


    @RequiresApi(Build.VERSION_CODES.Q)
    private fun setupConfigOnviewCreated() {
        requireActivity().run {
            viewModel.getImagesPath(requireCompatActivity()).apply {
                this.let {
                    listaimg = it
                }
            }
        }

        viewBinding.enviarImagen.setOnClickListener {
            viewModel.getImageFile(requireContext(), file!!.path)
            compressImageSize()
        }
        setupGalleryAdapter()

        setupGalleryAdapterOptions()
    }

    private fun compressImageSize() {
        if (viewModel.state.value == null) {
            viewModel.state.observe(viewLifecycleOwner) {
                when (it) {
                    GalleryViewState.Complete -> {
                        val bundle = Bundle()
                        bundle.putString("data", viewModel.urlDirectionImage)
                        Handler(Looper.getMainLooper()).postDelayed({
                            requireActivity().supportFragmentManager.setFragmentResult(keyPhoto, bundle)
                            findNavController().navigateUp()
                        }, 400L)
                    }
                    GalleryViewState.Loading -> {

                    }
                    GalleryViewState.Error -> {

                    }
                    else -> {
                    }
                }
            }
        }
    }

    private fun setupGalleryAdapterOptions() {
        adaptadorView = GalleryOptionsAdapter(this)
        viewBinding.reclyclerOptions.apply {
            layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            adapter = adaptadorView
        }
        adaptadorView.updateData(viewModel.listOptionsMenu)
    }

    private fun setupGalleryAdapter() {
        adaptador = GalleryAdapter(this)
        viewBinding.reciclerImg.apply {
            layoutManager = GridLayoutManager(requireContext(), 2)
            adapter = adaptador
        }
        adaptador.updateData(listaimg)
    }


    var file: File? = null
    override fun click(path: File?, position: Int?) {
        if (path == null) {
            file = null
            viewBinding.enviarImagen.visibility = View.GONE
            adaptador.selectData(null)
        } else {
            file = path
            viewBinding.enviarImagen.visibility = View.VISIBLE
            adaptador.selectData(path.path)
        }

    }


    @RequiresApi(Build.VERSION_CODES.Q)
    override fun onclickGallery(dato: String, position: Int) {
        if (dato == "") {
            listaimg = viewModel.getImagesPath(requireActivity())
            adaptador.updateData(listaimg)
            adaptadorView.setearPosition(position)
        } else {
            viewModel.listImages(dato).observe(this) {
                if (it.isNotEmpty()) {
                    listaimg = it
                    adaptador.updateData(listaimg)
                    adaptadorView.setearPosition(position)
                }
            }
        }

    }

    override fun onInitDependencyInjection() {
        DaggerGalleryComponent
            .builder()
            .coreComponent(MyApp.coreComponent(requireContext()))
            .galleryModule(GalleryModule(this))
            .build()
            .inject(this)
    }

    override fun onInitDataBinding() {
        viewBinding.viewModel = viewModel
    }
}

