package com.example.week7

import retrofit2.Call
import retrofit2.http.GET

interface SongApi {
    @GET("/songs")
    fun getSongs(): Call<SongResponse>
}