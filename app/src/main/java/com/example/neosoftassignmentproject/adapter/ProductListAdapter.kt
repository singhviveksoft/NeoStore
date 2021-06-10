package com.example.neosoftassignmentproject.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.neosoftassignmentproject.databinding.UserProductBinding
import com.example.neosoftassignmentproject.model.ProductCategory

class UserProductAdapter(val click:clickItem):RecyclerView.Adapter<UserProductAdapter.UserProductViewHolder>() {
   val userProductList= arrayListOf<ProductCategory>()



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserProductViewHolder {
        val view=UserProductBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return UserProductViewHolder(view,click)
    }

    override fun onBindViewHolder(holder: UserProductViewHolder, position: Int) {

        holder.bind(userProductList[position])

    }

    override fun getItemCount(): Int {
     return userProductList.size
    }

    fun addProduct(list: List<ProductCategory>){
       this.userProductList.clear()
        this.userProductList.addAll(list)
        notifyDataSetChanged()

    }


  inner  class UserProductViewHolder(val binding: UserProductBinding,val item:clickItem) :RecyclerView.ViewHolder(binding.root){

        fun bind(productCategory: ProductCategory) {
            binding.model=productCategory
        }

        init {
            itemView.setOnClickListener {
                item.onClick(userProductList[adapterPosition] )
            }

        }

    }
        interface clickItem{
            fun onClick(productCategory: ProductCategory)
        }

}