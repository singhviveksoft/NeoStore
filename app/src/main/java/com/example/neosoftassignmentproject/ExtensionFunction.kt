package com.example.neosoftassignmentproject

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import coil.load

@BindingAdapter("loadImg")
fun ImageView.LoadImg(str:String){

  try {
      this.load(str){
          crossfade(true)
          crossfade(2000)
      }

  }catch (ex:Exception){

  }
}



fun <T> LiveData<T>.observeOnce(lifecycleOwner: LifecycleOwner, observer: Observer<T>) {
    observe(lifecycleOwner, object : Observer<T> {
        override fun onChanged(t: T?) {
            observer.onChanged(t)
            removeObserver(this)
        }
    })
}