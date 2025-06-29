package com.example.week7.data.entities

import com.google.gson.annotations.SerializedName

data class SongResponse(
    @SerializedName("isSuccess") val isSuccess: Boolean,
    @SerializedName("code") val code: Int,
    @SerializedName("message") val message: String,

)

data class FloChartResult(
    @SerializedName("songs") val songs: List<FloChartSongs>
)

data class FloChartSongs(
    @SerializedName("songIdx") val songIdx: Int,
    @SerializedName("albumIdx") val albumIdx: Int,
    @SerializedName("singer") val singer: String,
    @SerializedName("title") val title:String,
    @SerializedName("coverImgUrl") val coverImgUrl : String
)