package model.weapon

import android.util.Log
import com.example.juegocartas.R
import com.example.juegocartas.model.AdditionalEfects
import com.example.juegocartas.model.Texts
import model.Config

class Arco: Weapon() {
    private val img= R.drawable.arco
    private val text = Texts.ARCO

    override  fun getImg(): Int {
        return img
    }
    override fun efect(): AdditionalEfects {
        return AdditionalEfects.SIN_EFECTO
    }

    override fun target(): List<Int> {
        return listOf(3)
    }

    override fun mana(): Int {
        return 2
    }

    override fun damage(target: Int): Int {
        if ( target().contains(target)){
            return if (isCritic()){
                Log.d("CRIT","ARCO")
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