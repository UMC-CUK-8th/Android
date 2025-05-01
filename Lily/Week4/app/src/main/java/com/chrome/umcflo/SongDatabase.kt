package com.chrome.umcflo

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Song::class, Album::class, User::class, Like::class], version = 1)
abstract class SongDatabase : RoomDatabase() {

    abstract fun albumDao(): AlbumDao
    abstract fun songDao(): SongDao
    abstract fun userDao(): UserDao

    companion object {
        @Volatile
        private var instance: SongDatabase? = null

        /**
         * SongDatabase의 싱글톤 인스턴스를 반환
         * 메인 스레드에서 쿼리 허용 (편의 목적)
         */
        fun getInstance(context: Context): SongDatabase {
            return instance ?: synchronized(this) {
                instance ?: Room.databaseBuilder(
                    context.applicationContext,
                    SongDatabase::class.java,
                    "song-database" // DB 이름 (충돌 방지를 위해 명시)
                )
                    .allowMainThreadQueries() // 메인 스레드에서 DB 접근 허용 (비추천이나 단순 프로젝트에서는 허용)
                    .build()
                    .also { instance = it }
            }
        }
    }
}
