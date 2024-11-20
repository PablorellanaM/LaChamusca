package com.example.lachamusca.view

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.lachamusca.repository.Partido
import com.example.lachamusca.repository.PartidoRepository
import com.google.firebase.firestore.FirebaseFirestore

@Composable
fun EquiposParaUnirteScreen(navController: NavController) {
    val db = FirebaseFirestore.getInstance()
    val partidoRepository = PartidoRepository(db)

    // Posición del jugador obtenida de SharedPreferences o algún repositorio
    val posicionJugador = remember { mutableStateOf("") }

    // Estado para manejar los equipos desde Firestore
    val equiposFiltrados = remember { mutableStateListOf<Partido>() }
    val cargando = remember { mutableStateOf(true) }
    val error = remember { mutableStateOf<String?>(null) }

    // Obtener la posición del jugador (reemplazar por tu lógica real para cargar la posición)
    LaunchedEffect(Unit) {
        posicionJugador.value = obtenerPosicionJugador() // Implementar esta función
        partidoRepository.obtenerPartidosPorPosicion(
            posicionJugador.value,
            onSuccess = { partidos ->
                equiposFiltrados.clear()
                equiposFiltrados.addAll(partidos)
                cargando.value = false
            },
            onFailure = { exception ->
                error.value = exception.message
                cargando.value = false
            }
        )
    }

    // Diseño de la pantalla
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(brush = Brush.verticalGradient(colors = listOf(Color(0xFFA10202), Color(0xFF351111))))
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            Text(
                text = "Equipos",
                style = TextStyle(color = Color.White, fontSize = 25.sp),
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Botones de opciones principales
            Button(
                onClick = { navController.navigate("crearEquipo") }, // Navegar a CrearEquipoScreen
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 8.dp)
            ) {
                Text("Crear mi Equipo")
            }
            Button(
                onClick = { /* No acción específica porque ya está en esta pantalla */ },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp)
            ) {
                Text("Unirte a un Equipo")
            }

            if (cargando.value) {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.CenterHorizontally))
            } else if (error.value != null) {
                Text(
                    text = "Error: ${error.value}",
                    style = TextStyle(color = Color.Red),
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                )
            } else {
                LazyColumn(modifier = Modifier.fillMaxWidth()) {
                    items(equiposFiltrados) { equipo ->
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(8.dp)
                                .background(Color.White, shape = RoundedCornerShape(8.dp))
                                .border(BorderStroke(1.dp, Color.Gray), RoundedCornerShape(8.dp))
                                .padding(8.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Column {
                                Text(text = equipo.nombre, fontWeight = FontWeight.W400)
                                Text(
                                    text = equipo.descripcion,
                                    style = TextStyle(color = Color.Gray, fontSize = 14.sp)
                                )
                            }
                            Button(
                                onClick = { /* Acción al unirse */ },
                                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF009951)),
                                shape = RoundedCornerShape(8.dp)
                            ) {
                                Text(text = "Unirse", color = Color.White)
                            }
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Botón para regresar al menú
            Button(
                onClick = { navController.navigate("menu") },
                modifier = Modifier.align(Alignment.CenterHorizontally)
            ) {
                Text(text = "Menú")
            }
        }
    }
}

// Función ficticia para obtener la posición del jugador
// Implementa esta función para usar SharedPreferences o algún repositorio
fun obtenerPosicionJugador(): String {
    return "Defensa" // Cambiar por la posición real guardada
}
