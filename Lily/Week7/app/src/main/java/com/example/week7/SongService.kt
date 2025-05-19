package com.example.week7

import android.util.Log
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SongService() {
    private lateinit var lookView: LookView

    fun setLookView(lookView: LookView) {
        this.lookView = lookView
    }


}