package com.example.juegocartas.patronComando

import com.example.juegocartas.GameActivity
import model.Carta

//el receptor es el propio cliente alias Game Activity
class ComandoArtifact(private val carta: Carta, private val juego:GameActivity):ComandoHabilidades {
    override fun execute() {
        carta.efect()
        for(i in 0..1){
         juego.robarCarta(juego.playerHandLayout,juego.barajaController)
        }

    }
}