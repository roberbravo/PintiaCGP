package com.example.juegocartas

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ImageButton
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.example.juegocartas.GameActivity.Companion

@Suppress("DEPRECATION")
class VictoriaActivity: AppCompatActivity() {


    @SuppressLint("ClickableViewAccessibility")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        hideSystemUI()
        setContentView(R.layout.win_screen)
        //paramos anterior
        stopService(Intent(this, MusicService::class.java))
        startService(Intent(this, MusicService::class.java).apply {
            putExtra("MUSIC_RES_ID", R.raw.victoria2)
        })

        val volverJuego = findViewById<ImageButton>(R.id.winReintentarB)
        volverJuego.setOnClickListener {
            stopService(Intent(this, MusicService::class.java))
            val nextLevel = intent.getIntExtra(EXTRA_NEXT_LEVEL, 1) + 1

            val intent = GameActivity.createIntent(this, nextLevel)
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
        musicServiceIntent.putExtra("MUSIC_RES_ID", R.raw.victoria)
        startService(musicServiceIntent)
    }

    override fun onPause() {
        super.onPause()
        stopService(Intent(this, MusicService::class.java))
    }

    companion object {
        const val EXTRA_NEXT_LEVEL = "extra_next_level"

        fun createIntent(context: Context, nextLevel: Int): Intent {
            return Intent(context, VictoriaActivity::class.java).apply {
                putExtra(EXTRA_NEXT_LEVEL, nextLevel)
            }
        }
    }

}