package com.kotlin.samples.kotlinapp.sample

object Markers {

    private val data:MutableList<Place> = mutableListOf()

    fun addPlace(place:Place){
        data.add(place)
    }

    fun places():List<Place>{
        return data
    }
}