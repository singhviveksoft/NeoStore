package com.example.neosoftassignmentproject.adapter

import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.neosoftassignmentproject.databinding.SliderItemBinding


class SlideImageAdapter:RecyclerView.Adapter<SlideImageAdapter.SliderViewHolder>() {
val list= arrayListOf<Drawable>()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SliderViewHolder {
    val view= SliderItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return SliderViewHolder(view)
    }

    override fun onBindViewHolder(holder: SliderViewHolder, position: Int) {
        holder.bind(list.get(position))
    }

    override fun getItemCount(): Int {
        return list.size
    }

    fun addImages(list: ArrayList<Drawable>)
    {
        this.list.addAll(list)
    }


    class SliderViewHolder(val sliderItemBinding: SliderItemBinding):RecyclerView.ViewHolder(sliderItemBinding.root){
        fun bind(i: Drawable) {
            sliderItemBinding.sliderImg.setImageDrawable(i)

        }

    }

}