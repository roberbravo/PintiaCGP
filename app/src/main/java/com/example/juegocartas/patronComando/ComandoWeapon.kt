package com.example.juegocartas.patronComando

import android.graphics.drawable.AnimationDrawable
import android.view.View
import android.widget.ImageView
import com.example.juegocartas.GameActivity
import model.weapon.Weapon
import com.example.juegocartas.R
import com.example.juegocartas.model.AdditionalEfects
import kotlin.random.Random


class ComandoWeapon(private val carta: Weapon,private val juego: GameActivity):ComandoHabilidades {
    override fun execute() {

        var enemigoPos=carta.target()[0]-1

        enemigoPos = encontrarEnemigoVivo(juego, enemigoPos) ?: return
        var realDamage:Int=golpear(enemigoPos)
        juego.vidasEnemigo[enemigoPos].progress = (juego.enemigos[enemigoPos].getVida()*100)/juego.enemigos[enemigoPos].getMaxVida()
        //comprobamos si el golpe le mata
        if(!juego.enemigos[enemigoPos].isAlive()) {
                enterrar(enemigoPos)
        }
        else if(carta.efect()==AdditionalEfects.STUN && realDamage>0) {
            juego.enemigos[enemigoPos].addStun()

            juego.efectoEnemigos[enemigoPos].setImageDrawable(null)

            juego.efectoEnemigos[enemigoPos].setImageResource(R.drawable.aturdimiento0)
            juego.efectoEnemigos[enemigoPos].setImageResource(R.drawable.animation_stun)
            val idleAnimation = juego.efectoEnemigos[enemigoPos].drawable as? AnimationDrawable
            idleAnimation?.start()
        }
    }

    private fun gamblingAlcoholico(damage:Int):Int{
        val probabilidad = Random.nextInt(3)
        if(probabilidad!=0){
            return  damage*2
        }
        else return 0
    }
    private  fun enterrar(enemigoPos:Int){
        //Ocultar barra de vida
        juego.vidasEnemigo[enemigoPos].visibility = View.INVISIBLE
        juego.efectoEnemigos[enemigoPos].setImageDrawable(null)

        // Enterrar
        val enemigoView =
            juego.enemigosLayout.getChildAt(enemigoPos) // Obtener la vista del enemigo
        if (enemigoView is ImageView)
            enemigoView.setImageResource(R.drawable.tumba)

    }
    private fun golpear(enemigoPos: Int):Int{
        var realDamage:Int =carta.damage(carta.target()[0])
        if(juego.vacceo.getDrunk()<=0) {
            juego.enemigos[enemigoPos].recibirDamage(realDamage)

        }
        else{
            realDamage=gamblingAlcoholico(carta.damage(carta.target()[0]))
            juego.enemigos[enemigoPos].recibirDamage(realDamage)

        }
        return  realDamage

    }
    private fun encontrarEnemigoVivo(juego: GameActivity, start: Int): Int? {
        val totalEnemigos = juego.enemigos.size
        var distancia = 0

        // Buscamos enemigos vivos
        while (distancia < totalEnemigos) {
            val izquierda = start - distancia
            val derecha = start + distancia

            if (izquierda >= 0 && juego.enemigos[izquierda].isAlive()) {
                return izquierda
            }
            if (derecha < totalEnemigos && juego.enemigos[derecha].isAlive()) {
                return derecha
            }
            distancia++
        }

        return null
    }
}