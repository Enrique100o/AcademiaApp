package com.example.academiaapp

import java.util.UUID

data class Actividad(
    val nombre: String,
    val descripcion: String,
    val tipo: String,
    val id: String = UUID.randomUUID().toString(),
    val completada: Boolean = false
)