package com.chrome.umcflo


import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthApi {
    @POST("/users")
    fun signUp(@Body user : User) : Call<BaseResponse>

    @POST("/users/login")
    fun login(@Body user : User) : Call<BaseResponse>
}