package com.example.academiaapp.repository

import com.example.academiaapp.model.CrearProductoRequest
import com.example.academiaapp.model.Producto
import com.example.academiaapp.model.ProductosResponse
import com.example.academiaapp.network.RetrofitInstance

class ProductoRepository {

    suspend fun obtenerProductos(): ProductosResponse {
        return RetrofitInstance.api.obtenerProductos()
    }

    suspend fun agregarProducto(
        producto: CrearProductoRequest
    ): Producto {
        return RetrofitInstance.api.agregarProducto(producto)
    }
}