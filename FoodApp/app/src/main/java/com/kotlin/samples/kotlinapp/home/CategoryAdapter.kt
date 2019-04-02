package com.kotlin.samples.kotlinapp.adapters

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.kotlin.samples.kotlinapp.R
import com.kotlin.samples.kotlinapp.model.Category


/**
 * @author : Eduardo Medina
 * @since : 11/17/18
 * @see : https://developer.android.com/index.html
 */
class CategoryAdapter(val context:Context, val pokemonList:List<Category>):RecyclerView.Adapter<CategoryAdapter.Companion.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view= LayoutInflater.from(parent?.context)
                .inflate(R.layout.layout_category_row,parent,false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return pokemonList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val name= pokemonList[position].name
        val image = pokemonList[position].photo

        holder?.tviName?.text=name
        //holder?.iviPhoto.setImageBitmap(getBitmapFromAssets(image))
        holder?.iviPhoto.setOnClickListener {
            //
        }
    }


    companion object {
        class ViewHolder(v:View ):RecyclerView.ViewHolder(v){
            val tviName:TextView=v.findViewById(R.id.textViewTitle)
            val tviDescription:TextView=v.findViewById(R.id.textViewDescription)
            val iviPhoto:ImageView= v.findViewById(R.id.imageViewCategory)
        }
    }
}