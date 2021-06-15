package com.example.neosoftassignmentproject.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.neosoftassignmentproject.databinding.OrderDetailBinding
import com.example.neosoftassignmentproject.model.OrderDetailData

class OrderDetailAdapter:RecyclerView.Adapter<OrderDetailAdapter.ViewHolder>() {
val arrayList= arrayListOf<OrderDetailData>()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view=OrderDetailBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
       holder.bind(arrayList[position])
    }

    override fun getItemCount(): Int {
      return  arrayList.size
    }

    fun addOrderDetail(list: List<OrderDetailData>){
        arrayList.addAll(list)
        notifyDataSetChanged()
    }
   class ViewHolder(val binding:OrderDetailBinding):RecyclerView.ViewHolder(binding.root) {

        fun bind(orderDetailData: OrderDetailData) {
            binding.model=orderDetailData
        }

        init {

        }

    }

}