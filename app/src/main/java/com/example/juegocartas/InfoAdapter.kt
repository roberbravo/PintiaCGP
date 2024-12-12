package com.example.juegocartas

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class InfoAdapter(private val img: Int, private val txt: String) : RecyclerView.Adapter<InfoAdapter.ViewHolder>() {

    // ViewHolder para manejar las vistas de cada ítem
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val cardImage: ImageView = itemView.findViewById(R.id.cardImage)
        val cardText: TextView = itemView.findViewById(R.id.cardText)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_info_card,parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.cardImage.setImageResource(img)
        holder.cardText.text = txt
    }

    override fun getItemCount(): Int = 1
}