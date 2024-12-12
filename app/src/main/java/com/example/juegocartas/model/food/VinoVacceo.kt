package model.food

import com.example.juegocartas.R
import com.example.juegocartas.model.AdditionalEfects
import com.example.juegocartas.model.Texts
import model.Config

class VinoVacceo: Food() {
    private val img= R.drawable.vino
    private val text = Texts.VINO

    override  fun getImg(): Int {
        return img
    }
    override fun efect(): AdditionalEfects {
        return AdditionalEfects.DRUNK
    }

    override fun target(): List<Int>? {
        return null
    }

    override fun mana(): Int {
        return 1
    }

    override fun heal(): Int {
        return 2 * Config.heal
    }

    override fun getText(): String {
        return this.text
    }

}
