package com.example.academiaapp

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.academiaapp.ui.screens.ProductosScreen
import com.example.academiaapp.viewmodel.ActividadViewModel
import kotlinx.coroutines.launch
import com.example.academiaapp.ui.screens.InicioScreen
import androidx.navigation.NavType
import androidx.navigation.navArgument
import com.example.academiaapp.ui.screens.DetalleProductoScreen
import com.example.academiaapp.viewmodel.ProductoViewModel
import com.example.academiaapp.ui.screens.ActividadesScreen
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppAcademica() {
    val navController = rememberNavController()
    val entradaActual by navController.currentBackStackEntryAsState()
    val rutaActual = entradaActual?.destination?.route

    val actividadViewModel: ActividadViewModel = viewModel()
    val productoViewModel: ProductoViewModel = viewModel()
    val snackbarHostState = remember {
        SnackbarHostState()
    }
    val scope = rememberCoroutineScope()

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = when (rutaActual) {
                            "productos" -> "Productos"
                            "detalle/{productoId}" -> "Detalle del producto"
                            "actividades" -> "Actividades"
                            else -> "Inicio"
                        }
                    )
                }
            )
        },
        bottomBar = {
            NavigationBar {
                listOf(
                    "inicio" to "Inicio",
                    "productos" to "Productos",
                    "actividades" to "Actividades"
                ).forEach { (ruta, titulo) ->
                    NavigationBarItem(
                        selected = rutaActual == ruta ||
                                (ruta == "productos" && rutaActual == "detalle/{productoId}"),
                        onClick = {
                            navController.navigate(ruta) {
                                popUpTo(
                                    navController.graph.findStartDestination().id
                                ) {
                                    saveState = true
                                }
                                launchSingleTop = true
                                restoreState = true
                            }
                        },
                        icon = {
                            Text(
                                text = when (ruta) {
                                    "inicio" -> "I"
                                    "productos" -> "P"
                                    else -> "A"
                                }
                            )
                        },
                        label = {
                            Text(text = titulo)
                        }
                    )
                }
            }
        },
        snackbarHost = {
            SnackbarHost(
                hostState = snackbarHostState,
                modifier = Modifier.imePadding()
            )
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = "inicio",
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            composable("inicio") {
                InicioScreen(
                    onVerProductos = {
                        navController.navigate("productos") {
                            launchSingleTop = true
                            restoreState = true
                        }
                    },
                    onVerActividades = {
                        navController.navigate("actividades") {
                            launchSingleTop = true
                            restoreState = true
                        }
                    }
                )
            }
            composable("actividades") {
                ActividadesScreen(
                    actividades = actividadViewModel.actividades,
                    onActividadGuardada = {
                        scope.launch {
                            snackbarHostState.showSnackbar(
                                message = "Actividad guardada"
                            )
                        }
                    }
                )
            }

            composable("productos") {
                ProductosScreen(
                    productoViewModel = productoViewModel,
                    onVerDetalle = { productoId ->
                        navController.navigate("detalle/$productoId") {
                            launchSingleTop = true
                        }
                    }
                )
            }
            composable(
                route = "detalle/{productoId}",
                arguments = listOf(
                    navArgument("productoId") {
                        type = NavType.IntType
                    }
                )
            ) { entrada ->
                val productoId = entrada.arguments
                    ?.getInt("productoId") ?: -1

                DetalleProductoScreen(
                    productoId = productoId,
                    productoViewModel = productoViewModel,
                    onVolver = {
                        navController.popBackStack()
                    }
                )
            }
        }
    }
}