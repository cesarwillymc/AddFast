package com.summit.home.category.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.summit.core.network.model.Anuncios
import com.summit.home.databinding.LayoutCategoryLargeCardBinding


class AddLargeAdapter(private val listener: Listener) : RecyclerView.Adapter<AddLargeAdapter.ViewHolder>() {

    var anuncios: MutableList<Anuncios> = mutableListOf()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val binding = LayoutCategoryLargeCardBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(

            binding
        )
    }

    override fun getItemCount() = anuncios.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(anuncios[position], position)
    }


    fun updateData(data: MutableList<Anuncios>) {
        anuncios = mutableListOf()
        anuncios.addAll(data)
        notifyDataSetChanged()
    }

    fun getValue(position: Int) = anuncios[position]

    inner class ViewHolder(private val binding: LayoutCategoryLargeCardBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(anuncio: Anuncios, position: Int) {
            binding.model = anuncio
            binding.executePendingBindings()
            binding.root.setOnClickListener {
                listener.onclick(anuncio, position)

            }
        }
    }

    interface Listener {
        fun onclick(anuncios: Anuncios, position: Int)
    }
}

