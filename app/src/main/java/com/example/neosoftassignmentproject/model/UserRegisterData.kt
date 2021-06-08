package com.example.neosoftassignmentproject.model

data class UserRegisterData(
    val `data`: RegisterData,
    val message: String,
    val status: Int,
    val user_msg: String
)