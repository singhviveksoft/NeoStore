package com.example.neosoftassignmentproject.model

data class MyOrder(
    val `data`: List<MyOrderData>,
    val message: String,
    val status: Int,
    val user_msg: String
)

data class MyOrderData(
    val cost: Int,
    val created: String,
    val id: Int
)