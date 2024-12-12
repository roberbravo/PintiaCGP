package model

import com.example.juegocartas.model.AdditionalEfects

abstract class Carta {
    abstract fun getImg(): Int
    abstract fun getText(): String
    abstract fun efect() : AdditionalEfects
    abstract fun target() : List<Int>?
    abstract fun mana(): Int
    abstract val tipo: Int

    override fun equals(other: Any?): Boolean {
        if (other == null || this::class != other::class) return false
        else return true
    }
}