package com.example.jack_week4

data class Album(
    val title: String? = "",
    val singer: String? = "",
    val coverImage: Int? = null,
    var songs: ArrayList<Song>? = null
)