package com.example.academiaapp.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun InicioScreen(
    onVerProductos: () -> Unit,
    onVerActividades: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(
            text = "Bienvenido, Faber",
            style = MaterialTheme.typography.headlineSmall
        )

        Text(
            text = "Consulta productos de Internet y organiza tus actividades."
        )

        Button(
            onClick = onVerProductos,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = "Ver productos")
        }

        OutlinedButton(
            onClick = onVerActividades,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = "Mis actividades")
        }
    }
}