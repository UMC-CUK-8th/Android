package com.example.week7.data.local.user

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.week7.data.entities.User

@Dao
interface UserDao {
    @Insert
    fun insert(user : User)

    @Query("select * from UserTable")
    fun getUsers() : List<User>

    @Query("select * from UserTable where email =:email and password = :password")
    fun getUser(email : String, password : String) : User?
}