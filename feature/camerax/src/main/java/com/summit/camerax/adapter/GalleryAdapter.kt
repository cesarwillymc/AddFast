package com.summit.camerax.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.summit.camerax.databinding.GalleryItemAdapterBinding
import java.io.File

class GalleryAdapter(private val Listener: clickListener) : RecyclerView.Adapter<GalleryAdapter.ViewHolder>() {

    var listPedido: List<File> = listOf()
    var posiciionDato: String? = null
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(GalleryItemAdapterBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun getItemCount() = listPedido.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(listPedido[position], position)
    }

    fun selectData(position: String?) {
        posiciionDato = position
        notifyDataSetChanged()
    }

    fun updateData(data: List<File>?) {
        listPedido = data ?: listOf()
        notifyDataSetChanged()
    }

    inner class ViewHolder(private val binding: GalleryItemAdapterBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(file: File, position: Int) {
            binding.file = file
            binding.state = when (posiciionDato) {
                file.path -> {
                    true
                }
                else -> {
                    false
                }
            }
            binding.executePendingBindings()

            binding.root.setOnClickListener {
                if (posiciionDato == file.path) {
                    Listener.click(null, position)
                } else {
                    Listener.click(file, position)

                }

            }
        }
    }

    interface clickListener {
        fun click(path: File?, position: Int?)
    }
}
