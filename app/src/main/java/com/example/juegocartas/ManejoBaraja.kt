package com.example.juegocartas

import PrepBelica
import model.Carta
import model.Config
import model.food.Fruta
import model.food.LechazoVacceo
import model.food.VinoVacceo
import model.weapon.Arco
import model.weapon.Daga
import model.weapon.Escudo
import model.weapon.Espada
import model.weapon.Honda
import model.weapon.Lanza

class ManejoBaraja {

    // Lista de cartas (nombres de archivos en res/drawable)
    private val cardDrawables = arrayListOf(
        Fruta(),
        LechazoVacceo(),
        VinoVacceo(),
        PrepBelica(),
        Arco(),
        Daga(),
        Escudo(),
        Espada(),
        Honda(),
        Lanza()
    )
    private var descartes = arrayListOf<Carta>()
    private var mano = arrayListOf<Carta>()
    private var pila = ArrayList(cardDrawables)


    fun cartaRandom():Carta?{
        if(pila.isNotEmpty()){
            val cartaRandom=pila.random()
            pilaQuit(cartaRandom)
            mano.add(cartaRandom)
            return cartaRandom
        }
        else return null
    }

    fun pilaRestock(){
        for (carta in descartes)
            pila.add(carta)
        descartes.clear()
    }

    private fun pilaQuit(carta: Carta) {
        pila.remove(carta)
    }

    fun manoQuit(carta: Carta) {
        mano.remove(carta)
        descartesAdd(carta)
    }

    fun quedanCartas():Boolean{
        return pila.isNotEmpty()
    }
    
    private  fun descartesAdd(carta: Carta){
        descartes.add(carta)
    }

    fun getDescartes():List<Carta>{
        return descartes
    }

    fun getMano():List<Carta>{
        return mano
    }

    fun isMaxMano():Boolean{
        return mano.size >= Config.tam_max_mano
    }
}
