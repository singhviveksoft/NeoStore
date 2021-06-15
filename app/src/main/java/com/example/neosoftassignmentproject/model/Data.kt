package com.example.neosoftassignmentproject.model

data class Data(
    val address: String,
    val cost: Int,
    val id: Int,
    val order_details: List<OrderDetailX>
)