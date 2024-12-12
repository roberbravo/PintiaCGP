package com.example.juegocartas.patronComando

import android.graphics.drawable.AnimationDrawable
import com.example.juegocartas.GameActivity
import com.example.juegocartas.R
import com.example.juegocartas.model.AdditionalEfects
import model.food.Food


class ComandoFood(private val carta: Food,private val juego: GameActivity):ComandoHabilidades {
    override fun execute() {
        juego.vacceo.curar(carta.heal())
        if(juego.vacceo.getVida()>=100){
            juego.vacceo.fullHp()
        }
        if(carta.efect()==AdditionalEfects.DRUNK){
            juego.vacceo.addDrunk()
            juego.estadoVacceo.setImageDrawable(null)
            juego.estadoVacceo.setImageResource(R.drawable.borracho_0)
            juego.estadoVacceo.setImageResource(R.drawable.animarion_borracho)

            val idleAnimation = juego.estadoVacceo.drawable as? AnimationDrawable
            idleAnimation?.start()
        }
        juego.vidaVacceo.progress=juego.vacceo.getVida()

    }
}