package com.example.neosoftassignmentproject.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.neosoftassignmentproject.databinding.ProductListItemBinding
import com.example.neosoftassignmentproject.databinding.UserProductBinding
import com.example.neosoftassignmentproject.model.ProductCategory
import com.example.neosoftassignmentproject.model.ProductListData

class ProductListAdapter(val onClick:clickItem):RecyclerView.Adapter<ProductListAdapter.ProductViewHolder>() {
   val ProductList= arrayListOf<ProductListData>()



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val view=ProductListItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ProductViewHolder(view,onClick)
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {

        holder.bind(ProductList[position])

    }

    override fun getItemCount(): Int {
     return ProductList.size
    }

    fun addProduct(list: List<ProductListData>){
       this.ProductList.clear()
        this.ProductList.addAll(list)
        notifyDataSetChanged()

    }


  inner  class ProductViewHolder(val binding: ProductListItemBinding,val onClick:clickItem) :RecyclerView.ViewHolder(binding.root){

        fun bind(productList: ProductListData) {
            binding.model=productList
        }

        init {
            itemView.setOnClickListener {
                onClick.onClick(ProductList[adapterPosition] )
            }

        }

    }
        interface clickItem{
            fun onClick(productlist: ProductListData)
        }

}