package com.summit.home.home.adapter


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.summit.core.network.model.ListaAnuncios
import com.summit.home.databinding.LayoutInitCategoryProductBinding



class AdapterProductosCategoria(private val producListener: CategoriasProductosListener) :
    RecyclerView.Adapter<AdapterProductosCategoria.ViewHolder>() {

    private val viewPool = RecyclerView.RecycledViewPool()
    private var lisProducts: List<ListaAnuncios> = listOf()
    fun updateData(lisProducts: List<ListaAnuncios>) {
        this.lisProducts = lisProducts
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = LayoutInitCategoryProductBinding
            .inflate(LayoutInflater.from(parent.context), parent, true)
        return ViewHolder(binding)
    }


    inner class ViewHolder(private val binding: LayoutInitCategoryProductBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(anuncio: ListaAnuncios, position: Int) {

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
            binding.ficiRv.setRecycledViewPool(viewPool)
            binding.ficiRv.layoutManager = linearLayoutManager
            binding.ficiRv.adapter=AdAdapter(producListener,anuncio.lista)


        }
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(lisProducts[position], position)
    }

    override fun getItemCount() = lisProducts.size


}
