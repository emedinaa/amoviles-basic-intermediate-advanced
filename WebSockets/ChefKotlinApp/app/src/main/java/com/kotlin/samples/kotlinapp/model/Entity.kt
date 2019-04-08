package com.kotlin.samples.kotlinapp.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class User(@SerializedName("_id") val id:String?,   @SerializedName("nombres") val name:String?,
                @SerializedName("apellido_paterno") val lastName:String?,
                @SerializedName("apellido_materno") val mLastName:String?,
                val email:String?, val socketId:String?,
                @SerializedName("imagen") val image:String?, val token:String?):Serializable{

    fun fullName():String{
        return this.name+" "+this.lastName+" "+this.mLastName
    }
}

open class OrderViewType(){

    var type:Int?=-1
    open fun isHeader(): Boolean {
        return false
    }

    open fun isItem(): Boolean {
        return false
    }

    open fun isFooter(): Boolean {
        return false
    }
}

class OrderViewHeader:OrderViewType(){
    override fun isHeader(): Boolean {
        return true
    }
}

data class Plate(@SerializedName("_id") val id:String?,
                 @SerializedName("cantidad") val amount:Int?,
                 @SerializedName("nombre") val name:String?,
                 @SerializedName("plato_id") val dishId:String?,
                 @SerializedName("precio") val price:Double?):OrderViewType() ,Serializable{
    override fun isItem(): Boolean {
        return true
    }

    fun getTotal():Double{
        return (price?:0.0)*(amount?.toDouble()?:0.0)
    }
}

data class Order(@SerializedName("_id") val id:String?,
                 @SerializedName("cliente_id") val clientId:User?,
                 @SerializedName("detalle") val plates:List<Plate>?,
                 @SerializedName("fechaRegistro") val regDate:String?,val total:Double?)