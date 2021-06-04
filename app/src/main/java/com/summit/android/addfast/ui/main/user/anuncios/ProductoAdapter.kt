/*
package com.summit.android.addfast.ui.main.user.anuncios

import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.summit.android.addfast.R
import com.summit.android.addfast.repo.model.Anuncios
import com.summit.android.addfast.utils.Constants
import com.xwray.groupie.kotlinandroidextensions.GroupieViewHolder
import com.xwray.groupie.kotlinandroidextensions.Item



class ProductoAdapter   (private val producListener: CategoriasProductosListener, private val books: Anuncios):
        Item(){


    override fun bind(viewHolder: GroupieViewHolder, position: Int) {
        val nombre = viewHolder.itemView.findViewById<TextView>(R.id.firsii_name)
        //  val email = itemView.findViewById<TextView>(R.id.lbl_estudiante_item_email)
        val imagen = viewHolder.itemView.findViewById<ImageView>(R.id.firsii_img)
        val desc = viewHolder.itemView.findViewById<TextView>(R.id.firsii_desc)
        nombre.text="${books.titulo}"
        Log.e("productos",books.toString())
        Glide.with(viewHolder.itemView.context).load(books.img).into(imagen)

        desc.text = books.descripcion
        viewHolder.itemView.setOnClickListener {
            producListener.onCLickItem(books,position)
        }
    }

    override fun getSpanSize(spanCount: Int, position: Int)=spanCount/1
    override fun getLayout()=R.layout.fragment_inicio_refactor_servicios_item



}
 */