package com.summit.home.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.summit.core.network.model.CategoriasModel
import com.summit.home.R
import com.summit.home.databinding.LayoutCategoryCardBinding

class CategoriasAdaper(private val listener: (Int,CategoriasModel)->Unit) : RecyclerView.Adapter<CategoriasAdaper.ViewHolder>() {

    var precios: MutableList<CategoriasModel> = mutableListOf()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = LayoutCategoryCardBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return ViewHolder(binding)
    }

    override fun getItemCount() = precios.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(precios[position], position)
    }

    var positionSelected = 0


    fun updateData(data: MutableList<CategoriasModel>) {
        precios = mutableListOf()
        precios.addAll(data)
        precios.add(0, CategoriasModel("Todos", "Todos", "https://firebasestorage.googleapis.com/v0/b/addfast-af981.appspot.com/o/icon%2Fall.png?alt=media&token=357dba3e-4fda-403e-8759-e6fbaac9ad12", 0))
        notifyDataSetChanged()
    }

    fun getValue(position: Int) = precios[position]

    inner class ViewHolder(private val binding: LayoutCategoryCardBinding) : RecyclerView.ViewHolder(binding.root) {


        fun bind(get: CategoriasModel, position: Int) {
            binding.model=get
            if (position == positionSelected) {
                binding.fsiTexto.setTextColor(itemView.context.getColor(R.color.black))
                binding.fsiConstraint.background = itemView.context.getDrawable(R.drawable.border_button_carrito)
            } else {
                binding.fsiTexto.setTextColor(itemView.context.getColor(R.color.gris_2))
                binding.fsiConstraint.background =
                    itemView.context.getDrawable(R.drawable.border_button_carrito_inactive)
            }

            binding.root.setOnClickListener {
                listener.invoke(position, get)
            }
        }
    }

}
