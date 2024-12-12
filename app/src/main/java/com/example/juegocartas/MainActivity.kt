package com.example.juegocartas

import android.annotation.SuppressLint
import android.app.ActivityManager
import android.content.Intent
import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import android.widget.ImageButton
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

@Suppress("DEPRECATION")
class MainActivity : AppCompatActivity() {
    @SuppressLint("ClickableViewAccessibility")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        hideSystemUI()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val musicServiceIntent = Intent(this, MusicService::class.java)
        musicServiceIntent.putExtra("MUSIC_RES_ID", R.raw.background_menu)
        startService(musicServiceIntent)

        val jugar = findViewById<ImageButton>(R.id.jugarB)
        jugar.setOnClickListener {
            // La música seguirá sonando
            val intent = GameActivity.createIntent(this, 1)
            finish()
            startActivity(intent)
        }

        val guia = findViewById<ImageButton>(R.id.guiaB)
        guia.setOnClickListener {
            // La música seguirá sonando
            val intent = Intent(this, GuideActivity::class.java)
            finish()
            startActivity(intent)
        }
    }

    override fun onRestart() {
        super.onRestart()
        hideSystemUI()
        val musicServiceIntent = Intent(this, MusicService::class.java)
        musicServiceIntent.putExtra("MUSIC_RES_ID", R.raw.background_menu)
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
}
