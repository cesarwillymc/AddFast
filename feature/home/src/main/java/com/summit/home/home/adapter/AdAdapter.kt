package com.summit.home.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.summit.core.network.model.Anuncios
import com.summit.home.databinding.LayoutAdCardBinding


class AdAdapter(private val producListener: CategoriasProductosListener,var lisProducts: List<Anuncios>) :
    RecyclerView.Adapter<AdAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = LayoutAdCardBinding
            .inflate(LayoutInflater.from(parent.context), parent, true)
        return ViewHolder(binding)
    }


    inner class ViewHolder(private val binding: LayoutAdCardBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(anuncio: Anuncios, position: Int) {
            binding.model = anuncio
            binding.executePendingBindings()
            binding.root.setOnClickListener {
                producListener.onCLickItem(anuncio, position)
            }

        }
    }


    override fun onBindViewHolder(holder: AdAdapter.ViewHolder, position: Int) {
        holder.bind(lisProducts[position], position)
    }

    override fun getItemCount() = lisProducts.size


}
