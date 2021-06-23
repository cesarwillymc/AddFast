package com.summit.home.category

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.summit.android.addfast.app.MyApp
import com.summit.commons.ui.base.BaseFragment
import com.summit.commons.ui.extension.observe
import com.summit.core.network.model.Anuncios
import com.summit.home.R
import com.summit.home.category.adapter.AddLargeAdapter
import com.summit.home.category.di.CategoryModule
import com.summit.home.category.di.DaggerCategoryComponent
import com.summit.home.databinding.FragmentCategoryBinding

class CategoryFragment : BaseFragment<FragmentCategoryBinding, CategoryViewModel>(
    layoutId = R.layout.fragment_category
) {
    private val args: CategoryFragmentArgs by navArgs()
    private lateinit var adapterAdds: AddLargeAdapter

    override fun onInitDependencyInjection() {
        DaggerCategoryComponent.builder()
            .coreComponent(MyApp.coreComponent(requireContext()))
            .categoryModule(CategoryModule(this))
            .build()
            .inject(this)
    }

    override fun onInitDataBinding() {
        viewBinding.viewModel = viewModel
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getAllAdds()
        setupRvCategoryAdapter()
    }

    private fun getAllAdds() {
        if (viewModel.data.value == null) {
            viewModel.getAllAnunciosByCategorias(args.idcategory)
        }
    }

    private fun setupRvCategoryAdapter() {
        adapterAdds = AddLargeAdapter { anuncios, _ ->
            findNavController().navigate(CategoryFragmentDirections.actionNavListCategoryToNavDetailAd(model = anuncios))
        }
        viewBinding.includeList.categoriasVerRv.apply {
            adapter = adapterAdds
            layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        }
        observe(viewModel.data, ::updateAdapterAddsData)
    }

    private fun updateAdapterAddsData(anuncios: List<Anuncios>) {
        adapterAdds.updateData(anuncios.toMutableList())
    }
}