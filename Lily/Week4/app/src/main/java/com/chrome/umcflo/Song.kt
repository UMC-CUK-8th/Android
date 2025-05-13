package com.chrome.umcflo

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "SongTable")
data class Song(
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0,

    var title: String = "",
    var singer: String = "",

    var playTime: Int = 0,
    var second: Int = 0,

    var isPlaying: Boolean = false,
    var music: String Int? = null,
    var coverImg: Int? = null,
    var isLike: Boolean = false


    var albumIdx: Int = 0
)
