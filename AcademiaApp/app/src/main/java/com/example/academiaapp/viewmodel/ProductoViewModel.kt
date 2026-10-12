package com.example.academiaapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.academiaapp.repository.ProductoRepository
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException
import com.example.academiaapp.model.CrearProductoRequest

class ProductoViewModel : ViewModel() {

    private val repository = ProductoRepository()

    private val _estado = MutableStateFlow<ProductoUiState>(
        ProductoUiState.Loading
    )

    val estado: StateFlow<ProductoUiState> = _estado.asStateFlow()
    fun agregarProducto(
        nombre: String,
        precio: Double,
        categoria: String
    ) {
        viewModelScope.launch {
            _estado.value = ProductoUiState.Enviando

            try {
                val solicitud = CrearProductoRequest(
                    title = nombre.trim(),
                    price = precio,
                    category = categoria.trim()
                )

                val productoCreado = repository.agregarProducto(
                    producto = solicitud
                )

                _estado.value = ProductoUiState.ProductoAgregado(
                    producto = productoCreado
                )
            } catch (e: CancellationException) {
                throw e
            } catch (e: IOException) {
                _estado.value = ProductoUiState.Error(
                    mensaje = "Error de conexión al enviar el producto."
                )
            } catch (e: HttpException) {
                _estado.value = ProductoUiState.Error(
                    mensaje = "El servidor respondió con el código ${e.code()}."
                )
            } catch (e: Exception) {
                _estado.value = ProductoUiState.Error(
                    mensaje = "No se pudo enviar el producto."
                )
            }
        }
    }
    fun cargarProductos() {
        viewModelScope.launch {
            _estado.value = ProductoUiState.Loading

            try {
                val respuesta = repository.obtenerProductos()

                _estado.value = ProductoUiState.Success(
                    productos = respuesta.products
                )
            } catch (e: CancellationException) {
                throw e
            } catch (e: IOException) {
                _estado.value = ProductoUiState.Error(
                    mensaje = "Error de conexión. Revisa tu acceso a Internet."
                )
            } catch (e: HttpException) {
                _estado.value = ProductoUiState.Error(
                    mensaje = "El servidor respondió con el código ${e.code()}."
                )
            } catch (e: Exception) {
                _estado.value = ProductoUiState.Error(
                    mensaje = "No se pudieron cargar los productos."
                )
            }
        }
    }
}