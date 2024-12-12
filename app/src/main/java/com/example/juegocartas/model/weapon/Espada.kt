package model.weapon

import android.util.Log
import model.Config
import com.example.juegocartas.R
import com.example.juegocartas.model.AdditionalEfects
import com.example.juegocartas.model.Texts

class Espada: Weapon() {
    private val img= R.drawable.espada
    private val text = Texts.ESPADA

    override  fun getImg(): Int {
        return img
    }

    override fun efect(): AdditionalEfects {
        return AdditionalEfects.SIN_EFECTO
    }

    override fun target(): List<Int> {
        return listOf(1)
    }

    override fun mana(): Int {
        return 2
    }

    override fun damage(target: Int): Int {
        if ( target().contains(target)){
            return if (isCritic()){
                Log.d("CRIT","ESPADA")
                2 * Config.damage
            } else {
                Config.damage
            }
        }
        else throw error("Target no válido")
    }

    override fun getText(): String {
        return this.text
    }
}