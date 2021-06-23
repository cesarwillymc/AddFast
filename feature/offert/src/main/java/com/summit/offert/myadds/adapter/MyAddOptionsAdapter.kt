package com.summit.offert.myadds.adapter

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.summit.android.addfast.R
import com.summit.offert.databinding.MyAddOptionsAdapterBinding

class MyAddOptionsAdapter(private val Listener: MyAddOptionsAdapterListener) :
    RecyclerView.Adapter<MyAddOptionsAdapter.ViewHolder>() {

    var listPedido: List<String> = listOf()
    var pisicion: Int = 0

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(MyAddOptionsAdapterBinding.inflate(LayoutInflater.from(parent.context), parent, false))
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


    inner class ViewHolder(private val binding: MyAddOptionsAdapterBinding) :
        RecyclerView.ViewHolder(binding.root) {


        fun bind(letter: String, position: Int) {
            binding.text = letter
            if (pisicion == position)
                binding.textTipeSearch.setTextColor(itemView.context.getColor(R.color.itemMenuUnchecked))
            else
                binding.textTipeSearch.setTextColor(Color.GRAY)

            binding.root.setOnClickListener {
                Listener.onclickMyAdd(letter, position)
            }
        }
    }


    interface MyAddOptionsAdapterListener {
        fun onclickMyAdd(dato: String, position: Int)
    }
}
