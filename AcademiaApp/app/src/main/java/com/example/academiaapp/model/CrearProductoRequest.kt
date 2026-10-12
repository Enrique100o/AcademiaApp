package com.example.academiaapp.model

data class CrearProductoRequest(
    val title: String,
    val price: Double,
    val category: String,
    val thumbnail: String = ""
)