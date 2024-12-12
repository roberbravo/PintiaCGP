package model.weapon

import com.example.juegocartas.model.AdditionalEfects
import model.Carta
import model.Config
import kotlin.random.Random

abstract class Weapon : Carta() {
    abstract override fun getImg(): Int
    abstract override fun efect() : AdditionalEfects
    abstract override fun target() : List<Int>
    abstract override fun mana(): Int
    abstract fun damage(target : Int): Int
    protected var critic: Double = Config.critic_prob
    override val tipo=2

    protected fun isCritic(): Boolean {
        return Random.nextDouble(0.0, 1.0) < critic
    }
}