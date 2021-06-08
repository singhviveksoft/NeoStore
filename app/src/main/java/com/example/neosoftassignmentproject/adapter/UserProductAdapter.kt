package com.example.neosoftassignmentproject.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.neosoftassignmentproject.databinding.UserProductBinding
import com.example.neosoftassignmentproject.model.GetUserData
import com.example.neosoftassignmentproject.model.ProductCategory

class UserProductAdapter:RecyclerView.Adapter<UserProductAdapter.UserProductViewHolder>() {
   val userProductList= arrayListOf<ProductCategory>()



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserProductViewHolder {
        val view=UserProductBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return UserProductViewHolder(view)
    }

    override fun onBindViewHolder(holder: UserProductViewHolder, position: Int) {



    }

    override fun getItemCount(): Int {
     return userProductList.size
    }

    fun addProduct(list: List<ProductCategory>){
        this.userProductList.addAll(list)

    }


    class UserProductViewHolder(binding: UserProductBinding) :RecyclerView.ViewHolder(binding.root){
        init {

        }

    }


}