package com.example.filmatory.utils

import android.content.Context
import android.widget.ImageView
import android.widget.TextView
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.example.filmatory.R
import com.example.filmatory.scenes.activities.MovieScene
import com.example.filmatory.scenes.activities.PersonScene
import com.example.filmatory.scenes.activities.TvScene

class PersonRecyclerViewAdapter(private val arrayList: MutableList<PersonItem>, private val context: Context) : RecyclerView.Adapter<PersonRecyclerViewAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(context).inflate(R.layout.slider_item_container, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int){
        holder.itemRealName.text = arrayList[position].realName
        holder.itemCharName.text = arrayList[position].charName
        Glide.with(this.context)
            .load(arrayList[position].image)
            .error(R.drawable.placeholder_image)
            .fallback(R.drawable.placeholder_image)
            .placeholder(R.drawable.placeholder_image)
            .into(holder.itemImage)
        holder.itemId = arrayList[position].id

        holder.itemView.setOnClickListener {
            val model = arrayList[position]
            val personId: Int = model.id
            val intent = Intent(context, PersonScene::class.java)
            intent.putExtra("personId", personId)
            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return arrayList.size
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val itemImage: ImageView = view.findViewById(R.id.slider_image)
        val itemRealName: TextView = view.findViewById(R.id.slider_title)
        val itemCharName: TextView = view.findViewById(R.id.slider_date)
        var itemId: Int? = null
    }

}