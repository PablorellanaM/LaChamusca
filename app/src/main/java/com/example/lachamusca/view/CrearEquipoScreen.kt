package com.example.lachamusca.view

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Group
import androidx.compose.material.icons.filled.PersonAdd
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CrearEquipoScreen(navController: NavHostController) {
    var nombreEquipo by remember { mutableStateOf("") }
    var posicionNecesaria by remember { mutableStateOf("") }
    val opcionesPosiciones = listOf("Arquero", "Defensa", "Mediocampista", "Delantero", "Todas")

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    colors = listOf(Color(0xFFA10202), Color(0xFF351111))
                )
            )
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            // Título
            Text(
                text = "Crear Equipo",
                fontSize = 24.sp,
                color = Color.White
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Campo para el nombre del equipo
            TextField(
                value = nombreEquipo,
                onValueChange = { nombreEquipo = it },
                label = { Text("Nombre del Equipo") },
                colors = TextFieldDefaults.textFieldColors(
                    containerColor = Color.White,
                    focusedTextColor = Color.Black,
                    unfocusedTextColor = Color.Black
                ),
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Selección de posición necesaria
            Text(
                text = "Selecciona una posición necesaria:",
                color = Color.White
            )

            opcionesPosiciones.forEach { opcion ->
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { posicionNecesaria = opcion }
                        .padding(vertical = 8.dp)
                ) {
                    RadioButton(
                        selected = posicionNecesaria == opcion,
                        onClick = { posicionNecesaria = opcion },
                        colors = RadioButtonDefaults.colors(selectedColor = Color.White)
                    )
                    Text(text = opcion, color = Color.White)
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Botón para guardar equipo
            Button(
                onClick = {
                    // Aquí guardarás los datos en Firestore
                },
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF6200EA))
            ) {
                Icon(imageVector = Icons.Default.Group, contentDescription = null, tint = Color.White)
                Spacer(modifier = Modifier.width(8.dp))
                Text("Guardar Equipo", color = Color.White)
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Botón para regresar al menú
            Button(
                onClick = { navController.navigate("menu") },
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF6200EA))
            ) {
                Icon(imageVector = Icons.Default.PersonAdd, contentDescription = null, tint = Color.White)
                Spacer(modifier = Modifier.width(8.dp))
                Text("Volver al Menú", color = Color.White)
            }
        }
    }
}
