package com.example.juegocartas

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ImageButton
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity

@Suppress("DEPRECATION")
class DerrotaActivity:AppCompatActivity() {

    private var nivel: Int = 1 // Valor por defecto

    @SuppressLint("ClickableViewAccessibility")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        hideSystemUI()

        setContentView(R.layout.lose_screen)
        //paramos anterior
        stopService(Intent(this, MusicService::class.java))
        startService(Intent(this, MusicService::class.java).apply {
            putExtra("MUSIC_RES_ID", R.raw.derrota)
        })



        val volverJuego = findViewById<ImageButton>(R.id.winReintentarB)
        volverJuego.setOnClickListener {
            stopService(Intent(this, MusicService::class.java))
            val currentLevel = intent.getIntExtra(EXTRA_NEXT_LEVEL, 1)

            val intent = GameActivity.createIntent(this, currentLevel)
            finish()
            startActivity(intent)

        }
        val inicio = findViewById<ImageButton>(R.id.winVolverB)
        inicio.setOnClickListener {
            stopService(Intent(this, MusicService::class.java))
            val intent = Intent(this, MainActivity::class.java)
            finish()
            startActivity(intent)
        }
    }

    private fun hideSystemUI() {
        window.decorView.systemUiVisibility = (
                View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                        or View.SYSTEM_UI_FLAG_FULLSCREEN
                        or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                )
    }

    override fun onRestart() {
        super.onRestart()
        hideSystemUI()
        val musicServiceIntent = Intent(this, MusicService::class.java)
        musicServiceIntent.putExtra("MUSIC_RES_ID", R.raw.derrota)
        startService(musicServiceIntent)
    }

    override fun onPause() {
        super.onPause()
        stopService(Intent(this, MusicService::class.java))
    }

    companion object {
        const val EXTRA_NEXT_LEVEL = "extra_next_level"

        fun createIntent(context: Context, nextLevel: Int): Intent {
            return Intent(context, DerrotaActivity::class.java).apply {
                putExtra(EXTRA_NEXT_LEVEL, nextLevel)
            }
        }
    }
}