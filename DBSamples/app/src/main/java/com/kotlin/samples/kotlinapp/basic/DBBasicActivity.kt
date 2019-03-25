package com.kotlin.samples.kotlinapp.basic

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.kotlin.samples.kotlinapp.R
import com.kotlin.samples.kotlinapp.sample.model.Note
import com.kotlin.samples.kotlinapp.sample.storage.NoteDatabase
import com.kotlin.samples.kotlinapp.sample.storage.NoteRepository
import kotlinx.android.synthetic.main.activity_dbbasic.*
import java.lang.StringBuilder

class DBBasicActivity : AppCompatActivity() {

    private var sb:StringBuilder= StringBuilder("")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dbbasic)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        db()
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    private fun db(){
        val noteRepository= BNoteRepository(BNoteDatabase(this))

        //agregar notas
        noteRepository.addNote(BNote(null,"Nota 1", "Nota 1"))
        noteRepository.addNote(BNote(null,"Nota 2", "Nota 2"))


        //listar notas
        val notes:List<BNote> = noteRepository.notes()
        notes.forEach {
            Log.v("CONSOLE", "note $it")
            sb.appendln("note $it")
        }
        textView.text= sb.toString()


        //eliminar nota
        //noteRepository.deleteNote()
        /*val dNote= Note(4,"Nota 2","Nota 2")
        val row= noteRepository.deleteNote(dNote)
        Log.v("CONSOLE", "row $row")*/

        //editar nota
        /*val uNote= Note(3,"Nota 3","Desc Nota 3")
        val row=noteRepository.updateNote(uNote)
        Log.v("CONSOLE", "row update $row")*/
    }
}
