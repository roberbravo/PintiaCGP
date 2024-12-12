package model.weapon

import com.example.juegocartas.R
import com.example.juegocartas.model.AdditionalEfects
import com.example.juegocartas.model.Texts
import model.Config

class Escudo: Weapon() {
    private val img= R.drawable.escudo
    private val text = Texts.ESCUDO

    override  fun getImg(): Int {
        return img
    }
    override fun efect(): AdditionalEfects {
        return AdditionalEfects.STUN
    }

    override fun target(): List<Int> {
        return listOf(1)
    }

    override fun mana(): Int {
        return 1
    }

    override fun damage(target: Int): Int {
        if ( target().contains(target)) return (Config.damage/3).toInt()
        else throw error("Target no válido")
    }

    override fun getText(): String {
        return this.text
    }

}