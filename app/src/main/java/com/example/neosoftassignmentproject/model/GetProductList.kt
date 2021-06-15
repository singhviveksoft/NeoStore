package com.example.neosoftassignmentproject.model

data class GetProductList(
    val `data`: List<ProductListData>,
    val status: Int
)