/*
package com.summit.android.addfast.ui.main.user.postulaciones

import android.os.Handler
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
import com.summit.android.addfast.repo.model.Postulacion
import com.summit.android.addfast.utils.setOnSingleClickListener
import org.jetbrains.anko.backgroundColor
import org.ocpsoft.prettytime.PrettyTime
import java.util.*

class PostulacionesAdapter(private val listener: Listener): RecyclerView.Adapter<PostulacionesAdapter.ViewHolder>() {

    var precios:MutableList<Postulacion> = mutableListOf()
    var preciosinicial:MutableList<Postulacion> = mutableListOf()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(
        LayoutInflater.from(parent.context).inflate(
            R.layout.fragment_postulaciones_item, parent, false
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
    fun updateData(data: MutableList<Postulacion>){
        precios = mutableListOf()
        preciosinicial= mutableListOf()

        precios.addAll(data)
        preciosinicial.addAll(data)
        notifyDataSetChanged()
    }
    fun searchBy(dato: String) {
        precios = if(dato=="TODOS"){
            preciosinicial
        }else{
            val lista:List<Postulacion> = precios.sortedBy {
                it.estado==dato
            }.reversed()
            lista as  MutableList<Postulacion>
        }

        Handler().postDelayed({notifyDataSetChanged()},500L)
        //Log.e("anuncios","$dato  $lista y luego $precios")
    }

    fun getValue(position: Int) = precios[position]
    fun removeDato(posicion: Int){
        precios.removeAt(posicion)
        notifyDataSetChanged()
    }
    inner class ViewHolder(itemView: View):RecyclerView.ViewHolder(itemView){

        val imagen = itemView.findViewById<ImageView>(R.id.fpi_img)
        val name = itemView.findViewById<TextView>(R.id.fpi_name)
        val desc = itemView.findViewById<TextView>(R.id.fpi_desc)
        val status = itemView.findViewById<TextView>(R.id.fpi_status)
        val timeago = itemView.findViewById<TextView>(R.id.fpi_timeago)
        fun bind(get: Postulacion, position: Int) {
            status.text=get.estado
            if(get.estado=="ENVIADO"){
                status.backgroundColor = itemView.context.getColor(R.color.red)
            }else{
                status.backgroundColor = itemView.context.getColor(R.color.colorPrimary)
            }
            desc.text=get.descripcion
            name.text= get.titulo
            val prettyTime = PrettyTime(Locale.getDefault())
            val ago: String = prettyTime.format(Date(get.fecha))
            timeago.text=ago
            Glide.with(itemView.context).load(get.imganuncio)
                .error(R.drawable.ic_historial).placeholder(R.drawable.ic_historial).into(imagen)
            itemView.setOnSingleClickListener  {
                listener.onclick(get, position)

            }
        }
    }
    interface Listener{
        fun onclick(anuncios: Postulacion, position: Int)
    }

}
 */