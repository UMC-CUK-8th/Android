package com.chrome.umcflo

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "SongTable")
data class Song(
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0, // 고유 식별자

    var title: String = "", // 곡 제목
    var singer: String = "", // 가수 이름

    var playTime: Int = 0, // 전체 재생 시간 (ms 또는 sec)
    var second: Int = 0, // 현재 재생 위치 (초 단위)

    var isPlaying: Boolean = false, // 재생 중 여부
    var isLike: Boolean = false, // 좋아요 여부

    var music: String = "", // 음악 파일 경로 or 리소스명
    var coverImg: Int? = null, // 앨범 커버 이미지 리소스 ID

    var albumIdx: Int = 0 // 소속 앨범 ID
)
