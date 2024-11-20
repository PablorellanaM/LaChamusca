package com.example.lachamusca.view

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.lachamusca.repository.Partido
import com.example.lachamusca.repository.PartidoRepository
import com.google.firebase.firestore.FirebaseFirestore

@Composable
fun CrearEquipoScreen(navController: NavHostController) {
    var nombreEquipo by remember { mutableStateOf("") }
    var posicionesJugadores by remember { mutableStateOf(listOf<String>()) }
    var posicionDisponible by remember { mutableStateOf("") }
    var mensajeExito by remember { mutableStateOf<String?>(null) }
    var mensajeError by remember { mutableStateOf<String?>(null) }
    val partidoRepository = remember { PartidoRepository(FirebaseFirestore.getInstance()) } // Inicializa el repositorio

    Column(modifier = Modifier.padding(16.dp)) {
        // Campo para el nombre del equipo
        TextField(
            value = nombreEquipo,
            onValueChange = { nombreEquipo = it },
            label = { Text("Nombre del equipo") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(16.dp))

        // Lista de posiciones de los jugadores
        Text("Posiciones de jugadores:")
        LazyColumn(modifier = Modifier.fillMaxWidth()) {
            items(posicionesJugadores) { posicion ->
                Text(text = posicion, modifier = Modifier.padding(4.dp))
            }
        }
        OutlinedButton(
            onClick = {
                if (posicionDisponible.isNotEmpty()) {
                    posicionesJugadores = posicionesJugadores + posicionDisponible
                    posicionDisponible = "" // Limpia el campo después de agregar
                }
            },
            modifier = Modifier.padding(top = 8.dp)
        ) {
            Text("Agregar Posición")
        }
        Spacer(modifier = Modifier.height(16.dp))

        // Campo para posición disponible
        TextField(
            value = posicionDisponible,
            onValueChange = { posicionDisponible = it },
            label = { Text("Posición disponible") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(16.dp))

        // Botón para crear equipo
        Button(
            onClick = {
                if (nombreEquipo.isNotEmpty() && posicionesJugadores.isNotEmpty()) {
                    // Crea el objeto Partido y guárdalo
                    val nuevoEquipo = Partido(
                        nombre = nombreEquipo,
                        posicionesNecesarias = posicionesJugadores
                    )
                    partidoRepository.agregarPartido(
                        partido = nuevoEquipo,
                        onSuccess = {
                            mensajeExito = "Equipo creado exitosamente."
                            mensajeError = null
                            navController.navigate("menu") // Navega al menú si tiene éxito
                        },
                        onFailure = { exception ->
                            mensajeExito = null
                            mensajeError = "Error al crear equipo: ${exception.message}"
                        }
                    )
                } else {
                    mensajeError = "Por favor, completa todos los campos."
                }
            },
            modifier = Modifier.align(Alignment.CenterHorizontally)
        ) {
            Text("Crear Equipo")
        }

        // Mensaje de éxito
        mensajeExito?.let { mensaje ->
            Text(text = mensaje, color = Color.Green, modifier = Modifier.padding(top = 16.dp))
        }

        // Mensaje de error
        mensajeError?.let { mensaje ->
            Text(text = mensaje, color = Color.Red, modifier = Modifier.padding(top = 16.dp))
        }
    }
}
