package com.summit.offert.myadds.adapter


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.summit.core.network.model.Anuncios
import com.summit.offert.databinding.FragmentMyAddsItemBinding

class MyAddAdapter(private val listener: Listener) : RecyclerView.Adapter<MyAddAdapter.ViewHolder>() {

    private var adds: MutableList<Anuncios> = mutableListOf()
    private var addsinicial: MutableList<Anuncios> = mutableListOf()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            FragmentMyAddsItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun getItemCount() = adds.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(adds[position], position)
    }


    fun updateData(data: MutableList<Anuncios>) {
        adds = mutableListOf()
        addsinicial = mutableListOf()
        adds.addAll(data)
        addsinicial.addAll(data)
        notifyDataSetChanged()
    }

    fun getValue(position: Int) = adds[position]

    fun searchBy(dato: String) {
        if (adds.isNotEmpty()) {
            adds = if (dato == "TODOS") {
                addsinicial
            } else {
                val lista: List<Anuncios> = adds.sortedBy {
                    it.estado == dato
                }.reversed()
                lista as MutableList<Anuncios>
            }
        }
        notifyDataSetChanged()
    }

    inner class ViewHolder(val binding: FragmentMyAddsItemBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(add: Anuncios, position: Int) {
            binding.model=add
            binding.executePendingBindings()

            itemView.setOnClickListener {
                listener.onclick(add, position)

            }
        }
    }

    interface Listener {
        fun onclick(anuncios: Anuncios, position: Int)
    }

}