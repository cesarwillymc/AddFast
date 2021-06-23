package com.summit.camerax.adapter

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.summit.camerax.databinding.GalleryItemAdapterOptionsBinding

class GalleryOptionsAdapter(private val Listener: (String, Int) -> Unit) :
    RecyclerView.Adapter<GalleryOptionsAdapter.ViewHolder>() {

    var listPedido: List<GalleryDocs> = listOf()
    var pisicion: Int = 0

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(GalleryItemAdapterOptionsBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun getItemCount() = listPedido.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        holder.bind(listPedido[position], position)
    }

    fun setearPosition(dato: Int) {

        pisicion = dato
        notifyDataSetChanged()
    }

    fun updateData(dato: List<GalleryDocs>) {
        if (listPedido.isEmpty())
            listPedido = listOf()
        listPedido = dato
        notifyDataSetChanged()
    }

    fun getData() = listPedido

    inner class ViewHolder(private val binding: GalleryItemAdapterOptionsBinding) :
        RecyclerView.ViewHolder(binding.root) {


        fun bind(get: GalleryDocs, position: Int) {
            binding.text = get.name
            if (pisicion == position)
                binding.textTipeSearch.setTextColor(Color.WHITE)
            else
                binding.textTipeSearch.setTextColor(Color.GRAY)

            binding.root.setOnClickListener {
                Listener.invoke(get.directorio, position)
            }
        }
    }

    data class GalleryDocs(
        val name: String,
        val directorio: String
    )

}
