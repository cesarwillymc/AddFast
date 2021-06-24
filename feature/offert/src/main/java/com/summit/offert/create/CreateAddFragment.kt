package com.summit.offert.create

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import androidx.navigation.fragment.findNavController
import com.summit.android.addfast.app.MyApp
import com.summit.commons.ui.base.BaseFragment
import com.summit.offert.R
import com.summit.offert.create.adapter.SpinnerAdapter
import com.summit.offert.create.adapter.SpinnerCategoryAdapter
import com.summit.offert.create.di.CreateAddModule
import com.summit.offert.create.di.DaggerCreateAddComponent
import com.summit.offert.databinding.FragmentCreateAddBinding
import java.io.File

class CreateAddFragment : BaseFragment<FragmentCreateAddBinding, CreateAddViewModel>(
    layoutId = R.layout.fragment_create_add
) {
    private val keyPhoto = "KEY_PHOTO_REGISTER"
    private lateinit var adapterDepartament: SpinnerAdapter
    private lateinit var adapterProvince: SpinnerAdapter
    private lateinit var adapterCategory: SpinnerCategoryAdapter

    override fun onInitDependencyInjection() {
        DaggerCreateAddComponent.builder().coreComponent(MyApp.coreComponent(requireContext()))
            .createAddModule(CreateAddModule(this)).build().inject(this)

    }

    override fun onInitDataBinding() {
        viewBinding.viewModel = viewModel
    }

    private fun listenFragmentData() {
        requireActivity().supportFragmentManager.setFragmentResultListener(
            keyPhoto, viewLifecycleOwner
        ) { key, result ->
            if (keyPhoto == key) {
                if (!result.getString("data").isNullOrEmpty()) {
                    viewModel.photoDirection.postValue(File(result.getString("data")!!))
                }
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.initViewModel()
        setupBindingViews()
        setupRVDepartament()
        setupRVProvince()
        setupRVTypeCategory()
        listenFragmentData()
        listenDataViewModelCategory()
        listenDataViewModelDepartament()
        listenDataViewModelProvince()
    }

    private fun listenDataViewModelCategory() {

        viewModel.stateCategory.observe(viewLifecycleOwner) {
            when (it) {
                CreateAddViewState.Loading -> {

                }
                CreateAddViewState.Complete -> {
                    adapterCategory.updateData(viewModel.dataCategory)
                    viewBinding.formView.crearAnuncioTipoAnuncio.setSelection(viewModel.positionCategory)
                    listenCategoryAdapter()
                }
                CreateAddViewState.Error -> {
                    toast("Recarga la pagina, no se pudo obtener las categorias.")
                }
                else -> {
                }
            }

        }

    }

    private fun listenDataViewModelDepartament() {

        viewModel.dataDepartamento.observe(viewLifecycleOwner) {
            if (!it.isNullOrEmpty()) {
                adapterDepartament.updateData(it)
                viewBinding.formView.crearAnuncioDepartamento.setSelection(viewModel.positionDepartament)
            }
        }

    }

    private fun listenDataViewModelProvince() {

        viewModel.dataProvincia.observe(viewLifecycleOwner) {
            if (!it.isNullOrEmpty()) {
                adapterProvince.updateData(it)
                viewBinding.formView.crearAnuncioProvincia.setSelection(viewModel.positionProvince)
            }
        }

    }

    private fun setupRVTypeCategory() {
        adapterCategory = SpinnerCategoryAdapter(requireContext())
        viewBinding.formView.crearAnuncioTipoAnuncio.apply {
            adapter = adapterCategory
        }
    }

    private fun listenCategoryAdapter() {
        viewBinding.formView.crearAnuncioTipoAnuncio.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onNothingSelected(parent: AdapterView<*>?) {}
                override fun onItemSelected(
                    parent: AdapterView<*>?, view: View?, position: Int, id: Long
                ) {
                    viewModel.positionCategory=position
                    val item = adapterCategory.lisProducts[position]
                    viewModel.typeAdd.postValue(listOf(item.name, item.id))
                }
            }
    }

    private fun setupRVProvince() {
        adapterProvince = SpinnerAdapter(requireContext())
        viewBinding.formView.crearAnuncioProvincia.apply {
            adapter = adapterProvince
        }
        listenProvinceAdapter()
    }

    private fun listenProvinceAdapter() {
        viewBinding.formView.crearAnuncioProvincia.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onNothingSelected(parent: AdapterView<*>?) {}
                override fun onItemSelected(
                    parent: AdapterView<*>?, view: View?, position: Int, id: Long
                ) {
                    viewModel.positionProvince=position
                    val item = adapterProvince.lisProducts[position]
                    viewModel.provinceData.postValue(item.name)
                }
            }
    }

    private fun setupRVDepartament() {
        adapterDepartament = SpinnerAdapter(requireContext())
        viewBinding.formView.crearAnuncioDepartamento.apply {
            adapter = adapterDepartament
        }
        listenDepartamentAdapter()
    }

    private fun listenDepartamentAdapter() {
        viewBinding.formView.crearAnuncioDepartamento.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onNothingSelected(parent: AdapterView<*>?) {}
                override fun onItemSelected(
                    parent: AdapterView<*>?, view: View?, position: Int, id: Long
                ) {
                    viewModel.positionDepartament=position
                    val item = adapterDepartament.lisProducts[position]
                    viewModel.getProvincias(item.id)
                    viewModel.departamentData.postValue(item.name)
                }
            }
    }

    private fun setupBindingViews() {
        viewBinding.formView.crearAnuncioCrear.setOnClickListener {
            viewModel.createAdd()
            listenResultForm()
        }
        viewBinding.formView.crearAnuncioCargarImg.setOnClickListener {
            findNavController().navigate(CreateAddFragmentDirections.actionNavCreateAddToNavCameraxGraph())
        }
        viewBinding.formView.crearAnuncioImg.setOnClickListener {
            findNavController().navigate(CreateAddFragmentDirections.actionNavCreateAddToNavCameraxGraph())
        }
    }

    private fun listenResultForm() {

        viewModel.stateRegister.observe(viewLifecycleOwner) {
            when (it) {
                CreateAddViewState.Loading -> {
                    hideKeyboard()
                }
                CreateAddViewState.Complete -> {
                    findNavController().navigateUp()
                }
                CreateAddViewState.Error -> {
                    toast("Sucedio un error al crear el anuncio")
                }
                else -> {
                }
            }
        }

    }

}