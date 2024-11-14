package com.example.movie.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.movie.R
import com.example.movie.model.Cast

class CastAdapter(private var castList: List<Cast>) : RecyclerView.Adapter<CastAdapter.CastViewHolder>() {

    class CastViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val castImageView: ImageView = view.findViewById(R.id.castImageView)
        val castNameTextView: TextView = view.findViewById(R.id.castNameTextView)
        val castCharacterTextView: TextView = view.findViewById(R.id.castCharacterTextView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CastViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_cast, parent, false)
        return CastViewHolder(view)
    }

    override fun onBindViewHolder(holder: CastViewHolder, position: Int) {
        val cast = castList[position]
        holder.castNameTextView.text = cast.name
        holder.castCharacterTextView.text = cast.character

        Glide.with(holder.itemView.context)
            .load("https://image.tmdb.org/t/p/w500${cast.profilePath}")
            .into(holder.castImageView)
    }

    override fun getItemCount(): Int {
        return castList.size
    }

    fun updateCastList(newCastList: List<Cast>) {
        Log.d("D","SDAA")
        castList = newCastList
    }
}
