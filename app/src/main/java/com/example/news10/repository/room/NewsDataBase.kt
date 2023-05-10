package com.example.news10.repository.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.news10.models.DaoNewsModel

@Database(entities = [DaoNewsModel::class], version = 1)
@TypeConverters(CustomConverter::class)
abstract class NewsDataBase: RoomDatabase(){

    abstract fun getDao():NewsDao

    companion object {
        @Volatile
        private var INSTANCE:NewsDataBase ?= null
        fun getDatabase(context: Context): NewsDataBase{
            if (INSTANCE == null) {
                synchronized(this){
                    INSTANCE = Room.databaseBuilder(context.applicationContext,
                        NewsDataBase::class.java,
                        "NewsDatabase").build()
                }
            }
            return INSTANCE!!
        }
    }
}