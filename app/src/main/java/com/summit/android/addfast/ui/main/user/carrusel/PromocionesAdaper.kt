/*
package com.summit.android.addfast.ui.main.user.carrusel

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions.bitmapTransform
import com.summit.android.addfast.R
import com.summit.android.addfast.repo.model.Promociones
import com.summit.android.addfast.utils.Constants
import com.summit.android.addfast.utils.setOnSingleClickListener
import jp.wasabeef.glide.transformations.BlurTransformation
import java.util.*

class PromocionesAdaper(private val listener: onCLickListenerPromo): RecyclerView.Adapter<PromocionesAdaper.ViewHolder>() {

    var precios:MutableList<Promociones> = mutableListOf()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(
        LayoutInflater.from(parent.context).inflate(
            R.layout.fragment_inicio_ofertas_item,parent,false))
    override fun getItemCount() = if (precios!=null) precios!!.size else 0

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(precios?.get(position)!!,position)
    }

    fun updateData(data: MutableList<Promociones>){
        Log.e("updateData",data.toString())
        precios.clear()
        precios =data
        notifyDataSetChanged()
    }
    fun removeDato(posicion:Int){
        precios.removeAt(posicion)
        notifyDataSetChanged()
    }
    inner class ViewHolder(itemView: View):RecyclerView.ViewHolder(itemView){

        val derecha = itemView.findViewById<ImageView>(R.id.ofertas_item_derecha)
        val izquierda = itemView.findViewById<ImageView>(R.id.ofertas_item_izquierda)
        val img = itemView.findViewById<ImageView>(R.id.ofertas_item_img)
        val img_blur = itemView.findViewById<ImageView>(R.id.ofertas_item_img_blur)
        fun bind(get: Promociones, position: Int) {
            val decimal = (0..9).random()*0.1
            Log.e("datos","$get")
           // review.text ="(${(50..250).random()} reviews)"

            Glide.with(itemView.context ).load(get.img)
                .error(R.drawable.grad_splash).into(img)

            Glide.with(itemView.context ).load(get.img)
                    .apply(bitmapTransform(BlurTransformation(5, 2)))
                .error(R.drawable.grad_splash).into(img_blur)
            itemView.setOnSingleClickListener  {
                listener.onCLickItem(get,position)
            }
            derecha.setOnSingleClickListener {
                listener.onClickDerecha(position,precios.size)
            }
            izquierda.setOnSingleClickListener {
                listener.onClickIzquierda(position,precios.size)
            }
        }
    }
    interface onCLickListenerPromo{
        fun onClickIzquierda(position: Int,total:Int)
        fun onClickDerecha(position: Int,total:Int)
        fun onCLickItem(item:Promociones,position: Int)
    }


}
 */