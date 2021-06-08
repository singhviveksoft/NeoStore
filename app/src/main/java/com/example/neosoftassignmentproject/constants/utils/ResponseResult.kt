package com.example.neosoftassignmentproject.constants.utils

import androidx.lifecycle.LiveData
import okhttp3.ResponseBody

sealed class ResponseResult<T>(){
   data class Sucess<T>(val status: LiveData<T>?):ResponseResult<T>()
   data class Failed<T>(
            val isNetworkError:Boolean,
            val errorCode:Int?,
            val errorBody: ResponseBody?):ResponseResult<Nothing>()
}
