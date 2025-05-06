package com.flow.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "SongTable")
data class Song(
    var title: String = "",       // 변경됨!
    var singer: String = "",      // 변경됨!
    var second: Int = 0,
    var playTime: Int = 0,
    var isPlaying: Boolean = false,
    var music: String = "",
    var coverImg: Int? = null,
    var isLike: Boolean = false,
    var isChecked: Boolean = false // 스위치 상태 저장용 변수 추가

) {
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
}
