package com.summit.home.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.summit.core.network.model.Promociones
import com.summit.home.databinding.LayoutOffertCardBinding

class SlideAdapter (private val listener:OnCLickListenerPromo):RecyclerView.Adapter<SlideAdapter.SlideViewHolder>(){

    fun setDataImage(data:List<Promociones>){
        if(data.isNotEmpty())
            slidesImg= listOf()
        slidesImg=data
        notifyDataSetChanged()
    }
    fun getData()= slidesImg

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SlideViewHolder {
        val binding= LayoutOffertCardBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return SlideViewHolder(
            binding
        )
    }
    private var slidesImg:List<Promociones> = listOf()
    inner class SlideViewHolder(private val binding: LayoutOffertCardBinding) : RecyclerView.ViewHolder(binding.root){

        fun bind(get:Promociones){
            binding.model=get
            binding.executePendingBindings()
            binding.root.setOnClickListener  {
                listener.onCLickItem(get,position)
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
    interface OnCLickListenerPromo{
        fun onCLickItem(item:Promociones,position: Int)
    }
}
