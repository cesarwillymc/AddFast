package com.summit.commons.ui.base

import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter

import com.summit.commons.ui.R


abstract class BaseAdapter<T>(callback: DiffUtil.ItemCallback<T>) : ListAdapter<T, BaseViewHolder<ViewDataBinding>>(callback) {

    override fun onBindViewHolder(holder: BaseViewHolder<ViewDataBinding>, position: Int) {
        (holder as BaseViewHolder<*>).binding.root.setTag(1221321, position)
        bind(holder.binding, position)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = getViewHolder(parent, viewType)

    open fun getViewHolder(parent: ViewGroup, viewType: Int) = BaseViewHolder(createBinding(parent, viewType))

    abstract fun createBinding(parent: ViewGroup, viewType: Int): ViewDataBinding

    protected abstract fun bind(binding: ViewDataBinding, position: Int)
}
