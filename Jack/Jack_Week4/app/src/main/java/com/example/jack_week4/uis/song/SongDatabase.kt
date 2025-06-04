package com.example.jack_week4.uis.song

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.jack_week4.data.entities.UserDao
import com.example.jack_week4.data.entities.Album
import com.example.jack_week4.data.entities.Like
import com.example.jack_week4.data.entities.Song
import com.example.jack_week4.data.entities.User
import com.example.jack_week4.uis.main.album.AlbumDao


@Database(entities = [Song::class, Album::class, Like::class, User::class], version = 3)
abstract class SongDatabase : RoomDatabase() {
    abstract fun songDao(): SongDao
    abstract fun albumDao(): AlbumDao
    abstract fun userDao() : UserDao

    companion object {
        private var instance: SongDatabase? = null

        @Synchronized
        fun getInstance(context: Context): SongDatabase {
            if (instance == null) {
                instance = Room.databaseBuilder(
                    context.applicationContext,
                    SongDatabase::class.java,
                    "song-database"
                )
                    .fallbackToDestructiveMigration()
                    .allowMainThreadQueries()
                    .build()
            }
            return instance!!
        }
    }
}
