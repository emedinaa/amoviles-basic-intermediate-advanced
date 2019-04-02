package com.kotlin.samples.kotlinapp.model

import java.io.Serializable

data class Category(val id:Int, val name:String?, val desc:String?,
                    val photo:Int?):Serializable