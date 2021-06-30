package com.summit.dynamicfeatures.navhost.dialog

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import com.summit.core.network.model.departamento.ProvinciaItem
import com.summit.dynamicfeatures.navhost.R

@SuppressLint("ViewHolder")
class SpinnerAdapter( context:Context): BaseAdapter() {
    var inflater: LayoutInflater = LayoutInflater.from(context)
    var lisProducts:List<ProvinciaItem>? = null
    fun updateData(data: List<ProvinciaItem>?){
        lisProducts=null
        Log.e("adapter",data.toString())
        lisProducts=data
        notifyDataSetChanged()
    }
    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val viewHolder:DropDownViewHolder
        var view= convertView
        viewHolder = if (view==null){
            view= inflater.inflate( R.layout.item_spinner_dropdown,parent,false)
            DropDownViewHolder(view!!)
        }else{
            view.tag as DropDownViewHolder
        }
        view.tag =viewHolder
        viewHolder.textoD.text=lisProducts?.get(position)!!.name

        return view
    }
    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val viewHolder:ViewHolder
        var view= convertView
        viewHolder = if (view==null){
            view= inflater.inflate( R.layout.item_spinner,parent,false)
            ViewHolder(view!!)
        }else{
            view.tag as ViewHolder
        }
        view.tag =viewHolder

        viewHolder.texto.text=lisProducts?.get(position)!!.name

        return view
    }

    override fun getItem(position: Int): Any? {
        return if (lisProducts==null) null else lisProducts!![position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    class ViewHolder(view:View){
        var texto:TextView = view.findViewById(R.id.spinnerValue)
    }
    class DropDownViewHolder(view:View){
        var textoD:TextView = view.findViewById(R.id.spinnerValueDrop)
    }
    override fun getCount(): Int = if (lisProducts!=null) lisProducts!!.size else 0


}