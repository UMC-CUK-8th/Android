package com.example.jack_week4

data class SongFirebase(
    var id: Int = 0,
    var title: String = "",
    var singer: String = "",
    var second: Int = 0,
    var playTime: Int = 0,
    var isPlaying: Boolean = false,
    var music: String = "",
    var coverImg: Int = 0,
    var isLike: Boolean = false,
)
