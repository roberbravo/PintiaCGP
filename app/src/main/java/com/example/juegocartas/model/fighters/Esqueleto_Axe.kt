package com.example.juegocartas.model.fighters

import com.example.juegocartas.R
import com.example.juegocartas.model.Texts
import model.Config

class Esqueleto_Axe: Enemigo() {
    override val imagen: Int = R.drawable.skeletons_axe_00
    override val descrip: String = Texts.ESQUELETO
    override fun getTipo(): Int {
        return 1
    }
    override fun atacar(): Int {
        return Config.damage_skeleton_axe
    }

    override fun getMaxVida(): Int {
        return Config.life_skeleton_axe
    }

    override var currentVida: Int = Config.life_skeleton_axe
}