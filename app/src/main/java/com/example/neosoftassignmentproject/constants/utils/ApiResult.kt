package com.example.neosoftassignmentproject.constants.utils

sealed class ApiResult{
    data class Success(val msg:String) : ApiResult()
    data class Error(val message: String) : ApiResult()
    object Loading : ApiResult()
  //  object Empty : ApiResult()
}
