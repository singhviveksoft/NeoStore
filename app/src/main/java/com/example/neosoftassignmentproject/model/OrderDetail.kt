package com.example.neosoftassignmentproject.model

data class OrderDetail(
    val `data`: OrderData,
    val status: Int
)

data class OrderData(
    val address: String,
    val cost: Int,
    val id: Int,
    val order_details: List<OrderDetailData>
)


data class OrderDetailData(
    val id: Int,
    val order_id: Int,
    val prod_cat_name: String,
    val prod_image: String,
    val prod_name: String,
    val product_id: Int,
    val quantity: Int,
    val total: Int
)