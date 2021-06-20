package com.example.neosoftassignmentproject.constants.utils

import androidx.lifecycle.LiveData
import okhttp3.ResponseBody

data class ApiResponse<out T>(val apiStatus: ApiStatus, val data: T?, val message: String?) {
    companion object {
        fun <T> success(data: T): ApiResponse<T> = ApiResponse(apiStatus = ApiStatus.SUCCESS, data = data, message = null)

        fun <T> error(data: T?, message: String): ApiResponse<T> =
            ApiResponse(apiStatus = ApiStatus.ERROR, data = data, message = message)

     //   fun <T> loading(data: T?): ApiResponse<T> = ApiResponse(apiStatus = ApiStatus.LOADING, data = data, message = null)
    }
}