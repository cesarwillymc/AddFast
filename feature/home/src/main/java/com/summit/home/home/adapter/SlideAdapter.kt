package com.summit.home.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.summit.core.network.model.Promociones
import com.summit.home.databinding.LayoutOffertCardBinding

class SlideAdapter (private val listener:(Promociones,Int)->Unit):RecyclerView.Adapter<SlideAdapter.SlideViewHolder>(){

    fun setDataImage(data:List<Promociones>){
        slidesImg=data
        notifyDataSetChanged()
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SlideViewHolder {
        val binding= LayoutOffertCardBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return SlideViewHolder(
            binding
        )
    }
    private var slidesImg:List<Promociones> = listOf()
    inner class SlideViewHolder(private val binding: LayoutOffertCardBinding) : RecyclerView.ViewHolder(binding.root){

        fun bind(get:Promociones,position: Int){
            binding.model=get
            binding.executePendingBindings()
            binding.root.setOnClickListener  {
                listener.invoke(get,position)
            }

        }
    }
    override fun getItemCount()= slidesImg.size

    override fun onBindViewHolder(holder: SlideViewHolder, position: Int) {
        if(slidesImg.isNotEmpty()){
            holder.bind(slidesImg[position],position)
        }
    }

}
