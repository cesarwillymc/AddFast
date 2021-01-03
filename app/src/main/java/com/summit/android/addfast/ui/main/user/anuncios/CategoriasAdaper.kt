package com.summit.android.addfast.ui.main.user.anuncios

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.summit.android.addfast.R
import com.summit.android.addfast.repo.model.CategoriasModel
import com.summit.android.addfast.utils.Constants
import com.summit.android.addfast.utils.setOnSingleClickListener

class CategoriasAdaper(private val listener: CategoriasListener): RecyclerView.Adapter<CategoriasAdaper.ViewHolder>() {

    var precios:MutableList<CategoriasModel> = mutableListOf()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(
        LayoutInflater.from(parent.context).inflate(
            R.layout.fragment_services_item,parent,false))
    override fun getItemCount() = if (precios!=null) precios!!.size else 0

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(precios?.get(position)!!,position)
    }
    var positionSelected=0
    fun selectPosition(position: Int){
        positionSelected=position
        notifyDataSetChanged()
    }
    fun updateData(data: MutableList<CategoriasModel>){
        precios = mutableListOf()
        Log.e("updateData1",data.toString())
        Log.e("updateData2",precios.toString())

        precios.addAll(data)
        precios.add(0,CategoriasModel("Todos","Todos","",0))
        Log.e("updateData3",precios.toString())
        notifyDataSetChanged()
    }
    fun getValue(position: Int) = precios[position]
    fun removeDato(posicion:Int){
        precios.removeAt(posicion)
        notifyDataSetChanged()
    }
    inner class ViewHolder(itemView: View):RecyclerView.ViewHolder(itemView){

        val imagen = itemView.findViewById<ImageView>(R.id.fsi_img)
        val name = itemView.findViewById<TextView>(R.id.fsi_texto)
        val constraint = itemView.findViewById<FrameLayout>(R.id.fsi_constraint)
        fun bind(get: CategoriasModel, position: Int) {
            if(position==positionSelected){
                name.setTextColor(itemView.resources.getColor(R.color.black))
                constraint.background = itemView.context.getDrawable(R.drawable.border_button_carrito)
            }else{
                name.setTextColor(itemView.resources.getColor(R.color.gris_2))
                constraint.background = itemView.context.getDrawable(R.drawable.border_button_carrito_inactive)
            }
            name.text= get.name
            Glide.with(itemView.context ).load(get.img)
                .error(R.drawable.ic_historial).placeholder(R.drawable.ic_historial).into(imagen)
            itemView.setOnSingleClickListener  {
                listener.listener(position,get)

            }
        }
    }


}