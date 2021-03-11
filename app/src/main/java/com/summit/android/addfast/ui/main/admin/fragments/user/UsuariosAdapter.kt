package com.summit.android.addfast.ui.main.admin.fragments.user

import android.os.Handler
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.summit.android.addfast.R
import com.summit.android.addfast.repo.model.Anuncios
import com.summit.android.addfast.repo.model.Usuario
import com.summit.android.addfast.utils.setOnSingleClickListener
import org.jetbrains.anko.backgroundColor

class UsuariosAdapter(private val listener: Listener) :
    RecyclerView.Adapter<UsuariosAdapter.ViewHolder>() {

    var precios: MutableList<Usuario> = mutableListOf()
    var preciosinicial: MutableList<Usuario> = mutableListOf()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(
        LayoutInflater.from(parent.context).inflate(
            R.layout.fragment_usuarios_item, parent, false
        )
    )

    override fun getItemCount() = if (precios != null) precios!!.size else 0

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(precios?.get(position)!!, position)
    }

    var positionSelected = 0
    fun selectPosition(position: Int) {
        positionSelected = position
        notifyDataSetChanged()
    }
    fun searchData(palabra:String){
        precios=if(palabra==""){
            preciosinicial
        }else{
            val lista:List<Usuario> = precios.sortedBy {
                it.name?.toUpperCase()?.contains(palabra.toUpperCase())
            }.reversed()
            lista as  MutableList<Usuario>
        }
        Handler().postDelayed({notifyDataSetChanged()},500L)
    }
    fun updateData(data: MutableList<Usuario>) {
        precios = mutableListOf()
        preciosinicial = mutableListOf()

        precios.addAll(data)
        preciosinicial.addAll(data)
        notifyDataSetChanged()
    }


    fun getValue(position: Int) = precios[position]
    fun removeDato(posicion: Int) {
        precios.removeAt(posicion)
        notifyDataSetChanged()
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val imagen = itemView.findViewById<ImageView>(R.id.usuarios_img)
        val name = itemView.findViewById<TextView>(R.id.usuarios_name)
        val desc = itemView.findViewById<TextView>(R.id.usuarios_desc)
        val status = itemView.findViewById<TextView>(R.id.usuarios_status)
        val timeago = itemView.findViewById<TextView>(R.id.usuarios_timeago)
        fun bind(get: Usuario, position: Int) {
            status.text = if (get.accountactivate) {
                "ACTIVO"
            } else {
                "DESACTIVADO"
            }
            if (get.accountactivate) {
                status.backgroundColor = itemView.context.getColor(R.color.colorPrimary)
            } else {
                status.backgroundColor = itemView.context.getColor(R.color.red)
            }
            desc.text = get.phone
            name.text = get.name
            get.admin?.let {
                Log.e("usuarios","$get")
                timeago.text = when {
                    get.admin!! -> {
                        "ADMIN"
                    }
                    get.nameEmpresa != "" -> {
                        "EMPRESA"
                    }
                    else -> {
                        "USUARIO"
                    }
                }
            }

            Glide.with(itemView.context).load(get.uriImgPerfil)
                .error(R.drawable.ic_historial).placeholder(R.drawable.ic_historial).into(imagen)
            itemView.setOnSingleClickListener {
                listener.onclick(get, position)

            }
        }
    }

    interface Listener {
        fun onclick(anuncios: Usuario, position: Int)
    }

}