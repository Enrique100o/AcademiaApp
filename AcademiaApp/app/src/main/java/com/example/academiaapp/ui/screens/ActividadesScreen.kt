package com.example.academiaapp.ui.screens
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.academiaapp.Actividad
import com.example.academiaapp.R
import com.example.academiaapp.ui.theme.AcademiaAppTheme

@Composable
fun ActividadesScreen(
    modifier: Modifier = Modifier,
    onActividadGuardada: () -> Unit = {},
    actividades: SnapshotStateList<Actividad> = remember {
        mutableStateListOf<Actividad>()
    }
) {
    var completada by remember {
        mutableStateOf(false)
    }

    var nombreActividad by remember {
        mutableStateOf("Parcial")
    }

    var nombreIngresado by remember {
        mutableStateOf("Parcial")
    }

    var errorNombre by remember {
        mutableStateOf(false)
    }

    var mostrarRecordatorio by remember {
        mutableStateOf(false)
    }
    var descripcionIngresada by remember {
        mutableStateOf("")
    }

    var descripcionActividad by remember {
        mutableStateOf("")
    }

    var tipoSeleccionado by remember {
        mutableStateOf("Examen")
    }

    var tipoActividad by remember {
        mutableStateOf("Examen")
    }


    Column(
        modifier = modifier
            .fillMaxSize()
            .imePadding()
            .verticalScroll(rememberScrollState())
            .padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Image(
                painter = painterResource(
                    id = R.drawable.logo
                ),
                contentDescription = "Logo de la aplicación",
                modifier = Modifier
                    .size(72.dp)
                    .background(
                        color = MaterialTheme.colorScheme.primary,
                        shape = MaterialTheme.shapes.medium
                    )
            )

            Column {
                Text(
                    text = "Mi app académica",
                    style = MaterialTheme.typography.headlineSmall
                )

                Text(text = "Estudiante: Faber")
            }
        }

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(110.dp)
                .background(
                    color = MaterialTheme.colorScheme.primaryContainer,
                    shape = MaterialTheme.shapes.medium
                ),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "Desarrollo móvil",
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.onPrimaryContainer
            )

            Text(
                text = "Mi semestre",
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(8.dp),
                color = MaterialTheme.colorScheme.onPrimaryContainer
            )
        }
        Text(
            text = "Editar actividad",
            style = MaterialTheme.typography.titleMedium
        )

        CampoNombreActividad(
            nombre = nombreIngresado,
            error = errorNombre,
            onNombreChange = { nuevoTexto ->
                nombreIngresado = nuevoTexto
                errorNombre = false
            }
        )
        TextField(
            value = descripcionIngresada,
            onValueChange = { nuevoTexto ->
                descripcionIngresada = nuevoTexto

            },
            label = {
                Text(text = "Descripción")
            },
            minLines = 2,
            maxLines = 4,
            modifier = Modifier.fillMaxWidth()
        )

        Text(
            text = "Tipo de actividad",
            style = MaterialTheme.typography.titleMedium
        )

        listOf("Examen", "Tarea", "Proyecto").forEach { opcion ->
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                RadioButton(
                    selected = tipoSeleccionado == opcion,
                    onClick = {
                        tipoSeleccionado = opcion

                    }
                )

                Text(text = opcion)
            }
        }
        Button(
            onClick = {
                val nombreLimpio = nombreIngresado.trim()

                if (nombreLimpio.isEmpty()) {
                    errorNombre = true

                } else {
                    nombreActividad = nombreLimpio
                    nombreIngresado = nombreLimpio
                    descripcionActividad = descripcionIngresada.trim()
                    tipoActividad = tipoSeleccionado
                    errorNombre = false

                    actividades.add(
                        Actividad(
                            nombre = nombreLimpio,
                            descripcion = descripcionIngresada.trim(),
                            tipo = tipoSeleccionado
                        )
                    )

                    onActividadGuardada()
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = "Guardar")
        }
        Text(
            text = "Actividades registradas: ${actividades.size}",
            style = MaterialTheme.typography.titleMedium
        )

        if (actividades.isEmpty()) {
            Text(text = "Todavía no hay actividades registradas")
        }

        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .height(320.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(
                items = actividades,
                key = { actividad -> actividad.id }
            ) { actividad ->
                Card(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Text(
                            text = actividad.nombre,
                            style = MaterialTheme.typography.titleMedium
                        )

                        Text(text = "Tipo: ${actividad.tipo}")
                        Text(
                            text = if (actividad.completada) {
                                "Estado: completada"
                            } else {
                                "Estado: pendiente"
                            }
                        )

                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Checkbox(
                                checked = actividad.completada,
                                onCheckedChange = { nuevoEstado ->
                                    val indice = actividades.indexOfFirst {
                                        it.id == actividad.id
                                    }

                                    if (indice != -1) {
                                        actividades[indice] = actividades[indice].copy(
                                            completada = nuevoEstado
                                        )
                                    }
                                }
                            )

                            Text(text = "Actividad completada")
                        }

                        if (actividad.descripcion.isNotBlank()) {
                            Text(text = actividad.descripcion)
                        }

                        OutlinedButton(
                            onClick = {
                                actividades.remove(actividad)
                            },
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text(text = "Eliminar")
                        }
                    }
                }
            }
        }

        Card(
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Icon(
                        painter = painterResource(
                            id = R.drawable.ic_launcher_foreground
                        ),
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.size(24.dp)
                    )

                    Text(
                        text = "Vista de la última actividad guardada",
                        style = MaterialTheme.typography.titleMedium
                    )
                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(text = nombreActividad)
                    Text(
                        text = if (completada) "Completada" else "Pendiente"
                    )
                }

                Text(text = "Curso: Desarrollo móvil")
                Text(text = "Tipo: $tipoActividad")

                if (descripcionActividad.isNotBlank()) {
                    Text(text = descripcionActividad)
                }
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Checkbox(
                        checked = completada,
                        onCheckedChange = { nuevoEstado ->
                            completada = nuevoEstado
                        }
                    )

                    Text(text = "Actividad completada")
                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(text = "Mostrar recordatorio")

                    Switch(
                        checked = mostrarRecordatorio,
                        onCheckedChange = { nuevoEstado ->
                            mostrarRecordatorio = nuevoEstado
                        }
                    )
                }

                if (mostrarRecordatorio) {
                    Text(
                        text = "Recordatorio: preparar $nombreActividad",
                        color = MaterialTheme.colorScheme.primary
                    )
                }
                Button(
                    onClick = {
                        completada = true
                    },
                    enabled = !completada,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(text = "Marcar como completada")
                }

                OutlinedButton(
                    onClick = {
                        completada = false
                    },
                    enabled = completada,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(text = "Volver a pendiente")
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    AcademiaAppTheme {
        ActividadesScreen()
    }
}
@Composable
fun CampoNombreActividad(
    nombre: String,
    error: Boolean,
    onNombreChange: (String) -> Unit
) {
    OutlinedTextField(
        value = nombre,
        onValueChange = onNombreChange,
        label = {
            Text(text = "Nombre de la actividad")
        },
        isError = error,
        supportingText = {
            if (error) {
                Text(text = "El nombre es obligatorio")
            }
        },
        singleLine = true,
        modifier = Modifier.fillMaxWidth()
    )
}