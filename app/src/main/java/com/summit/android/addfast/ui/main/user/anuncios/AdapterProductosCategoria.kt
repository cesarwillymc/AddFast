/*
package com.summit.android.addfast.ui.main.user.anuncios

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.summit.android.addfast.R
import com.summit.android.addfast.repo.model.Anuncios
import com.summit.android.addfast.repo.model.ListaAnuncios
import com.xwray.groupie.ExpandableGroup
import com.xwray.groupie.ExpandableItem
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.Section
import com.xwray.groupie.kotlinandroidextensions.GroupieViewHolder
import com.xwray.groupie.kotlinandroidextensions.Item


class AdapterProductosCategoria(private val producListener: CategoriasProductosListener, private val lisProducts: ListaAnuncios):
        Item() {

    val section = Section()
    private val viewPool = RecyclerView.RecycledViewPool()
    val adapterGrupie = GroupAdapter<com.xwray.groupie.GroupieViewHolder>().apply {
        spanCount=1
    }


    override fun bind(viewHolder: GroupieViewHolder, position: Int) {
        val nombre = viewHolder.itemView.findViewById<TextView>(R.id.fici_name)
        val vermas = viewHolder.itemView.findViewById<TextView>(R.id.fici_ver_mas)
        val recyclerView = viewHolder.itemView.findViewById<RecyclerView>(R.id.fici_rv)

        nombre.text=lisProducts.name
        vermas.setOnClickListener {
            producListener.onClickVerMas(lisProducts, position)
        }
        //  val librosAdapter= ProductoAdapter(producListener, lisProducts[position].items)
        val linearLayoutManager: LinearLayoutManager =
                object : LinearLayoutManager(viewHolder.itemView.context, HORIZONTAL, false) {
                    override fun canScrollVertically(): Boolean {
                        return false
                    }
                }
        adapterGrupie.clear()
        linearLayoutManager.initialPrefetchItemCount= lisProducts.lista.size
        linearLayoutManager.isItemPrefetchEnabled=true
        recyclerView.setRecycledViewPool(viewPool)
        recyclerView.layoutManager =  linearLayoutManager
        recyclerView.adapter = adapterGrupie

        for (variables in lisProducts.lista){
            val section = Section()
            val modelo = ProductoAdapter(producListener,variables)
            // section.setHeader(modelo)
            section.add(modelo)
            adapterGrupie.add(section)
        }


    }
    private fun convertItems(items: List<Anuncios>): MutableList<ProductoAdapter> {
        return MutableList(items.size){
            ProductoAdapter(producListener,items[it])
        }

    }
    override fun getLayout()=   R.layout.fragment_inicio_categoria_item



}
 */