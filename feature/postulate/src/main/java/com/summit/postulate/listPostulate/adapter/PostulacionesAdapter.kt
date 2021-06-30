package com.summit.postulate.listPostulate.adapter

import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.summit.core.network.model.Postulacion
import com.summit.postulate.databinding.FragmentPostulateItemBinding

class PostulacionesAdapter(private val listener: (Postulacion,Int)->Unit) : RecyclerView.Adapter<PostulacionesAdapter.ViewHolder>() {

    private var precios: MutableList<Postulacion> = mutableListOf()
    private var preciosinicial: MutableList<Postulacion> = mutableListOf()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            FragmentPostulateItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun getItemCount() = precios.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(precios[position], position)
    }



    fun updateData(data: MutableList<Postulacion>) {
        precios = mutableListOf()
        preciosinicial = mutableListOf()

        precios.addAll(data)
        preciosinicial.addAll(data)
        notifyDataSetChanged()
    }

    fun searchBy(dato: String) {
        precios = if (dato == "TODOS") {
            preciosinicial
        } else {
            val lista: List<Postulacion> = precios.sortedBy {
                it.estado == dato
            }.reversed()
            lista.toMutableList()
        }

        Handler(Looper.getMainLooper()).postDelayed({ notifyDataSetChanged() }, 500L)

    }

    fun getValue(position: Int) = precios[position]

    inner class ViewHolder(private val binding: FragmentPostulateItemBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(model: Postulacion, position: Int) {
            binding.model = model
            binding.executePendingBindings()

            itemView.setOnClickListener {
                listener.invoke(model, position)

            }
        }
    }



}
