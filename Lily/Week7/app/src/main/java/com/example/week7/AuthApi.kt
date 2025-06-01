package com.example.week7

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthRetrofitInterface {
    @POST("/join")
    fun signUp(@Body user: User) : Call<BaseResponse<SignUpResult>>


    @POST("/login")
    fun login(@Body request: LoginRequest): Call<BaseResponse<LoginResult>>


}