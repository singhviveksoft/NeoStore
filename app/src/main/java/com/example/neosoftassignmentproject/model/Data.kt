package com.example.neosoftassignmentproject.model

data class Data(
    val product_categories: List<ProductCategory>,
    val total_carts: Int,
    val total_orders: Int,
    val user_data: UserData
)