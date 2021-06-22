package com.example.neosoftassignmentproject.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable
@Entity
data class ProductCategory(
    val created: String,
    val icon_image: String,
    val id: Int,
    val modified: String,
   @PrimaryKey
    val name: String
)