package com.example.neosoftassignmentproject.model

data class UserLoginData(
    val `data`: LoginData,
    val message: String,
    val status: Int,
    val user_msg: String
)