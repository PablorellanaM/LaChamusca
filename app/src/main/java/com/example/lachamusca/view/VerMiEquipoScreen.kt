package com.example.lachamusca.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.lachamusca.repository.Equipo
import com.example.lachamusca.repository.EquipoRepository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

@Composable
fun VerMiEquipoScreen(navController: NavHostController) {
    val equipoRepository = EquipoRepository(FirebaseFirestore.getInstance())
    val equipos = remember { mutableStateListOf<Equipo>() }
    val cargando = remember { mutableStateOf(true) }
    val error = remember { mutableStateOf<String?>(null) }
    val currentUser = FirebaseAuth.getInstance().currentUser
    val userId = currentUser?.uid

    // Cargar equipos a los que pertenece el usuario desde Firebase
    LaunchedEffect(Unit) {
        equipoRepository.obtenerTodosLosEquipos(
            onSuccess = { listaEquipos ->
                equipos.clear()
                // Filtrar equipos a los que el usuario pertenece
                equipos.addAll(
                    listaEquipos.filter { equipo ->
                        userId != null && equipo.miembros.contains(userId)
                    }
                )
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
            .background(
                Brush.verticalGradient(
                    colors = listOf(Color(0xFFA10202), Color(0xFF351111))
                )
            )
            .padding(16.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Mi Equipo",
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
            } else if (equipos.isEmpty()) {
                Text(
                    text = "No te has unido a ningún equipo.",
                    color = Color.White,
                    fontSize = 16.sp
                )
            } else {
                LazyColumn(modifier = Modifier.fillMaxWidth()) {
                    items(equipos) { equipo ->
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(8.dp),
                            colors = CardDefaults.cardColors(containerColor = Color.White)
                        ) {
                            Column(
                                modifier = Modifier.padding(16.dp)
                            ) {
                                Text(
                                    text = "Nombre: ${equipo.nombre}",
                                    fontSize = 18.sp,
                                    color = Color.Black
                                )
                                Text(
                                    text = "Posición Solicitada: ${equipo.posicionSolicitada}",
                                    fontSize = 16.sp,
                                    color = Color.Gray
                                )
                                Text(
                                    text = "Miembros: ${equipo.miembros.size} / 10",
                                    fontSize = 16.sp,
                                    color = Color.Gray
                                )
                            }
                        }
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
