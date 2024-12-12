package com.example.juegocartas

import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import model.Carta

class DiscardDialogFragment(private val cartas: List<Carta>) : DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val view = layoutInflater.inflate(R.layout.dialog_discard, null)

        // Configurar el RecyclerView
        val recyclerView = view.findViewById<RecyclerView>(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        recyclerView.adapter = DiscardAdapter(cartas)

        return MaterialAlertDialogBuilder(requireContext())
            .setView(view)
            .setPositiveButton("Cerrar") { dialog, _ -> dialog.dismiss() }
            .create()
    }
}

