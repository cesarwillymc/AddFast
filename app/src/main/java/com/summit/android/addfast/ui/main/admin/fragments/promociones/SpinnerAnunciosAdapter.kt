package com.summit.android.addfast.ui.main.admin.fragments.promociones

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import com.summit.android.addfast.R
import com.summit.android.addfast.repo.model.Anuncios

@SuppressLint("ViewHolder")
class SpinnerAnunciosAdapter(private val context:Context): BaseAdapter() {
    var inflater: LayoutInflater = LayoutInflater.from(context)
    var lisProducts:List<Anuncios>? = null
    fun updateData(data: List<Anuncios>?){
        lisProducts=null
        Log.e("adapter",data.toString())
        lisProducts=data
        notifyDataSetChanged()
    }

    fun getData(position: Int)= lisProducts?.get(position)
    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup?): View {
        var viewHolder:DropDownViewHolder
        var view= convertView
        viewHolder = if (view==null){
            view= inflater.inflate( R.layout.item_spinner_dropdown,parent,false)
            DropDownViewHolder(view!!)
        }else{
            view.tag as DropDownViewHolder
        }
        view?.tag=viewHolder
        viewHolder.textoD.text=lisProducts?.get(position)!!.titulo

        return view
    }
    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        var viewHolder:ViewHolder
        var view= convertView
        viewHolder = if (view==null){
            view= inflater.inflate( R.layout.item_spinner,parent,false)
            ViewHolder(view!!)
        }else{
            view.tag as ViewHolder
        }
        view?.tag=viewHolder

        viewHolder.texto.text=lisProducts?.get(position)!!.titulo

        return view
    }

    override fun getItem(position: Int): Any? {
        return if (lisProducts==null) null else lisProducts!![position]
    }

    override fun getItemId(position: Int): Long {
            return position.toLong()
    }

    class ViewHolder(view:View){
        lateinit var texto:TextView
        init {
            texto= view.findViewById<TextView>(R.id.spinnerValue)
        }
    }
    class DropDownViewHolder(view:View){
        lateinit var textoD:TextView
        init {
            textoD= view.findViewById<TextView>(R.id.spinnerValueDrop)
        }
    }
    override fun getCount(): Int = if (lisProducts!=null) lisProducts!!.size else 0


}