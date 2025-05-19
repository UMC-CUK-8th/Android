package com.example.week7

interface LookView {
    fun onGetSongLoading()
    fun onGetSongFailure(code: Int, message: String)
}