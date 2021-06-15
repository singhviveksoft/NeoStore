package com.example.neosoftassignmentproject.model

data class MyOrder(
    val `data`: List<Data>,
    val message: String,
    val status: Int,
    val user_msg: String
)