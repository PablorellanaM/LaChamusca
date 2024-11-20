package com.example.lachamusca.view

import androidx.compose.foundation.background
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.lachamusca.repository.PartidoRepository
import com.google.firebase.firestore.FirebaseFirestore
import com.example.lachamusca.repository.Partido



@Composable
fun EquiposParaUnirteScreen(navController: NavController) {
    val db = FirebaseFirestore.getInstance()
    val partidoRepository = PartidoRepository(db)

    // Asegúrate de usar la clase Partido desde repository
    val equiposDisponibles = remember { mutableStateListOf<com.example.lachamusca.repository.Partido>() }
    val cargando = remember { mutableStateOf(true) }
    val error = remember { mutableStateOf<String?>(null) }

    LaunchedEffect(Unit) {
        partidoRepository.obtenerTodosLosPartidos(
            onSuccess = { partidos ->
                equiposDisponibles.clear()
                equiposDisponibles.addAll(partidos)
                cargando.value = false
            },
            onFailure = { exception ->
                error.value = exception.message
                cargando.value = false
            }
        )
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Brush.verticalGradient(colors = listOf(Color(0xFFA10202), Color(0xFF351111))))
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Equipos Disponibles",
                fontSize = 24.sp,
                color = Color.White
            )

            Spacer(modifier = Modifier.height(16.dp))

            if (cargando.value) {
                CircularProgressIndicator(color = Color.White)
            } else if (error.value != null) {
                Text(
                    text = "Error: ${error.value}",
                    color = Color.Red,
                    fontSize = 16.sp
                )
            } else {
                LazyColumn(modifier = Modifier.fillMaxWidth()) {
                    items(equiposDisponibles) { equipo ->
                        EquipoItem(equipo = equipo)
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = { navController.navigate("menu") },
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF6200EA))
            ) {
                Text("Volver al Menú", color = Color.White)
            }
        }
    }
}

@Composable
fun EquipoItem(equipo: Partido) {
    Card(
        shape = RoundedCornerShape(8.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .background(Color.White),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = "Nombre: ${equipo.nombre}",
                fontSize = 18.sp,
                color = Color.Black
            )
            Text(
                text = "Descripción: ${equipo.descripcion}",
                fontSize = 16.sp,
                color = Color.Gray
            )
            Text(
                text = "Ubicación: ${equipo.ubicacion}",
                fontSize = 16.sp,
                color = Color.Gray
            )
        }
    }
}
