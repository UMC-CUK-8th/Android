package com.example.jack_week4

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase


@Database(entities = [Song::class, Album::class, Like::class], version = 2)
abstract class SongDatabase : RoomDatabase() {
    abstract fun songDao(): SongDao
    abstract fun albumDao(): AlbumDao

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
                    .fallbackToDestructiveMigration() // ← 이거 꼭 있어야 함
                    .allowMainThreadQueries() // 테스트용
                    .build()
            }
            return instance!!
        }
    }
}
