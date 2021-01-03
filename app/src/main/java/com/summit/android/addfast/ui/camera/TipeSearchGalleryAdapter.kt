package com.summit.android.addfast.ui.camera

import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.summit.android.addfast.R

class TipeSearchGalleryAdapter(private val Listener: TipeSearchGalleryListener): RecyclerView.Adapter<TipeSearchGalleryAdapter.ViewHolder>() {

    var listPedido:List<GalleryDocs> = listOf()
    var pisicion:Int=0

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(
        LayoutInflater.from(parent.context).inflate(
        R.layout.layout_tipe_search_gallery,parent,false))
    override fun getItemCount() = if (listPedido!=null) listPedido!!.size else 0

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        holder.bind(listPedido?.get(position)!!,position)
    }
    fun setearPosition(dato:Int){

        pisicion=dato
        notifyDataSetChanged()
    }
    fun updateData(dato :List<GalleryDocs> ){
        if (listPedido.isEmpty())
            listPedido= listOf()
        listPedido=dato
        notifyDataSetChanged()
    }
    fun getData()= listPedido

    inner class ViewHolder(itemView: View):RecyclerView.ViewHolder(itemView){

        val name = itemView.findViewById<TextView>(R.id.text_tipe_search)

        fun bind(get: GalleryDocs, position: Int) {
            Log.e("datosadapter$position",get.toString())
            if (pisicion==position)
                name.setTextColor(Color.WHITE   )
            else
                name.setTextColor( Color.GRAY)
            name.text= get.name

            itemView.setOnClickListener {
                Log.e("entro","onclickGallery ${get.directorio}")
                Listener.onclickGallery(get.directorio,position)
            }
        }
    }
    data class GalleryDocs(
            val name:String,
            val directorio:String
    )

    interface TipeSearchGalleryListener {
        fun onclickGallery(dato: String, position: Int)
    }
}