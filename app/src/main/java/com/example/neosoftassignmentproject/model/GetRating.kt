package com.example.neosoftassignmentproject.model

data class GetRating(
    val `data`: Data,
    val message: String,
    val status: Int,
    val user_msg: String
)