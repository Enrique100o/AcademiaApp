package com.example.academiaapp.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.academiaapp.viewmodel.ProductoUiState
import com.example.academiaapp.viewmodel.ProductoViewModel
import androidx.compose.foundation.clickable
@Composable
fun ProductosScreen(
    modifier: Modifier = Modifier,
    productoViewModel: ProductoViewModel = viewModel(),
    onVerDetalle: (Int) -> Unit = {}
) {
    val estado by productoViewModel.estado.collectAsState()

    LaunchedEffect(productoViewModel) {
        productoViewModel.cargarProductos()
    }

    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        when (val estadoActual = estado) {
            ProductoUiState.Loading -> {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    CircularProgressIndicator()
                    Text(text = "Cargando productos...")
                }
            }

            is ProductoUiState.Success -> {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    item {
                        Column(
                            verticalArrangement = Arrangement.spacedBy(12.dp)
                        ) {
                            Text(
                                text = "Productos desde Internet",
                                style = MaterialTheme.typography.headlineSmall
                            )

                            Button(
                                onClick = {
                                    productoViewModel.agregarProducto(
                                        nombre = "Cuaderno académico",
                                        precio = 15.0,
                                        categoria = "stationery"
                                    )
                                },
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Text(text = "Enviar producto de prueba")
                            }
                        }
                    }

                    items(
                        items = estadoActual.productos,
                        key = { producto -> producto.id }
                    ) { producto ->
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable {
                                    onVerDetalle(producto.id)
                                }
                        ) {
                            Column(
                                modifier = Modifier.padding(16.dp),
                                verticalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                Text(
                                    text = producto.title,
                                    style = MaterialTheme.typography.titleMedium
                                )

                                Text(text = "Categoría: ${producto.category}")
                                Text(text = "Precio: ${producto.price}")
                            }
                        }
                    }
                }
            }
            ProductoUiState.Enviando -> {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    CircularProgressIndicator()
                    Text(text = "Enviando producto...")
                }
            }

            is ProductoUiState.ProductoAgregado -> {
                Column(
                    modifier = Modifier.padding(24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Text(
                        text = "Producto enviado correctamente",
                        style = MaterialTheme.typography.headlineSmall
                    )

                    Text(text = "ID: ${estadoActual.producto.id}")
                    Text(text = "Nombre: ${estadoActual.producto.title}")
                    Text(text = "Precio: ${estadoActual.producto.price}")
                    Text(text = "Categoría: ${estadoActual.producto.category}")

                    Button(
                        onClick = {
                            productoViewModel.cargarProductos()
                        }
                    ) {
                        Text(text = "Volver al listado")
                    }
                }
            }
            is ProductoUiState.Error -> {
                Column(
                    modifier = Modifier.padding(24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Text(text = estadoActual.mensaje)

                    Button(
                        onClick = {
                            productoViewModel.cargarProductos()
                        }
                    ) {
                        Text(text = "Reintentar")
                    }
                }
            }
        }
    }
}