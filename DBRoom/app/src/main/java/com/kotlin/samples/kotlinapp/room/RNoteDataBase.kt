package com.kotlin.samples.kotlinapp.room

import android.arch.persistence.room.Database
import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase
import android.content.Context
import com.kotlin.samples.kotlinapp.model.RNoteEntity


@Database(entities = [RNoteEntity::class], version = 1)
abstract class RNoteDataBase : RoomDatabase() {

    abstract fun noteDao(): RNoteDao

    companion object {
        private var INSTANCE: RNoteDataBase? = null
        private const val DBNAME="BDRoom.db"

        fun getInstance(context: Context): RNoteDataBase? {
            if (INSTANCE == null) {
                synchronized(RNoteDataBase::class) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            RNoteDataBase::class.java, DBNAME)
                            .build()
                }
            }
            return INSTANCE
        }

        fun destroyInstance() {
            INSTANCE = null
        }
    }
}