package com.example.juegocartas.model.fighters

import com.example.juegocartas.R
import com.example.juegocartas.model.Texts
import model.Config

class Romano: Enemigo() {
    override val imagen: Int = R.drawable.romano
    override val descrip: String = Texts.ROMANO
    override fun getTipo(): Int {
        return 0
    }
    override fun atacar(): Int {
        return Config.damage_romano
    }

    override fun getMaxVida(): Int {
        return Config.life_romano
    }

    override var currentVida: Int = Config.life_romano

}