package com.udemy.sevenminutesworkout.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.udemy.sevenminutesworkout.data.db.dao.HistoryDAO
import com.udemy.sevenminutesworkout.data.db.entities.HistoryEntity

@Database(entities = [HistoryEntity::class], version = 1)
abstract class HistoryDatabase : RoomDatabase() {

    companion object {
        @Volatile
        private var DB_INSTANCE: HistoryDatabase? = null

        fun getInstance(context: Context): HistoryDatabase {
            synchronized(this) {
                var instance = DB_INSTANCE
                if (instance == null) {
                    instance = Room.databaseBuilder(context.applicationContext, HistoryDatabase::class.java, "history_database")
                        .fallbackToDestructiveMigration().build()

                    DB_INSTANCE = instance
                }
                return instance
            }
        }
    }

    abstract fun getHistoryDao(): HistoryDAO
}