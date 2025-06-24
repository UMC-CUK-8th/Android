package com.example.week7.data.remote

import com.example.week7.data.entities.BaseResponse
import com.example.week7.data.entities.LoginRequest
import com.example.week7.data.entities.LoginResult
import com.example.week7.data.entities.SignUpResult
import com.example.week7.data.entities.User
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthRetrofitInterface {
    @POST("/join")
    fun signUp(@Body user: User) : Call<BaseResponse<SignUpResult>>


    @POST("/login")
    fun login(@Body request: LoginRequest): Call<BaseResponse<LoginResult>>


}