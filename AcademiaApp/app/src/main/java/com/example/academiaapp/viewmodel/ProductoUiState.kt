package com.example.academiaapp.viewmodel

import com.example.academiaapp.model.Producto

sealed class ProductoUiState {

    object Loading : ProductoUiState()

    data class Success(
        val productos: List<Producto>
    ) : ProductoUiState()

    data class Error(
        val mensaje: String
    ) : ProductoUiState()

    object Enviando : ProductoUiState()

    data class ProductoAgregado(
        val producto: Producto
    ) : ProductoUiState()
}
