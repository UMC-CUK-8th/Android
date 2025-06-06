package com.flow.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Song::class, Like::class, Album::class], version = 3)
abstract class SongDatabase: RoomDatabase() {
    abstract fun songDao(): SongDao
    abstract fun albumDao(): AlbumDao

    companion object {
        private var instance: SongDatabase? = null

        @Synchronized
        fun getInstance(context: Context): SongDatabase? {
            if (instance == null) {
                synchronized(SongDatabase::class) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        SongDatabase::class.java,
                        "song-database"
                    )
                        .fallbackToDestructiveMigration().allowMainThreadQueries().build()
                }
            }

            return instance
        }
    }
}