package com.example.neosoftassignmentproject.model

data class AddAddress(val address:String,val landmark:String,val city:String,val state:String,val zip:String,val country:String)
{
    val _address:String
    get() =address+" "+landmark+" "+zip+" "+city+""+state+" "+zip+" "+country
}
