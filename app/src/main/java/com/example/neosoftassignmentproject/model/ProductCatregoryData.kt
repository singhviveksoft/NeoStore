package com.example.neosoftassignmentproject.model

data class Data(
    val product_categories: ArrayList<ProductCategory>,
    val total_carts: Int,
    val total_orders: Int,
    val user_data: UserData
)