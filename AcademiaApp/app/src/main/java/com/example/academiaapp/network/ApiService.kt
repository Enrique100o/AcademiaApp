package com.example.academiaapp.network

import com.example.academiaapp.model.CrearProductoRequest
import com.example.academiaapp.model.Producto
import com.example.academiaapp.model.ProductosResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface ApiService {

    @GET("products")
    suspend fun obtenerProductos(): ProductosResponse

    @POST("products/add")
    suspend fun agregarProducto(
        @Body producto: CrearProductoRequest
    ): Producto
}