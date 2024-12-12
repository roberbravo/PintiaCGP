package com.example.juegocartas

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import model.Carta

class DiscardAdapter(private val cartas: List<Carta>) : RecyclerView.Adapter<DiscardAdapter.ViewHolder>() {

    // ViewHolder para manejar las vistas de cada ítem
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val cardImage: ImageView = itemView.findViewById(R.id.cardImage)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_card, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val carta = cartas[position] // Obtenemos la carta actual
        holder.cardImage.setImageResource(carta.getImg()) // Asignamos la imagen de la carta
    }

    override fun getItemCount(): Int = cartas.size
}