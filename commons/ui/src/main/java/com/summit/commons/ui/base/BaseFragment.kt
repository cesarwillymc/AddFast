package com.summit.commons.ui.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.databinding.library.baseAdapters.BR
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation


abstract class BaseFragment<ViewModel  : BaseViewModel, DB : ViewDataBinding>(@LayoutRes val layout: Int) : Fragment() {

    protected abstract val viewModel: ViewModel
    open lateinit var binding: DB
   // lateinit var dataBindingComponent: DataBindingComponent
    private fun onCreateConfig(inflater: LayoutInflater, container: ViewGroup) {
        binding = DataBindingUtil.inflate(inflater, layout, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.setVariable(BR.viewModel, viewModel)
    }

    open fun onCreateConfig() {}


    open fun onInject() {}

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        onCreateConfig(inflater, container!!)
        onCreateConfig()
        super.onCreateView(inflater, container, savedInstanceState)
        return binding.root
    }

    open fun refresh() {}

    open fun navigate(action: Int) {
        view?.let { _view ->
            Navigation.findNavController(_view).navigate(action)
        }
    }
}
