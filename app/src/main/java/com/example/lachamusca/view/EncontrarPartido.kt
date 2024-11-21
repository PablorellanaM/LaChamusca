package com.example.lachamusca.view

import android.content.Intent
import android.net.Uri
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
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

@Composable
fun EncontrarPartidoScreen(navController: NavHostController) {
    val db = FirebaseFirestore.getInstance()
    val partidos = remember { mutableStateListOf<Map<String, Any>>() }
    val cargando = remember { mutableStateOf(true) }
    val error = remember { mutableStateOf<String?>(null) }
    val currentUser = FirebaseAuth.getInstance().currentUser
    val userId = currentUser?.uid

    // Cargar partidos desde Firebase
    LaunchedEffect(Unit) {
        db.collection("partidos")
            .get()
            .addOnSuccessListener { result ->
                try {
                    partidos.clear()
                    partidos.addAll(result.documents.mapNotNull { it.data })
                    cargando.value = false
                } catch (e: Exception) {
                    error.value = "Error al procesar datos: ${e.message}"
                    cargando.value = false
                }
            }
            .addOnFailureListener { exception ->
                error.value = exception.message
                cargando.value = false
            }
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
                text = "Partidos Disponibles",
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
                    items(partidos) { partido ->
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
                                    text = "Nombre: ${partido["nombre"] as? String ?: "Sin nombre"}",
                                    fontSize = 18.sp,
                                    color = Color.Black
                                )
                                Text(
                                    text = "Descripción: ${partido["descripcion"] as? String ?: "Sin descripción"}",
                                    fontSize = 16.sp,
                                    color = Color.Gray
                                )
                                Text(
                                    text = "Ubicación: ${partido["ubicacion"] as? String ?: "Sin ubicación"}",
                                    fontSize = 16.sp,
                                    color = Color.Gray
                                )
                                Text(
                                    text = "Jugadores: ${(partido["jugadores"] as? List<*>)?.size ?: 0} / ${partido["limiteJugadores"] as? Int ?: 0}",
                                    fontSize = 16.sp,
                                    color = Color.Gray
                                )
                                Spacer(modifier = Modifier.height(8.dp))
                                Row(
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    modifier = Modifier.fillMaxWidth()
                                ) {
                                    // Botón para ver ubicación en el mapa
                                    Button(
                                        onClick = {
                                            val ubicacion = partido["ubicacion"] as? String
                                            if (ubicacion != null) {
                                                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(ubicacion))
                                                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                                                navController.context.startActivity(intent)
                                            }
                                        },
                                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF6200EA))
                                    ) {
                                        Text("Ver Mapa", color = Color.White)
                                    }

                                    // Botón para unirse al partido
                                    Button(
                                        onClick = {
                                            if (userId != null) {
                                                val jugadores = (partido["jugadores"] as? MutableList<String>) ?: mutableListOf()
                                                if (jugadores.size < (partido["limiteJugadores"] as? Int ?: 0)) {
                                                    jugadores.add(userId)
                                                    db.collection("partidos")
                                                        .document(partido["id"] as String)
                                                        .update("jugadores", jugadores)
                                                        .addOnSuccessListener {
                                                            partidos.remove(partido) // Quitar el partido si el usuario se une
                                                        }
                                                        .addOnFailureListener { e ->
                                                            error.value = "Error al unirse: ${e.message}"
                                                        }
                                                } else {
                                                    error.value = "Partido lleno."
                                                }
                                            }
                                        },
                                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF6200EA))
                                    ) {
                                        Text("Unirse", color = Color.White)
                                    }
                                }
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
