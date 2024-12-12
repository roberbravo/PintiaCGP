package com.example.juegocartas.model.fighters

abstract class Enemigo {

    abstract protected var currentVida: Int
    protected var vivo: Boolean = true
    protected var aturdimiento: Int = 0
    abstract protected val imagen: Int
    abstract protected val descrip: String

    abstract fun atacar(): Int
    abstract fun getTipo(): Int


    fun isAlive(): Boolean {
        if (currentVida <= 0) vivo = false
        return vivo
    }
    fun revive(){
        currentVida=getMaxVida()
        vivo=true
    }
    fun getStun(): Int {
        return aturdimiento
    }

    fun addStun() {
        aturdimiento++
    }

    fun minusStun() {
        if (aturdimiento > 0) aturdimiento--
    }

    fun recibirDamage(damage: Int) {
        currentVida -= damage
    }

    fun getImg():Int{
        return imagen
    }

    fun getVida(): Int {
        return currentVida
    }

    fun getDescripcion(): String {
        return descrip
    }

    abstract fun getMaxVida(): Int

}