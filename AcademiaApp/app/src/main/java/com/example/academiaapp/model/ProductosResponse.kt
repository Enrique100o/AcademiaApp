package com.example.academiaapp.model

data class ProductosResponse(
    val products: List<Producto>,
    val total: Int,
    val skip: Int,
    val limit: Int
)