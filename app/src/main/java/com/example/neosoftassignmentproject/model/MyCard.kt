package com.example.neosoftassignmentproject.model

data class MyCard(
    val count: Int,
    val `data`: List<CartData>,
    val status: Int,
    val total: Double
)
data class CartData(
    val id: Int,
    val product: Product,
    val product_id: Int,
    val quantity: Int
)

data class Product(
    val cost: Int,
    val id: Int,
    val name: String,
    val product_category: String,
    val product_images: String,
    val sub_total: Int
)