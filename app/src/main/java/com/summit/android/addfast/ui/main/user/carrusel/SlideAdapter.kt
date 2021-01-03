package com.summit.android.addfast.ui.main.user.carrusel

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.summit.android.addfast.R
import com.summit.android.addfast.repo.model.Promociones
import com.summit.android.addfast.utils.Constants
import com.summit.android.addfast.utils.setOnSingleClickListener
import jp.wasabeef.glide.transformations.BlurTransformation

class SlideAdapter (private val listener:onCLickListenerPromo):RecyclerView.Adapter<SlideAdapter.SlideViewHolder>(){

    fun setDataImage(data:List<Promociones>){
        if(data.isNotEmpty())
            slidesImg= listOf()
        slidesImg=data
        notifyDataSetChanged()
    }
    fun getData()= slidesImg
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SlideViewHolder {
        return SlideViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.fragment_inicio_ofertas_item,
                parent,
                false
            )
        )
    }
    private var slidesImg:List<Promociones> = listOf()
    inner class SlideViewHolder(view: View) : RecyclerView.ViewHolder(view){
        val derecha = itemView.findViewById<ImageView>(R.id.ofertas_item_derecha)
        val izquierda = itemView.findViewById<ImageView>(R.id.ofertas_item_izquierda)
        val img = itemView.findViewById<ImageView>(R.id.ofertas_item_img)
        val img_blur = itemView.findViewById<ImageView>(R.id.ofertas_item_img_blur)
        fun bind(get:Promociones){
            val decimal = (0..9).random()*0.1
            //name.text= get._idnameProduct

            Glide.with(itemView.context ).load(get.img)
                    .error(R.drawable.grad_splash).into(img)

            Glide.with(itemView.context ).load(get.img)
                    .apply(RequestOptions.bitmapTransform(BlurTransformation(5, 2)))
                    .error(R.drawable.grad_splash).into(img_blur)
            itemView.setOnSingleClickListener  {
                listener.onCLickItem(get,position)
            }
            derecha.setOnSingleClickListener {
                listener.onClickDerecha(position,slidesImg.size)
            }
            izquierda.setOnSingleClickListener {
                listener.onClickIzquierda(position,slidesImg.size)
            }
        }
    }
    override fun getItemCount(): Int {
        return slidesImg.size ?: 0
    }

    override fun onBindViewHolder(holder: SlideViewHolder, position: Int) {
        if(slidesImg.isNotEmpty()){
            holder.bind(slidesImg[position])
        }
    }
    interface onCLickListenerPromo{
        fun onClickIzquierda(position: Int,total:Int)
        fun onClickDerecha(position: Int,total:Int)
        fun onCLickItem(item:Promociones,position: Int)
    }
}