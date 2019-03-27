package com.kotlin.samples.kotlinapp.room

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.Query
import android.arch.persistence.room.OnConflictStrategy.REPLACE
import com.kotlin.samples.kotlinapp.model.RNoteEntity
import android.arch.persistence.room.Update
import android.arch.persistence.room.Delete
import android.arch.persistence.room.OnConflictStrategy.IGNORE


@Dao
interface RNoteDao {

    @Query("SELECT * from tb_notes")
    fun notes(): List<RNoteEntity>

    @Query("select * from tb_notes where id = :id")
    fun noteById(id: Int): RNoteEntity

    @Insert(onConflict = REPLACE)
    fun addNote(note: RNoteEntity)

    @Insert(onConflict = IGNORE)
    fun insertOrReplaceNotes(vararg notes: RNoteEntity)

    @Update(onConflict = REPLACE)
    fun updateNote(note: RNoteEntity)

    @Delete
    fun deleteNote(note: RNoteEntity)

    @Query("DELETE from tb_notes")
    fun deleteAll()

}