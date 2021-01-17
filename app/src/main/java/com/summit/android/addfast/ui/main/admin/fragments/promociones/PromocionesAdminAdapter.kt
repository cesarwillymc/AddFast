package com.summit.android.addfast.ui.main.admin.fragments.promociones

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.summit.android.addfast.R
import com.summit.android.addfast.repo.model.Promociones
import com.summit.android.addfast.repo.model.Usuario
import com.summit.android.addfast.utils.setOnSingleClickListener
import org.jetbrains.anko.backgroundColor
import org.ocpsoft.prettytime.PrettyTime
import java.util.*

class PromocionesAdminAdapter(private val listener: Listener): RecyclerView.Adapter<PromocionesAdminAdapter.ViewHolder>() {

    var precios:MutableList<Promociones> = mutableListOf()
    var preciosinicial:MutableList<Promociones> = mutableListOf()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(
        LayoutInflater.from(parent.context).inflate(
            R.layout.fragment_usuarios_item, parent, false
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
    fun updateData(data: MutableList<Promociones>){
        precios = mutableListOf()
        preciosinicial= mutableListOf()

        precios.addAll(data)
        preciosinicial.addAll(data)
        notifyDataSetChanged()
    }


    fun getValue(position: Int) = precios[position]
    fun removeDato(posicion: Int){
        precios.removeAt(posicion)
        notifyDataSetChanged()
    }
    inner class ViewHolder(itemView: View):RecyclerView.ViewHolder(itemView){

        val imagen = itemView.findViewById<ImageView>(R.id.usuarios_img)
        val name = itemView.findViewById<TextView>(R.id.usuarios_name)
        val desc = itemView.findViewById<TextView>(R.id.usuarios_desc)
        val status = itemView.findViewById<TextView>(R.id.usuarios_status)
        val timeago = itemView.findViewById<TextView>(R.id.usuarios_timeago)
        fun bind(get: Promociones, position: Int) {
            status.text = if(get.state){"ACTIVO"} else {"DESACTIVADO"}
            if(get.state){
                status.backgroundColor = itemView.context.getColor(R.color.colorPrimary)
            }else{
                status.backgroundColor = itemView.context.getColor(R.color.red)
            }
            desc.text=""
            name.text= get.name
            val prettyTime = PrettyTime(Locale.getDefault())
            val ago: String = prettyTime.format(Date(get.fecha))
            timeago.text=ago
            Glide.with(itemView.context).load(get.img)
                .error(R.drawable.ic_historial).placeholder(R.drawable.ic_historial).into(imagen)
            itemView.setOnSingleClickListener  {
                listener.onclick(get, position)

            }
        }
    }
    interface Listener{
        fun onclick(anuncios: Promociones, position: Int)
    }

}