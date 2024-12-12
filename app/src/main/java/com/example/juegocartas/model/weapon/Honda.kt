package model.weapon

import com.example.juegocartas.R
import com.example.juegocartas.model.AdditionalEfects
import com.example.juegocartas.model.Texts
import model.Config

class Honda: Weapon() {
    private val img= R.drawable.honda
    private val text = Texts.HONDA

    override  fun getImg(): Int {
        return img
    }
    override fun efect(): AdditionalEfects {
        return AdditionalEfects.STUN
    }

    override fun target(): List<Int> {
        return listOf(3)
    }

    override fun mana(): Int {
        return 1
    }

    override fun damage(target: Int): Int {
        if ( target().contains(target)) return (Config.damage/2).toInt()
        else throw error("Target no válido")
    }

    override fun getText(): String {
        return this.text
    }
}