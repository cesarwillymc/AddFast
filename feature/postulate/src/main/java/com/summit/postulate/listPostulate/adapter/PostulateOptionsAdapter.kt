package com.summit.postulate.listPostulate.adapter

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.summit.android.addfast.R
import com.summit.postulate.databinding.PostulateOptionsAdapterBinding

class PostulateOptionsAdapter(private val Listener: (String,Int)->Unit) :
    RecyclerView.Adapter<PostulateOptionsAdapter.ViewHolder>() {

    var listPedido: List<String> = listOf()
    var pisicion: Int = 0

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(PostulateOptionsAdapterBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun getItemCount() = listPedido.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        holder.bind(listPedido[position], position)
    }

    fun setearPosition(dato: Int) {

        pisicion = dato
        notifyDataSetChanged()
    }

    fun updateData(dato: List<String>) {
        if (listPedido.isEmpty())
            listPedido = listOf()
        listPedido = dato
        notifyDataSetChanged()
    }


    inner class ViewHolder(private val binding: PostulateOptionsAdapterBinding) :
        RecyclerView.ViewHolder(binding.root) {


        fun bind(letter: String, position: Int) {
            binding.text = letter
            if (pisicion == position)
                binding.textTipeSearch.setTextColor(itemView.context.getColor(R.color.itemMenuUnchecked))
            else
                binding.textTipeSearch.setTextColor(Color.GRAY)

            binding.root.setOnClickListener {
                Listener.invoke(letter, position)
            }
        }
    }


}
