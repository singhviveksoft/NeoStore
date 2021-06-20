package com.example.neosoftassignmentproject.model

import androidx.room.Entity
import androidx.room.PrimaryKey
@Entity
data class AddAddress(
    @PrimaryKey(autoGenerate = true)
    val id:Int=0,
    val address:String,val landmark:String,val city:String,val state:String,val zip:String,val country:String)
{
    val _address:String
    get() =address+" "+landmark+" "+zip+" "+city+""+state+" "+zip+" "+country
}
