package model.weapon

import android.util.Log
import com.example.juegocartas.R
import com.example.juegocartas.model.AdditionalEfects
import com.example.juegocartas.model.Texts
import model.Config

class Daga: Weapon() {
    private val img= R.drawable.punnal
    private val text = Texts.DAGA

    override fun getImg(): Int {
        return img
    }
    override fun efect(): AdditionalEfects {
        return AdditionalEfects.SIN_EFECTO
    }

    override fun target(): List<Int> {
        return listOf(1)
    }

    override fun mana(): Int {
        return 1
    }

    override fun damage(target: Int): Int {
        if ( target().contains(target)){
            return if (isCritic()){
                Log.d("CRIT","DAGA")
                (2 * (0.7 * Config.damage)).toInt()
            } else {
                (0.7 * Config.damage).toInt()
            }
        }
        else throw error("Target no válido")
    }

    override fun getText(): String {
        return this.text
    }

    init {
        critic = Config.critic_prob * 3
    }
}