package com.example.neosoftassignmentproject.model

data class AddToCart(
    val `data`: Boolean,
    val message: String,
    val status: Int,
    val total_carts: Int,
    val user_msg: String
)