package com.example.juegocartas.model.fighters

import com.example.juegocartas.R
import com.example.juegocartas.model.Texts
import model.Config

class Vacceo {
    private var vida: Int = Config.life_vacceo
    private var mana: Int = Config.total_mana
    private val img= R.drawable.vacceo
    private val descripcion : String = Texts.VACCEO
    private var drunk:Int =0

    fun getDrunk():Int{
        return drunk
    }
    fun addDrunk(){
        drunk++
    }
    fun minusDrunk(){
        if(drunk>0)
            drunk--
    }
     fun curar(catidad:Int) {
        vida += catidad
    }
    fun quitarMana(cantidad:Int){
            mana-=cantidad
    }
    fun delvolverMana(cantidad:Int){
        mana+=cantidad
    }

    fun resetMana() {
        mana = Config.total_mana
    }

    fun recibirDamage(damage: Int) {
        vida -= damage
    }

    fun fullHp(){
        vida=100
    }

    // Getter para 'vida'
    fun getVida(): Int {
        return vida
    }

    // Getter para 'mana'
    fun getMana(): Int {
        return mana
    }

    // Getter para 'descripcion'
    fun getDescripcion(): String {
        return descripcion
    }

    fun getImg(): Int {
        return img
    }

    fun getVidaMAX(): Int {
        return Config.life_vacceo
    }
}