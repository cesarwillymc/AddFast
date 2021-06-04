/*
package com.summit.android.addfast.ui.main.service

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

class VerPostulacionesAnuncioAdapter(private val listener: Listener): RecyclerView.Adapter<VerPostulacionesAnuncioAdapter.ViewHolder>() {

    var precios:MutableList<Postulacion> = mutableListOf()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(
        LayoutInflater.from(parent.context).inflate(
            R.layout.fragment_categorias_anuncios_item, parent, false
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

        precios.addAll(data)
        notifyDataSetChanged()
    }
    fun getValue(position: Int) = precios[position]
    fun removeDato(posicion: Int){
        precios.removeAt(posicion)
        notifyDataSetChanged()
    }
    inner class ViewHolder(itemView: View):RecyclerView.ViewHolder(itemView){

        val imagen = itemView.findViewById<ImageView>(R.id.fcai_img)
        val name = itemView.findViewById<TextView>(R.id.fcai_name)
        val desc = itemView.findViewById<TextView>(R.id.fcai_desc)
        val timeago = itemView.findViewById<TextView>(R.id.fcai_timeago)
        fun bind(get: Postulacion, position: Int) {

            desc.text=get.diferencia
            name.text= get.name
            val prettyTime = PrettyTime(Locale.getDefault())
            val ago: String = prettyTime.format(Date(get.fecha))
            timeago.text=ago
            Glide.with(itemView.context).load(get.imgpostulante)
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