package com.example.juegocartas

import android.annotation.SuppressLint
import android.app.ActivityManager
import android.content.Intent
import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible

@Suppress("DEPRECATION")
class GuideActivity : AppCompatActivity() {

    // Variables declaradas sin inicializarlas aquí
    private lateinit var previous: ImageButton
    private lateinit var next: ImageButton

    private var pageTexts = listOf(
        "¡Bienvenido a la Pintia!\n Esta es una región llena de batallas contra los romanos.\n" +
                "Aqui te enseñare las cosas basicas para que seas capaz de defenderte de los romanos",

        "Para ayudarte tendras distintas cartas. Esta carta es la espada." +
                " Con ella podras hacer 10 de daño al romano más cercano, el de la posición 1.\n" +
                "Además cuando ataques con la espada tienes un 25% de probabilidades de hacer el doble de daño",

        "Este es el arco, tu amigo confiable. Con el arco atacaras a la retaguardia romana, " +
        "causando 10 de daño al romano más lejano.\n" +
        "También al igual que con la espada tienes un 25% de probabilidades de hacer el doble de daño.\n"+
        "Con este arma no tendrán donde esconderse de ti.",

        "Como olvidarnos de la lanza, el arma más fuerte de nuestra aldea. Con ella causaras 30 de daño al romano del medio." +
        "Con la posibilidad de hacer el doble de daño una de cada cuarto veces.\n"+
        "No podrán defenderse de tu poder.",

        "Oooh... la daga, una muy buena amiga. La daga es un arma muy útil en los momentos duros. Con tan solo coste 1. Este arma hace 7 de daño" +
                " al enemigo más cercano. Pero aquí no acaba la cosa, si no que además tiene un 75% de probabilidades de hacer el doble de daño." +
                " Sin duda, la necesitarás.",

        "Aquí tenemos el escudo, un arma defensiva muy útil. Con el podrás aturdir al romano más cercano"+
        " impidiendo que te pueda atarcar en el siguiente turno. Además de hacer un poco de daño.\n"+
        "Cuidado, que no te ataquen",

        "Para finalizar con las armas, tenemos la honda, un arma muy versatil. Con ella podrás aturdir al romano"+
        " más lejano e inflingirle la mitad de daño que un arco.\n"+ "¡Piedra va!",

        "En pintia no solo contaras con armas tambien tendras comida, cartas que te curarán vida"+
        " para aguantar más los golpes enemigo.\n"+ "Esta es una carta de fruta y te curara 5 puntos de vida",

        "También contamos con el grandiso plato de EL LECHAZO VACCEO. Esta comida te curará 20 puntos de vida.\n"+
        "Los romanos no podrán tumbarte.",

        "Y por último en la parte de comidas, tenemos a la joya de la corana. El vino vacceo. Esta carta te curará"+
        " 20 puntos de vida pero adiferencia del lechazo te pondra el efecto de embriadez y acertar golpes será más dificil.",

        "Por último tenemos un truco, la preparación bélica. Cuando uses esta carta robaras dos más para "+
        "asi tener más opciones a tu disposión."
    )
    private var index = 0

    private var pageCards = listOf(
        R.drawable.invisible,
        R.drawable.espada,
        R.drawable.arco,
        R.drawable.lanza,
        R.drawable.punnal,
        R.drawable.escudo,
        R.drawable.honda,
        R.drawable.fruta,
        R.drawable.lechazo,
        R.drawable.vino,
        R.drawable.preparacion_belica
    )

    @SuppressLint("ClickableViewAccessibility")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        hideSystemUI()

        setContentView(R.layout.activity_guide)

        val activityManager = getSystemService(ACTIVITY_SERVICE) as ActivityManager
        val isServiceRunning = activityManager.getRunningServices(Integer.MAX_VALUE).any {
            it.service.className == MusicService::class.java.name
        }

        if (!isServiceRunning) {
            val musicServiceIntent = Intent(this, MusicService::class.java)
            musicServiceIntent.putExtra("MUSIC_RES_ID", R.raw.background_menu)
            startService(musicServiceIntent)
        }

        // Inicializa los botones después de setContentView
        previous = findViewById(R.id.previous)
        next = findViewById(R.id.next)

        updateView()

        next.setOnClickListener {
            index++
            updateView()
        }

        previous.setOnClickListener {
            index--
            updateView()
        }

        val close = findViewById<ImageButton>(R.id.cerrar)
        close.setOnClickListener {
            stopService(Intent(this, MusicService::class.java))
            val intent = Intent(this, MainActivity::class.java)
            finish()
            startActivity(intent)
        }

        // Inicializar los TextViews
        val cerrarText = findViewById<TextView>(R.id.cerrarText)
        val previousText = findViewById<TextView>(R.id.previousText)
        val nextText = findViewById<TextView>(R.id.nextText)

        // Subir y bajar texto del boton cerrar al pulsar su boton
        findViewById<View>(R.id.cerrar).setOnTouchListener { _, event ->
            when (event.action) {
                MotionEvent.ACTION_DOWN -> {
                    // Mover el texto hacia abajo cuando se presiona el botón
                    cerrarText.translationY = 10f  // Mover hacia abajo 20dp
                }
                MotionEvent.ACTION_UP, MotionEvent.ACTION_CANCEL -> {
                    // Restaurar la posición del texto cuando se suelta el botón
                    cerrarText.translationY = 0f
                }
            }
            false
        }

        // Subir y bajar texto del boton previous al pulsar su boton
        findViewById<View>(R.id.previous).setOnTouchListener { _, event ->
            when (event.action) {
                MotionEvent.ACTION_DOWN -> {
                    // Mover el texto hacia abajo cuando se presiona el botón
                    previousText.translationY = 16f  // Mover hacia abajo 20dp
                }
                MotionEvent.ACTION_UP, MotionEvent.ACTION_CANCEL -> {
                    // Restaurar la posición del texto cuando se suelta el botón
                    previousText.translationY = 0f
                }
            }
            false
        }

        // Subir y bajar texto del boton next al pulsar su boton
        findViewById<View>(R.id.next).setOnTouchListener { _, event ->
            when (event.action) {
                MotionEvent.ACTION_DOWN -> {
                    // Mover el texto hacia abajo cuando se presiona el botón
                    nextText.translationY = 16f  // Mover hacia abajo 20dp
                }
                MotionEvent.ACTION_UP, MotionEvent.ACTION_CANCEL -> {
                    // Restaurar la posición del texto cuando se suelta el botón
                    nextText.translationY = 0f
                }
            }
            false
        }
    }

    private fun updateView() {
        previous.isVisible = index > 0
        next.isVisible = index < pageTexts.size - 1

        // Obtener los TextViews de los botones
        val previousText = findViewById<TextView>(R.id.previousText)
        val nextText = findViewById<TextView>(R.id.nextText)

        // Cambiar la visibilidad o la opacidad en función del estado de los botones
        if (previous.isVisible) {
            previousText.alpha = 1.0f
        } else {
            previousText.alpha = 0f
        }

        if (next.isVisible) {
            nextText.alpha = 1.0f
        } else {
            nextText.alpha = 0f
        }

        val textView = findViewById<TextView>(R.id.infor)
        textView.text = pageTexts[index]

        val imgCard = findViewById<ImageView>(R.id.img_card)
        imgCard.setImageResource(pageCards[index])
    }

    override fun onPause() {
        super.onPause()
        stopService(Intent(this, MusicService::class.java))
    }

    override fun onRestart() {
        super.onRestart()
        hideSystemUI()
        val musicServiceIntent = Intent(this, MusicService::class.java)
        musicServiceIntent.putExtra("MUSIC_RES_ID", R.raw.background_menu)
        startService(musicServiceIntent)
    }

    private fun hideSystemUI() {
        window.decorView.systemUiVisibility = (
                View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                        or View.SYSTEM_UI_FLAG_FULLSCREEN
                        or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                )
    }

}
