package com.example.juegocartas

import android.app.Service
import android.content.Intent
import android.media.MediaPlayer
import android.os.IBinder
import android.util.Log

class MusicService: Service() {
    private lateinit var mediaPlayer: MediaPlayer
    val musicResourceNames = mapOf(
        R.raw.background_menu to "Música de menú",
        R.raw.background_game to "Música de juego",
        R.raw.derrota to "Música de derrota",
        R.raw.victoria to "Música de victoria"
    )
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        val musicResId = intent?.getIntExtra("MUSIC_RES_ID", R.raw.background_menu) ?: R.raw.background_menu

        mediaPlayer = MediaPlayer.create(this, musicResId)
        mediaPlayer.isLooping = true
        mediaPlayer.start()
        val musicName = musicResourceNames[musicResId] ?: "Nombre no encontrado"
        Log.d("MusicService", "Servicio de música: $musicName")
        return START_STICKY
    }

    override fun onDestroy() {
        super.onDestroy()
        if (::mediaPlayer.isInitialized) {
            mediaPlayer.stop()
            mediaPlayer.release()
        }
        Log.d("MusicService", "Servicio de música detenido")
    }

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

}