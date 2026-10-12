package com.example.academiaapp.ui.screens

import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.academiaapp.viewmodel.ProductoUiState
import com.example.academiaapp.viewmodel.ProductoViewModel

@Composable
fun DetalleProductoScreen(
    productoId: Int,
    productoViewModel: ProductoViewModel,
    onVolver: () -> Unit,
    modifier: Modifier = Modifier
) {
    val estado by productoViewModel.estado.collectAsState()

    LaunchedEffect(productoViewModel) {
        if (productoViewModel.estado.value !is ProductoUiState.Success) {
            productoViewModel.cargarProductos()
        }
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        when (val estadoActual = estado) {
            ProductoUiState.Loading,
            ProductoUiState.Enviando -> {
                CircularProgressIndicator()
                Text(text = "Cargando información...")
            }

            is ProductoUiState.Success -> {
                val producto = estadoActual.productos.firstOrNull {
                    it.id == productoId
                }

                if (producto == null) {
                    Text(text = "No se encontró el producto.")
                } else {
                    Text(
                        text = producto.title,
                        style = MaterialTheme.typography.headlineSmall
                    )

                    Text(text = "ID: ${producto.id}")
                    Text(text = "Categoría: ${producto.category}")
                    Text(text = "Precio: ${producto.price}")
                }
            }

            is ProductoUiState.Error -> {
                Text(text = estadoActual.mensaje)
            }

            is ProductoUiState.ProductoAgregado -> {
                Text(text = "Regresa al listado para seleccionar un producto.")
            }
        }

        Button(onClick = onVolver) {
            Text(text = "Volver")
        }
    }
}