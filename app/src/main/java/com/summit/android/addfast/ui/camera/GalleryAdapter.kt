package com.summit.android.addfast.ui.camera

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.summit.android.addfast.R
import java.io.File

class GalleryAdapter(private val Listener: clickListener): RecyclerView.Adapter<GalleryAdapter.ViewHolder>() {

    var listPedido:List<File> = listOf()
    var posiciionDato:String?=null
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(
        LayoutInflater.from(parent.context).inflate(
        R.layout.imagen_carrusel,parent,false))
    override fun getItemCount() = if (listPedido!=null) listPedido!!.size else 0

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(listPedido?.get(position)!!,position)
    }
    fun selectData(position: String?){
        posiciionDato= position
        notifyDataSetChanged()
    }
    fun updateData(data: List<File>?){

        listPedido = if(data==null){
            listOf()
        }else{
            data!!
        }
        notifyDataSetChanged()
    }

    fun getData()= listPedido
    inner class ViewHolder(itemView: View):RecyclerView.ViewHolder(itemView){

        val imagen = itemView.findViewById<ImageView>(R.id.imagecarrusel)
        val selectImage = itemView.findViewById<ImageView>(R.id.select_image)
        fun bind(get: File, position: Int) {

            when (posiciionDato) {
                null -> {
                    selectImage.visibility=View.GONE
                }
                get.path -> {
                    Log.e("datosadapter$position","$posiciionDato \n ${get.path}")
                    selectImage.visibility=View.VISIBLE
                }
                else -> {
                    selectImage.visibility=View.GONE
                }
            }
            var requestOptions = RequestOptions()
            requestOptions = requestOptions.transforms(CenterCrop(), RoundedCorners(24))
            Glide.with(itemView.context)
                .load(get)
                .apply(requestOptions)
                .into(imagen)

            itemView.setOnClickListener {
                if (posiciionDato==get.path){
                    Listener.click(null,position)
                }else {
                    Listener.click(get,position)

                }
//(posiciionDato==null) if (posiciionDato==get.path)
            }
        }
    }

    interface clickListener{
        fun click(path: File?, position: Int?)
    }
}