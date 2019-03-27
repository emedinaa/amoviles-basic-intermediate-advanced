package com.kotlin.samples.kotlinapp

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.kotlin.samples.kotlinapp.model.RNoteEntity
import com.kotlin.samples.kotlinapp.room.RNoteRepository
import kotlinx.android.synthetic.main.activity_add_note.*

class AddNoteActivity : AppCompatActivity() {

    private lateinit var noteRepository: RNoteRepository

    private var name:String?=null
    private var desc:String?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_note)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        setupRepository()
        ui()
    }


    private fun ui(){
        btnAddNote.setOnClickListener {
            if(validateForm()){
                addNote()
                finish()
            }
        }
    }

    private fun addNote(){
        val note= RNoteEntity(null,name,desc)
        noteRepository.add(note)
    }

    private fun clearForm(){
        eteName.error=null
        eteDesc.error=null
    }

    private fun validateForm():Boolean{
        clearForm()
        name= eteName.text.toString().trim()
        desc= eteDesc.text.toString().trim()

        if(name.isNullOrEmpty()){
            eteName.error="Campo nombre inválido"
            return false
        }

        if(desc.isNullOrEmpty()){
            eteDesc.error="Campo descripción inválido"
            return false
        }

        return true
    }

    private fun setupRepository(){
        //noteRepository= NoteRepository(NoteDatabase(this))
        noteRepository= (application as RNoteApplication ).getNoteRepository()
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}
