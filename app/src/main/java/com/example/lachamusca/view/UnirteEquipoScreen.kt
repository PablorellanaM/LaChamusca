package com.example.lachamusca.view

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Button
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp


@Composable
fun UnirteEquipoScreen(
    posicionUsuario: String,
    equipos: List<Equipo>,
    onJoinTeam: (Equipo) -> Unit
) {
    val equiposFiltrados = equipos.filter { equipo ->
        equipo.posicionesDisponibles.contains(posicionUsuario)
    }

    LazyColumn(modifier = Modifier.fillMaxSize()) {
        items(equiposFiltrados) { equipo ->
            Card(modifier = Modifier.padding(8.dp)) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text("Equipo: ${equipo.nombre}")
                    Text("Posiciones disponibles: ${equipo.posicionesDisponibles.joinToString(", ")}")
                    Button(onClick = { onJoinTeam(equipo) }) {
                        Text("Unirte")
                    }
                }
            }
        }
    }
}
