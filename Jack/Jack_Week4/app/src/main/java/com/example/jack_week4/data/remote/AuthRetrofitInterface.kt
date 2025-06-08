package com.example.jack_week4.data.remote

import com.example.jack_week4.uis.signin.LoginRequest
import com.example.jack_week4.data.entities.User
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthRetrofitInterface {
    @POST("/join")
    fun signUp(@Body user: User): Call<AuthResponse>

    @POST("/login")
    fun login(@Body request: LoginRequest): Call<AuthResponse>
}
