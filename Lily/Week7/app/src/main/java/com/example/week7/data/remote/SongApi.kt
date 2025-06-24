package com.example.week7.data.remote

import com.example.week7.data.entities.SongResponse
import retrofit2.Call
import retrofit2.http.GET

interface SongApi {
    @GET("/songs")
    fun getSongs(): Call<SongResponse>
}