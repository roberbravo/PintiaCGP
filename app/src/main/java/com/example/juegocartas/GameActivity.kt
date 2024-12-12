package com.example.juegocartas

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.graphics.drawable.AnimationDrawable
import android.os.Bundle
import android.view.MotionEvent
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.View
import android.widget.ImageButton
import android.widget.ProgressBar
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.example.juegocartas.model.fighters.Enemigo
import com.example.juegocartas.model.fighters.Necromancer
import com.example.juegocartas.model.fighters.Esqueleto_Axe
import com.example.juegocartas.model.fighters.Esqueleto_Sword
import com.example.juegocartas.model.fighters.Romano
import com.example.juegocartas.model.fighters.Vacceo
import com.example.juegocartas.patronComando.ComandoArtifact
import com.example.juegocartas.patronComando.ComandoFood
import com.example.juegocartas.patronComando.ComandoWeapon
import com.example.juegocartas.patronComando.ControladorDeComandos
import model.Carta
import model.food.Food
import model.weapon.Weapon

@Suppress("DEPRECATION")
class GameActivity : AppCompatActivity() {

    private var nivel: Int = 1 // Valor por defecto
    private var invoca=0;

    private var selectedCards =
        mutableMapOf<Carta, ImageView>()  // Mapa de cartas seleccionadas (Card -> ImageView)

    var enemigos = mutableListOf<Enemigo>()  // Lista de romanos enemigos
    var vacceo = Vacceo()

    // Referencia al contenedor de las cartas en la mano del jugador
    val playerHandLayout by lazy { findViewById<LinearLayout>(R.id.playerHand) }

    //inicializacion de la baraja
    val vidaVacceo by lazy { findViewById<ProgressBar>(R.id.vidaVacceo) }
    val vidasEnemigo by lazy {
        listOf<ProgressBar>(
            findViewById<ProgressBar>(R.id.vidaEnemigo1),
            findViewById<ProgressBar>(R.id.vidaEnemigo2),
            findViewById<ProgressBar>(R.id.vidaEnemigo3)
        )
    }
    val efectoEnemigos by lazy {
        listOf<ImageView>(
            findViewById<ImageView>(R.id.estado1),
            findViewById<ImageView>(R.id.estado2),
            findViewById<ImageView>(R.id.estado3)
        )
    }

    val enemigosLayout by lazy { findViewById<LinearLayout>(R.id.Enemigos) }
    val mana by lazy { findViewById<ImageView>(R.id.manaText) }
    val estadoVacceo by lazy { findViewById<ImageView>(R.id.estadoDeVaceo) }
    val barajaController = ManejoBaraja()
    @SuppressLint("ClickableViewAccessibility")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        nivel = intent.getIntExtra(EXTRA_NIVEL, 1)
        iniciarConfiguracion()
        ponerPersonajes()
        inicializarBotones()
        inicializarTextoBotones()
    }

    private fun iniciarConfiguracion() {
        enableEdgeToEdge()
        hideSystemUI()

        stopService(Intent(this, MusicService::class.java))
        startService(Intent(this, MusicService::class.java).apply {
            putExtra("MUSIC_RES_ID", R.raw.background_game)
        })

        setContentView(R.layout.activity_game)
    }

    private fun ponerPersonajes() {
        val imgVacceo = findViewById<ImageView>(R.id.vacceo)
        imgVacceo.setImageResource(R.drawable.vacceo)
        imgVacceo.setImageResource(R.drawable.animation_vacceo)
        imgVacceo.layoutParams = LinearLayout.LayoutParams(300, 400).apply { marginEnd = 16 }

        gestionMana(5)

        val idleAnimation = imgVacceo.drawable as? AnimationDrawable
        idleAnimation?.start()
        seleccionVacceo(imgVacceo)

        vidaVacceo.progress = vacceo.getVida()

        Log.d("Level", nivel.toString())
        when(nivel){
            1 -> generarRomanos(enemigosLayout)
            2-> generarNecromancer(enemigosLayout)
            else -> generarNecromancer(enemigosLayout)
        }

        for (i in 1..3) robarCarta(playerHandLayout, barajaController)
    }

    private fun inicializarBotones() {
        val playCardButton = findViewById<ImageButton>(R.id.btnDrawCard)
        val endTurnButton = findViewById<ImageButton>(R.id.btnEndTurn)
        val discardsButton = findViewById<ImageButton>(R.id.discards)

        updatePlayButtonState();
        playCardButton.setOnClickListener{ jugarCartasSeleccionadas() }
        endTurnButton.setOnClickListener { finalizarTurno() }
        discardsButton.setOnClickListener { mostrarDescartes() }
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun inicializarTextoBotones() {
        val DrawCardText = findViewById<TextView>(R.id.DrawCardText)
        val EndTurnText = findViewById<TextView>(R.id.EndTurnText)
        val discardsText = findViewById<TextView>(R.id.discardsText)

        // Subir y bajar texto del boton jugar cartas al pulsar su boton
        findViewById<View>(R.id.btnDrawCard).setOnTouchListener { _, event ->
            when (event.action) {
                MotionEvent.ACTION_DOWN -> {
                    // Mover el texto hacia abajo cuando se presiona el botón
                    DrawCardText.translationY = 12f
                }
                MotionEvent.ACTION_UP, MotionEvent.ACTION_CANCEL -> {
                    // Restaurar la posición del texto cuando se suelta el botón
                    DrawCardText.translationY = 0f
                }
            }
            false
        }

        // Subir y bajar texto del boton fin turno al pulsar su boton
        findViewById<View>(R.id.btnEndTurn).setOnTouchListener { _, event ->
            when (event.action) {
                MotionEvent.ACTION_DOWN -> {
                    // Mover el texto hacia abajo cuando se presiona el botón
                    EndTurnText.translationY = 12f
                }
                MotionEvent.ACTION_UP, MotionEvent.ACTION_CANCEL -> {
                    // Restaurar la posición del texto cuando se suelta el botón
                    EndTurnText.translationY = 0f
                }
            }
            false
        }

        // Subir y bajar texto del boton descartes al pulsar su boton
        findViewById<View>(R.id.discards).setOnTouchListener { _, event ->
            when (event.action) {
                MotionEvent.ACTION_DOWN -> {
                    // Mover el texto hacia abajo cuando se presiona el botón
                    discardsText.translationY = 14f
                }
                MotionEvent.ACTION_UP, MotionEvent.ACTION_CANCEL -> {
                    // Restaurar la posición del texto cuando se suelta el botón
                    discardsText.translationY = 0f
                }
            }
            false
        }
    }

    fun robarCarta(playerHandLayout: LinearLayout, manejoBaraja: ManejoBaraja) {
        if (!barajaController.isMaxMano()) {
            val randomCard = manejoBaraja.cartaRandom()
            if (randomCard != null) {
                val cardView = ImageView(this)
                seleccionCarta(randomCard, cardView)
                cardView.setImageResource(randomCard.getImg())
                cardView.layoutParams = LinearLayout.LayoutParams(200, 300).apply { marginEnd = 16 }
                playerHandLayout.addView(cardView)
            } else {
                barajaController.pilaRestock()
                robarCarta(playerHandLayout, manejoBaraja)
            }
        }
    }

    private fun finalizarTurno() {
        turnoEnemigo()
        if (!barajaController.quedanCartas()) barajaController.pilaRestock()
        for (i in 1..3) robarCarta(playerHandLayout, barajaController)

        vacceo.minusDrunk()
        if (vacceo.getDrunk() <= 0) estadoVacceo.setImageDrawable(null)

        vacceo.resetMana()
        gestionMana(vacceo.getMana())
        limpiarCartasSeleccionadas()
        updatePlayButtonState()
    }

    private fun limpiarCartasSeleccionadas() {
        for ((_, cardView) in selectedCards) cardView.alpha = 1.0f
        selectedCards.clear()
    }

    private fun jugarCartasSeleccionadas() {
        for ((card, cardView) in selectedCards) {
            barajaController.manoQuit(card)
            jugarCartas(card)
            playerHandLayout.removeView(cardView)
        }
        selectedCards.clear()
        updateHPBars()
        updatePlayButtonState()
    }

    private fun mostrarDescartes() {
        val discardDialog = DiscardDialogFragment(barajaController.getDescartes())
        discardDialog.show(supportFragmentManager, "DiscardDialog")
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun seleccionRomano(romano:ImageView,index:Int) {
        val handler = Handler(Looper.getMainLooper())
        var isLongPress = false
        romano.setOnTouchListener { _, event ->
            when (event.action) {
                MotionEvent.ACTION_DOWN -> {
                    isLongPress = false

                    // Iniciar temporizador para detectar si se mantiene pulsado
                    handler.postDelayed({
                        isLongPress = true

                        if (index == 0){
                            if (enemigos[index].getTipo() == 0) {
                                val infoDialog = InfoDialogFragment(enemigos[index].getImg(),"El soldado Diegus Flavius ${enemigos[index].getDescripcion()}")
                                infoDialog.show(supportFragmentManager, "DiscardDialog")
                            }else{
                                val infoDialog = InfoDialogFragment(enemigos[index].getImg(),enemigos[index].getDescripcion())
                                infoDialog.show(supportFragmentManager, "DiscardDialog")
                            }

                        }else if (index == 1) {
                            if (enemigos[index].getTipo() == 0) {
                                val infoDialog = InfoDialogFragment(
                                    enemigos[index].getImg(),
                                    "El soldado Sergius Aurelius ${enemigos[index].getDescripcion()}"
                                )
                                infoDialog.show(supportFragmentManager, "DiscardDialog")
                            } else {
                                val infoDialog = InfoDialogFragment(
                                    enemigos[index].getImg(),
                                    enemigos[index].getDescripcion()
                                )
                                infoDialog.show(supportFragmentManager, "DiscardDialog")
                            }
                        }else {
                            if (enemigos[index].getTipo() == 0) {
                                val infoDialog = InfoDialogFragment(
                                    enemigos[index].getImg(),
                                    "El soldado Robertus Magnus Decimo Meridio ${enemigos[index].getDescripcion()}"
                                )
                                infoDialog.show(supportFragmentManager, "DiscardDialog")
                            } else {
                                val infoDialog = InfoDialogFragment(
                                    enemigos[index].getImg(),
                                    enemigos[index].getDescripcion()
                                )
                                infoDialog.show(supportFragmentManager, "DiscardDialog")
                            }
                        }
                    }, 500) // Medio segundo
                }
            }
            true
        }
    }

    //selecciona vacceo para visualizar
    @SuppressLint("ClickableViewAccessibility")
    private fun seleccionVacceo(vacceoImageView: ImageView) {
        val handler = Handler(Looper.getMainLooper())
        var isLongPress = false
        vacceoImageView.setOnTouchListener { _, event ->
            when (event.action) {
                MotionEvent.ACTION_DOWN -> {
                    isLongPress = false

                    // Iniciar temporizador para detectar si se mantiene pulsado
                    handler.postDelayed({
                        isLongPress = true

                        val infoDialog = InfoDialogFragment(vacceo.getImg(),vacceo.getDescripcion())
                        infoDialog.show(supportFragmentManager, "DiscardDialog")

                    }, 500) // Medio segundo
                }
            }
            true
        }
    }

    //selecciona carta o permite visualizarlas en tamaño mas grande
    @SuppressLint("ClickableViewAccessibility")
    private fun seleccionCarta(card: Carta, cardView: ImageView) {
        val handler = Handler(Looper.getMainLooper())
        var isLongPress = false

        //accion al clickar
        cardView.setOnTouchListener { _, event ->
            when (event.action) {
                MotionEvent.ACTION_DOWN -> {
                    isLongPress = false

                    // Iniciar temporizador para detectar si se mantiene pulsado
                    handler.postDelayed({
                        isLongPress = true

                        val infoDialog = InfoDialogFragment(card.getImg(),card.getText())
                        infoDialog.show(supportFragmentManager, "DiscardDialog")

                    }, 500) // medio segundo
                }

                MotionEvent.ACTION_UP -> {
                    handler.removeCallbacksAndMessages(null) // Cancelar temporizador
                    if (!isLongPress) {
                        //opcines de mana
                        //seleccionar cartas
                        if (selectedCards.containsKey(card)) {
                            selectedCards.remove(card) // Desmarcar la carta
                            cardView.alpha = 1.0f // Restablecer opacidad
                            vacceo.delvolverMana(card.mana())
                            gestionMana(vacceo.getMana())

                        } else if (vacceo.getMana() >= card.mana()) {
                            selectedCards[card] = cardView // Marcar la carta
                            cardView.alpha = 0.5f // Reducir opacidad para indicar selección
                            vacceo.quitarMana(card.mana())
                            gestionMana(vacceo.getMana())
                        }
                        updatePlayButtonState()
                    }
                    //fin del click largo
                    else {
                        cardView.requestLayout() // Actualizar la vista
                    }
                }
                MotionEvent.ACTION_CANCEL -> {
                    handler.removeCallbacksAndMessages(null) // Cancelar temporizador
                }
            }
            true
        }
    }

    //funcion de ataque de romanos
    private fun turnoEnemigo() {
        var cont=0;
        var vivos=0;
        Log.d("PRE","Vacceo: "+vacceo.getVida()+"/ 120   Progress: "+vidaVacceo.progress)
        for (enemigo in enemigos) {
            if(enemigo.isAlive()){
                vivos++
                Log.d("HP", "ENEMIGOS VIVOS: "+ vivos.toString())
                if(enemigo.getStun()<=0){
                    vacceo.recibirDamage(enemigo.atacar())
                    Log.d("POST","Vacceo: "+vacceo.getVida()+"/ 120   Progress: "+vidaVacceo.progress)

                    //es carlos:D
                    if(enemigo.getTipo()==2 && vivos==1){
                        invoca++
                        Log.d("HP", "REVIVE: "+ invoca.toString())

                        if(invoca==2){
                            invocacion()
                            invoca=0
                        }
                    }
                }

                else{
                    enemigo.minusStun()
                    if(enemigo.getStun()<=0) {
                        efectoEnemigos[cont].setImageDrawable(null)
                        efectoEnemigos[cont].setImageResource(R.drawable.danno) // Asigna la nueva
                    }
                }
            } else {
                efectoEnemigos[cont].setImageDrawable(null)
                efectoEnemigos[cont].visibility = View.INVISIBLE
            }
            cont++

        }
        vidaVacceo.progress = (vacceo.getVida()*100)/vacceo.getVidaMAX()
        updateHPBars()
        if(vivos==0){
            ganaste()
        }
        else if (vacceo.getVida()<=0){
            perdiste()
        }
    }

    private fun invocacion() {
        if (nivel >= 3) {
            reviveEsqueleto(0)
            reviveEsqueleto(1)
        } else {
            val enemigoAleatorio = (0..1).random()
            reviveEsqueleto(enemigoAleatorio)
        }
    }

    private fun reviveEsqueleto(index: Int) {
        enemigos[index].revive()
        vidasEnemigo[index].visibility = View.VISIBLE
        vidasEnemigo[index].progress = (enemigos[index].getVida() * 100) / enemigos[index].getMaxVida()
        updateHPBars()

        efectoEnemigos[index].setImageDrawable(null)
        efectoEnemigos[index].setImageResource(R.drawable.danno)
        efectoEnemigos[index].visibility = View.VISIBLE

        val sprite = enemigosLayout.getChildAt(index)
        if (sprite is ImageView) {
            val animationResource = when (index) {
                0 -> R.drawable.animation_skeleton_1
                else -> R.drawable.animation_skeleton_2
            }
            sprite.setImageResource(animationResource)
            val idleAnimation = sprite.drawable as? AnimationDrawable
            idleAnimation?.start()
        }
    }

    private fun perdiste() {
        stopService(Intent(this, MusicService::class.java))
        val intent = DerrotaActivity.createIntent(this, nivel)
        finish()
        startActivity(intent)
    }

    private fun ganaste() {
        stopService(Intent(this, MusicService::class.java))
        val intent = VictoriaActivity.createIntent(this, nivel)
        finish()
        startActivity(intent)
    }

    // Función para generar Romanos
    private fun generarRomanos(romanoLayout: LinearLayout) {
        for (i in 1..3) {
            enemigos.add(Romano())
        }
        val handler = Handler(Looper.getMainLooper())
        val delayIncrement = 500L // Tiempo entre el inicio del movimiento de cada romano en milisegundos

        enemigos.forEachIndexed { index, romano ->
            val sprite = ImageView(this)
            sprite.setImageResource(R.drawable.animation_romanos)
            val idleAnimation = sprite.drawable as? AnimationDrawable
            idleAnimation?.start()

            sprite.layoutParams = LinearLayout.LayoutParams(
                300, // Ancho del sprite del romano
                400   // Alto del sprite del romano
            ).apply {
                marginEnd = 16
            }

            romanoLayout.addView(sprite)
            seleccionRomano(sprite,index)

            // Movimiento con retraso
            handler.postDelayed({
                moverRomano(sprite)
            }, index * delayIncrement) // Incrementar el delay para cada romano
        }

        for (imageView in efectoEnemigos) {
            imageView.setImageResource(R.drawable.danno)
        }

    }

    private fun generarNecromancer(enemigosLayout: LinearLayout) {
        enemigos.add(Esqueleto_Sword())
        enemigos.add(Esqueleto_Axe())
        enemigos.add(Necromancer())

        enemigos.forEachIndexed { index, enemigo ->
            val sprite = ImageView(this)

            // Configurar el recurso de animación basado en la posición
            val animationResource = when (index) {
                0 -> R.drawable.animation_skeleton_1
                1 -> R.drawable.animation_skeleton_2
                else -> R.drawable.animation_necromancer
            }

            sprite.setImageResource(animationResource)
            val idleAnimation = sprite.drawable as? AnimationDrawable
            idleAnimation?.start()

            sprite.layoutParams = LinearLayout.LayoutParams(
                300, // Ancho del sprite
                400  // Alto del sprite
            ).apply {
                marginEnd = 16
            }

            enemigosLayout.addView(sprite)
            seleccionRomano(sprite, index)

        }

        for (imageView in efectoEnemigos) {
            imageView.setImageResource(R.drawable.danno)
        }
    }


    private fun jugarCartas(carta: Carta) {
        val controlar = ControladorDeComandos()
        if (carta.tipo == 0) {
            controlar.inicializa(ComandoArtifact(carta, this))
        } else if (carta.tipo == 1) {
            controlar.inicializa(ComandoFood(carta as Food, this))
        } else if (carta.tipo == 2) {
            controlar.inicializa(ComandoWeapon(carta as Weapon, this))
        }
        controlar.ejecuta()
    }

    private fun moverRomano(sprite: ImageView) {
        sprite.animate()
            .translationXBy(100f) // Cambia este valor para ajustar la distancia del movimiento
            .setDuration(300)     // Duración del movimiento en milisegundos
            .withEndAction {
                // Opción: Regresar a la posición inicial
                sprite.animate()
                    .translationXBy(-100f)
                    .setDuration(300)
                    .start()
            }
            .start()
    }


    private fun updateHPBars() {
        Log.d("HP", "VACCEO:")
        updateColor(vidaVacceo) // Vacceo
        // Romanos (me la agarras con las manos)
        Log.d("HP", "ENEMIGOS:")

        for (barra in vidasEnemigo) {
            updateColor(barra)
        }
    }

    private fun updateColor(barra: ProgressBar) {
        Log.d("HP", barra.progress.toString())
        if (barra.progress > 50)
            barra.progressTintList = ContextCompat.getColorStateList(this, R.color.green)
        else if (barra.progress <= 50 && barra.progress > 20)
            barra.progressTintList = ContextCompat.getColorStateList(this, R.color.yellow)
        else if (barra.progress <= 20)
            barra.progressTintList = ContextCompat.getColorStateList(this, R.color.red)
    }


    override fun onRestart() {
        super.onRestart()
        hideSystemUI()
        val musicServiceIntent = Intent(this, MusicService::class.java)
        musicServiceIntent.putExtra("MUSIC_RES_ID", R.raw.background_game)
        startService(musicServiceIntent)
    }

    override fun onPause() {
        super.onPause()
        stopService(Intent(this, MusicService::class.java))
    }

    private fun hideSystemUI() {
        window.decorView.systemUiVisibility = (
                View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                        or View.SYSTEM_UI_FLAG_FULLSCREEN
                        or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                )
    }
    private fun updatePlayButtonState() {
        val playCardButton = findViewById<ImageButton>(R.id.btnDrawCard)
        playCardButton.isEnabled = selectedCards.isNotEmpty()
        playCardButton.alpha = if (selectedCards.isNotEmpty()) 1.0f else 0.5f

    }
    private fun gestionMana(cantidadMana: Int) {
        mana.setImageDrawable(null)
        when (cantidadMana){
            0 -> mana.setImageResource(R.drawable.energia_0)
            1 -> mana.setImageResource(R.drawable.energia_1)
            2 -> mana.setImageResource(R.drawable.energia_2)
            3 -> mana.setImageResource(R.drawable.energia_3)
            4 -> mana.setImageResource(R.drawable.energia_4)
            5 -> mana.setImageResource(R.drawable.energia_5)
            else -> println("Número desconocido")
        }
    }


    // Para seleccionar nivel
    companion object {
        const val EXTRA_NIVEL = "extra_nivel"

        // Método para crear un Intent para iniciar esta actividad
        fun createIntent(context: Context, nivel: Int): Intent {
            return Intent(context, GameActivity::class.java).apply {
                putExtra(EXTRA_NIVEL, nivel)
            }
        }
    }

}
