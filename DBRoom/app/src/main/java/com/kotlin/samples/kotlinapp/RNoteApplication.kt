package com.kotlin.samples.kotlinapp

import android.app.Application
import com.kotlin.samples.kotlinapp.room.RNoteRepository

class RNoteApplication :Application() {

    private lateinit var repository: RNoteRepository

    override fun onCreate() {
        super.onCreate()

        repository=RNoteRepository(this)
    }

    fun getNoteRepository():RNoteRepository{
        return repository
    }
}