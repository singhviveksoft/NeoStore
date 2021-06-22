package com.example.neosoftassignmentproject.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.neosoftassignmentproject.R
import com.example.neosoftassignmentproject.constants.UserPreferences
import com.example.neosoftassignmentproject.databinding.AddressItemBinding
import com.example.neosoftassignmentproject.databinding.UserProductBinding
import com.example.neosoftassignmentproject.model.AddAddress
import com.example.neosoftassignmentproject.model.ProductCategory
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AddressAdapter(val click:clickItem):RecyclerView.Adapter<AddressAdapter.UserProductViewHolder>() {
   val addressArraylist= arrayListOf<AddAddress>()
   var update_addressArraylist= arrayListOf<AddAddress>()
    private var selectedPosition = -1
    private lateinit var context: Context



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserProductViewHolder {
        val view=AddressItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        context=parent.context

        return UserProductViewHolder(view,click)
    }

    override fun onBindViewHolder(holder: UserProductViewHolder, position: Int) {

        holder.bind(addressArraylist[position])

    }

    override fun getItemCount(): Int {
     return addressArraylist.size
    }

    fun addProduct(list: List<AddAddress>){
        this.addressArraylist.clear()
        this.addressArraylist.addAll(list)
        notifyDataSetChanged()

    }


  inner  class UserProductViewHolder(val binding: AddressItemBinding,val item:clickItem) :RecyclerView.ViewHolder(binding.root) {
// on bind
      fun bind(address: AddAddress) {
          binding.model = address

         if (adapterPosition==selectedPosition) {
             binding.radioButton.isChecked = true
             binding.relativeLt.setBackgroundResource(R.drawable.selected_address_bg)
         }
          else{
             binding.radioButton.isChecked = false
             binding.relativeLt.setBackgroundResource(R.drawable.un_selected_address_bg)
          }
      }

      init {

        /**/  binding.radioButton.setOnClickListener {
              selectedPosition = adapterPosition
             notifyDataSetChanged()
              val address=binding.addressTxt.text.toString()

            CoroutineScope(Dispatchers.IO).launch{
                adddress(address)
            }


          }




          binding.removeImg.setOnClickListener {
            //  val position=adapterPosition
              item.onClick(addressArraylist[adapterPosition])
               //  addressArraylist.removeAt(position)
              notifyDataSetChanged()
          }

          //  itemView.setOnClickListener {

          // item.onClick(addressArraylist[adapterPosition] )
          //   }


      }

        suspend fun adddress(address: String){
            UserPreferences(context).setAddress(address)
            Log.i("Useraddress","$address")
        }


  }




        interface clickItem{
            fun onClick(address: AddAddress)
        }

}