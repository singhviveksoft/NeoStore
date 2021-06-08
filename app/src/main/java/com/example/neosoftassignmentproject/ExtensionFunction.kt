package com.example.neosoftassignmentproject

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import coil.load

@BindingAdapter("loadImg")
fun ImageView.LoadImg(str:String){
    this.load(str){
        crossfade(true)
        crossfade(2000)
    }
}