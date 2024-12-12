package model.food

import com.example.juegocartas.R
import com.example.juegocartas.model.AdditionalEfects
import com.example.juegocartas.model.Texts
import model.Config

class Fruta: Food() {
    private val img= R.drawable.fruta
    private val text = Texts.FRUTA

    override  fun getImg(): Int {
        return img
    }
    override fun efect(): AdditionalEfects {
        return AdditionalEfects.STEAL_CARD
    }

    override fun target(): List<Int>? {
        return null
    }

    override fun mana(): Int {
        return 1
    }

    override fun heal(): Int {
        return (0.5 * Config.heal).toInt()
    }

    override fun getText(): String {
        return this.text
    }
}