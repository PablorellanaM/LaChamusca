package com.example.lachamusca.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.google.firebase.firestore.FirebaseFirestore

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CrearPartidoScreen(navController: NavHostController) {
    var nombrePartido by remember { mutableStateOf("") }
    var descripcion by remember { mutableStateOf("") }
    var linkUbicacion by remember { mutableStateOf("") }
    var limiteJugadores by remember { mutableStateOf(10) }
    var mensaje by remember { mutableStateOf("") }

    // Instancia de Firebase Firestore
    val db = FirebaseFirestore.getInstance()

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
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {
            // Título
            Text(
                text = "Crear Partido",
                fontSize = 24.sp,
                color = Color.White
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Campo para el nombre del partido
            TextField(
                value = nombrePartido,
                onValueChange = { nombrePartido = it },
                label = { Text("Nombre del Partido") },
                modifier = Modifier.fillMaxWidth(),
                colors = TextFieldDefaults.textFieldColors(containerColor = Color.White)
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Campo para la descripción
            TextField(
                value = descripcion,
                onValueChange = { descripcion = it },
                label = { Text("Descripción") },
                modifier = Modifier.fillMaxWidth(),
                colors = TextFieldDefaults.textFieldColors(containerColor = Color.White)
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Campo para la ubicación
            TextField(
                value = linkUbicacion,
                onValueChange = { linkUbicacion = it },
                label = { Text("Ubicación (Google Maps Link)") },
                modifier = Modifier.fillMaxWidth(),
                colors = TextFieldDefaults.textFieldColors(containerColor = Color.White)
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Campo para el límite de jugadores
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = "Límite de Jugadores:",
                    color = Color.White,
                    modifier = Modifier.weight(1f)
                )
                TextField(
                    value = limiteJugadores.toString(),
                    onValueChange = { limiteJugadores = it.toIntOrNull() ?: limiteJugadores },
                    label = { Text("Máximo") },
                    modifier = Modifier.weight(1f),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    colors = TextFieldDefaults.textFieldColors(containerColor = Color.White)
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Botón para guardar partido
            Button(
                onClick = {
                    if (nombrePartido.isNotEmpty() && descripcion.isNotEmpty() && linkUbicacion.isNotEmpty()) {
                        val partido = mapOf(
                            "nombre" to nombrePartido,
                            "descripcion" to descripcion,
                            "ubicacion" to linkUbicacion,
                            "limiteJugadores" to limiteJugadores,
                            "jugadores" to listOf<String>()
                        )
                        db.collection("partidos")
                            .add(partido)
                            .addOnSuccessListener {
                                mensaje = "Partido creado exitosamente."
                                nombrePartido = ""
                                descripcion = ""
                                linkUbicacion = ""
                                limiteJugadores = 10
                            }
                            .addOnFailureListener { e ->
                                mensaje = "Error al crear partido: ${e.message}"
                            }
                    } else {
                        mensaje = "Por favor, completa todos los campos."
                    }
                },
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF6200EA))
            ) {
                Text("Guardar Partido", color = Color.White)
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Botón para volver al menú
            Button(
                onClick = { navController.navigate("menu") },
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF6200EA))
            ) {
                Text("Volver al Menú", color = Color.White)
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Mostrar mensaje de éxito o error
            if (mensaje.isNotEmpty()) {
                Text(text = mensaje, color = Color.Yellow, fontSize = 16.sp)
            }
        }
    }
}
