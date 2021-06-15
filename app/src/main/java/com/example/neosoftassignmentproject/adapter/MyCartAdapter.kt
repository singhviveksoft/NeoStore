package com.example.neosoftassignmentproject.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.neosoftassignmentproject.databinding.MyCartItemBinding
import com.example.neosoftassignmentproject.model.CartData
import com.example.neosoftassignmentproject.model.Product

class MyCartAdapter(val onClick: clickItem):RecyclerView.Adapter<MyCartAdapter.ProductViewHolder>() {
   val mycartList= arrayListOf<Product>()
   val cartDataArraylist= arrayListOf<CartData>()
    val arrays= arrayOf("1", "2", "3", "4", "5", "6", "7", "8", "9", "10")

private lateinit var context: Context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val view=MyCartItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        context=parent.context
        return ProductViewHolder(view, onClick)
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {

        holder.bind(mycartList[position], cartDataArraylist[position])

    }

    override fun getItemCount(): Int {
     return mycartList.size
    }

    fun addProduct(list: List<Product>, cartData: List<CartData>){
       this.mycartList.clear()
       this.cartDataArraylist.clear()
        this.mycartList.addAll(list)
        this.cartDataArraylist.addAll(cartData)
        notifyDataSetChanged()

    }


  inner  class ProductViewHolder(val binding: MyCartItemBinding, val IteamClick: clickItem) :RecyclerView.ViewHolder(
      binding.root
  ){

        fun bind(mycart: Product, cartData: CartData) {
            binding.model=mycart
        //  val quantity=  arrays.indexOf(cartData.quantity)

            binding.spinner.setSelection(cartData.quantity.minus(1))
        }


        init {
            binding.deleteImg.setOnClickListener {
                IteamClick.onClick(cartDataArraylist[adapterPosition], "delete")
            }


     //    val i=   binding.spinner.setSelection(Adapter.NO_SELECTION,true)

            var count: Int = 0



            binding.spinner.onItemSelectedListener=object :AdapterView.OnItemSelectedListener,
                    AdapterView.OnItemClickListener {
                    override fun onItemSelected(
                        parent: AdapterView<*>?,
                        view: View?,
                        position: Int,
                        id: Long
                    ) {

                         //  Toast.makeText(context, "onItemSelected + ${position.toString()}", Toast.LENGTH_SHORT).show()
                        //  IteamClick.onClick(cartDataArraylist[adapterPosition],"editQuantity",position)

                        if (++count>1) {
                          val currentItemvalue=  binding.spinner.selectedItem

                            IteamClick.onClick(cartDataArraylist[adapterPosition],"editQuantity",
                                currentItemvalue.toString().toInt()
                            )
                        }



                    }

                    override fun onNothingSelected(parent: AdapterView<*>?) {
                        Toast.makeText(context, "onNothingSelected", Toast.LENGTH_SHORT).show()
                    }

                    override fun onItemClick(
                        parent: AdapterView<*>?,
                        view: View?,
                        position: Int,
                        id: Long
                    ) {
                        Toast.makeText(context, "onItemClick", Toast.LENGTH_SHORT).show()

                    }

                }


            }


        }

    interface clickItem{
        fun onClick(mycartList: CartData, status: String, quantity: Number = 0)
    }


}





