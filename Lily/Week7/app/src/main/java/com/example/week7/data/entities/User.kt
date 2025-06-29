package com.example.week7.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "UserTable")
data class User(
    @SerializedName("email")
    var email : String,

    @SerializedName("password")
    var password : String,

    @SerializedName("name")
    var name : String =""
) {
    @PrimaryKey(autoGenerate = true) var id : Int = 0
}