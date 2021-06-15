package com.example.neosoftassignmentproject.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.neosoftassignmentproject.databinding.MyOrderItemBinding
import com.example.neosoftassignmentproject.model.MyOrderData

class MyOrderAdapter(val item:onItemClick):RecyclerView.Adapter<MyOrderAdapter.ViewHolder>() {
    val arrayList=ArrayList<MyOrderData>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view=MyOrderItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ViewHolder(view,item)

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(arrayList[position])
    }

    override fun getItemCount(): Int {
        return arrayList.size
    }

    fun addOrder(list: List<MyOrderData>){
        this.arrayList.addAll(list)
        notifyDataSetChanged()
    }

   inner class ViewHolder(val binding:MyOrderItemBinding,val item:onItemClick):RecyclerView.ViewHolder(binding.root){

        fun bind(myOrderData: MyOrderData) {
        binding.model=myOrderData
        }


        init {
            itemView.setOnClickListener {
                item.itemClick(arrayList[adapterPosition])
            }

        }

    }

    interface onItemClick{
        fun itemClick(myOrderData: MyOrderData)
    }

}