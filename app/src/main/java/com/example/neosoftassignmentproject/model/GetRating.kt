package com.example.neosoftassignmentproject.model

data class GetRating(
    val `data`: Data,
    val message: String,
    val status: Int,
    val user_msg: String
)
data class Data(
    val cost: Int,
    val created: String,
    val description: String,
    val id: Int,
    val modified: String,
    val name: String,
    val producer: String,
    val product_category_id: Int,
    val rating: Double,
    val view_count: Int
)