package com.summit.home.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.summit.android.addfast.R
import com.summit.core.network.model.CategoriasModel
import com.summit.home.databinding.LayoutCategoryCardBinding

class CategoriasAdaper(private val listener: CategoriasListener) : RecyclerView.Adapter<CategoriasAdaper.ViewHolder>() {

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
    fun selectPosition(position: Int) {
        positionSelected = position
        notifyDataSetChanged()
    }

    fun updateData(data: MutableList<CategoriasModel>) {
        precios = mutableListOf()
        precios.addAll(data)
        precios.add(0, CategoriasModel("Todos", "Todos", "https://firebasestorage.googleapis.com/v0/b/addfast-af981.appspot.com/o/icon%2Fall.png?alt=media&token=357dba3e-4fda-403e-8759-e6fbaac9ad12", 0))
        notifyDataSetChanged()
    }

    fun getValue(position: Int) = precios[position]
    fun removeDato(posicion: Int) {
        precios.removeAt(posicion)
        notifyDataSetChanged()
    }

    inner class ViewHolder(private val binding: LayoutCategoryCardBinding) : RecyclerView.ViewHolder(binding.root) {


        fun bind(get: CategoriasModel, position: Int) {
            binding.model=get
            if (position == positionSelected) {
                binding.fsiTexto.setTextColor(itemView.resources.getColor(R.color.black))
                binding.fsiConstraint.background = itemView.context.getDrawable(R.drawable.border_button_carrito)
            } else {
                binding.fsiTexto.setTextColor(itemView.resources.getColor(R.color.gris_2))
                binding.fsiConstraint.background =
                    itemView.context.getDrawable(R.drawable.border_button_carrito_inactive)
            }

            binding.root.setOnClickListener {
                listener.listener(position, get)
            }
        }
    }

    interface CategoriasListener {
        fun listener(position: Int, datos: CategoriasModel)
    }
}
