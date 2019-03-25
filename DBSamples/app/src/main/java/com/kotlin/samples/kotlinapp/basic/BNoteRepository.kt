package com.kotlin.samples.kotlinapp.basic

import android.content.ContentValues
import com.kotlin.samples.kotlinapp.sample.model.Note
import com.kotlin.samples.kotlinapp.sample.storage.NoteDatabase
import com.kotlin.samples.kotlinapp.sample.storage.NoteTable
import com.kotlin.samples.kotlinapp.sample.storage.NoteTable.KEY_NAME

class BNoteRepository(val noteDatabase: BNoteDatabase) {

    fun notes():List<BNote>{
        val sqliteDatabase= noteDatabase.readableDatabase //modo lectura

        val notes:MutableList<BNote> = mutableListOf()
        val sql= "SELECT  * FROM ${BNoteDatabase.BNOTE_TABLE_NAME}"
        val cursor= sqliteDatabase.rawQuery(sql,null)
        if(cursor.moveToFirst()){
            do{
                val note= BNote(cursor.getString(0).toInt(),
                        cursor.getString(1),
                        cursor.getString(2))
                notes.add(note)
            }while(cursor.moveToNext())
        }
        sqliteDatabase.close()
        return notes.toList()
    }

    fun addNote(note:BNote){
        val sqliteDatabase= noteDatabase.writableDatabase //modo escritura

        val contentValues= ContentValues()
        contentValues.put(BNoteDatabase.BNOTE_KEY_NAME,note.name)
        contentValues.put(BNoteDatabase.BNOTE_KEY_DESC,note.description)

        sqliteDatabase.insert(BNoteDatabase.BNOTE_TABLE_NAME,null,contentValues)
        sqliteDatabase.close()
    }
}