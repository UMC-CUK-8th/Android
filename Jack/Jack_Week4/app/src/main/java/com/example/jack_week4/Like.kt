package com.example.jack_week4

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "LikeTable", primaryKeys = ["userId", "albumId"])
data class Like(
    val userId: Int,
    val albumId: Int
)

