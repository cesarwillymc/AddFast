package com.summit.android.addfast.ui.main.service

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.summit.android.addfast.R
import com.summit.android.addfast.repo.model.Anuncios
import com.summit.android.addfast.utils.setOnSingleClickListener
import org.jetbrains.anko.backgroundColor
import org.ocpsoft.prettytime.PrettyTime
import java.util.*

class AnunciosAdapter(private val listener: Listener): RecyclerView.Adapter<AnunciosAdapter.ViewHolder>() {

    var precios:MutableList<Anuncios> = mutableListOf()
    var preciosinicial:MutableList<Anuncios> = mutableListOf()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(
        LayoutInflater.from(parent.context).inflate(
            R.layout.fragment_anuncios_item, parent, false
        )
    )
    override fun getItemCount() = if (precios!=null) precios!!.size else 0

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(precios?.get(position)!!, position)
    }
    var positionSelected=0
    fun selectPosition(position: Int){
        positionSelected=position
        notifyDataSetChanged()
    }
    fun updateData(data: MutableList<Anuncios>){
        precios = mutableListOf()
        preciosinicial= mutableListOf()
        Log.e("Datos","$data")
        precios.addAll(data)
        preciosinicial.addAll(data)
        notifyDataSetChanged()
    }
    fun getValue(position: Int) = precios[position]
    fun removeDato(posicion: Int){
        precios.removeAt(posicion)
        notifyDataSetChanged()
    }

    fun searchBy(dato: String) {
        if(precios.isNotEmpty()) {
            precios = if(dato=="TODOS"){
                preciosinicial
            }else{
                val lista:List<Anuncios> = precios.sortedBy {
                    it.estado==dato
                }.reversed()
                lista as  MutableList<Anuncios>
            }
        }
        notifyDataSetChanged()
        //Log.e("anuncios","$dato  $lista y luego $precios")
    }

    inner class ViewHolder(itemView: View):RecyclerView.ViewHolder(itemView){

        val imagen = itemView.findViewById<ImageView>(R.id.fai_img)
        val name = itemView.findViewById<TextView>(R.id.fai_name)
        val desc = itemView.findViewById<TextView>(R.id.fai_desc)
        val status = itemView.findViewById<TextView>(R.id.fai_status)
        val timeago = itemView.findViewById<TextView>(R.id.fai_timeago)
        val postulaciones = itemView.findViewById<TextView>(R.id.fai_postulaciones)
        fun bind(get: Anuncios, position: Int) {
            status.text=get.estado
            if(get.estado=="PUBLICADO"){
                status.backgroundColor = itemView.context.getColor(R.color.red)
            }else{
                status.backgroundColor = itemView.context.getColor(R.color.colorPrimary)
            }
            desc.text=get.descripcion
            name.text= get.titulo
            val prettyTime = PrettyTime(Locale.getDefault())
            val ago: String = prettyTime.format(Date(get.fecha))
            timeago.text=ago
            if (get.type!="Trabajos"){
                postulaciones.visibility=View.GONE
            }
            postulaciones.text=get.postulaciones.size.toString()
            Glide.with(itemView.context).load(get.img)
                .error(R.drawable.ic_historial).placeholder(R.drawable.ic_historial).into(imagen)
            itemView.setOnSingleClickListener  {
                listener.onclick(get, position)

            }
        }
    }
    interface Listener{
        fun onclick(anuncios: Anuncios, position: Int)
    }

}