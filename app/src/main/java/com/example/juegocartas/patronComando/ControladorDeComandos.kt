package com.example.juegocartas.patronComando

class ControladorDeComandos {
    private var command: ComandoHabilidades? = null

    fun inicializa(command: ComandoHabilidades) {
        this.command = command
    }

    fun ejecuta() {
        command?.execute()
    }
}