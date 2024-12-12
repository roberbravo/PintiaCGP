package com.example.juegocartas.model.fighters

import com.example.juegocartas.R
import com.example.juegocartas.model.Texts
import model.Config

class Necromancer: Enemigo() {
    override val imagen: Int = R.drawable.necromancer_00
    override val descrip: String = Texts.NECROMANCER
    override fun getTipo(): Int {
        return 2
    }
    override fun atacar(): Int {
        return Config.damage_necromancer
    }

    override fun getMaxVida(): Int {
        return Config.life_necromancer
    }

    override var currentVida: Int = Config.life_necromancer
}