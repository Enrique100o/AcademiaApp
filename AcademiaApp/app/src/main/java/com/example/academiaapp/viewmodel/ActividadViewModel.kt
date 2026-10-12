package com.example.academiaapp.viewmodel

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import com.example.academiaapp.Actividad

class ActividadViewModel : ViewModel() {

    val actividades = mutableStateListOf<Actividad>()
}