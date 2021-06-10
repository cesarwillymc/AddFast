package com.summit.home.home.adapter


import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.summit.core.network.model.ListaAnuncios
import com.summit.home.databinding.LayoutInitCategoryProductBinding



class AdapterProductosCategoria(private val producListener: CategoriasProductosListener) :
    RecyclerView.Adapter<AdapterProductosCategoria.ViewHolder>() {

    private val viewPool = RecyclerView.RecycledViewPool()
    private var lisProducts= mutableListOf<ListaAnuncios>()
    fun updateData(listaProduct: MutableList<ListaAnuncios>) {

        lisProducts.clear()
        lisProducts.addAll(listaProduct)
        Log.e("anuncios","entro ${lisProducts.size}")
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = LayoutInitCategoryProductBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }


    inner class ViewHolder(private val binding: LayoutInitCategoryProductBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(anuncio: ListaAnuncios, position: Int) {
            Log.e("bind","${anuncio}")
            binding.model=anuncio
            binding.executePendingBindings()
            binding.ficiVerMas.setOnClickListener {
                producListener.onClickVerMas(anuncio, position)
            }

            val linearLayoutManager: LinearLayoutManager =
                object : LinearLayoutManager(itemView.context, HORIZONTAL, false) {
                    override fun canScrollVertically(): Boolean {
                        return false
                    }
                }

            linearLayoutManager.initialPrefetchItemCount = anuncio.lista.size
            linearLayoutManager.isItemPrefetchEnabled = true
            binding.ficiRv.apply {
                setRecycledViewPool(viewPool)
                layoutManager = linearLayoutManager
                adapter= AdAdapter(producListener,anuncio.lista)
            }



        }
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(lisProducts[position], position)
    }

    override fun getItemCount() = lisProducts.size


}
