package com.kotlin.samples.kotlinapp.basic

class BNote(val id:Int?, val name:String?,val description:String?) {

    override fun toString(): String {
        return "BNote(id=$id, name=$name, description=$description)"
    }
}