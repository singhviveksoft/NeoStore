package com.example.neosoftassignmentproject.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.neosoftassignmentproject.databinding.MyCartItemBinding
import com.example.neosoftassignmentproject.databinding.ProductImageSliderBinding
import com.example.neosoftassignmentproject.model.CartData
import com.example.neosoftassignmentproject.model.Product
import com.example.neosoftassignmentproject.model.ProductImage

class ProductImageAdapter(val onClick: clickItem):RecyclerView.Adapter<ProductImageAdapter.ProductViewHolder>() {
   val imageArrayList= arrayListOf<ProductImage>()





    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val view=ProductImageSliderBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return ProductViewHolder(view, onClick)
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {

        holder.bind(imageArrayList[position])

    }

    override fun getItemCount(): Int {
     return imageArrayList.size
    }

    fun addProduct(list: List<ProductImage>){
       this.imageArrayList.clear()

        this.imageArrayList.addAll(list)

        notifyDataSetChanged()

    }


  inner  class ProductViewHolder(val binding: ProductImageSliderBinding, val IteamClick: clickItem) :RecyclerView.ViewHolder(
      binding.root
  ){

        fun bind(productImage: ProductImage) {
            binding.model=productImage
        //  val quantity=  arrays.indexOf(cartData.quantity)

        }


        init {
            itemView.setOnClickListener {
                IteamClick.onClick(imageArrayList[adapterPosition])
            }






            }


        }

    interface clickItem{
        fun onClick(productImage:ProductImage)
    }


}





