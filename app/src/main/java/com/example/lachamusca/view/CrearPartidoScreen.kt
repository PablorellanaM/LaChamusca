package com.example.lachamusca.view

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.google.firebase.firestore.FirebaseFirestore

@OptIn(ExperimentalMaterial3Api::class) // Habilita el uso de la API experimental
@Composable
fun CrearPartidoScreen(navController: NavController) {
    var canchaName by remember { mutableStateOf(TextFieldValue("")) }
    var cantidadParticipantes by remember { mutableStateOf(TextFieldValue("")) }
    var ubicacion by remember { mutableStateOf(TextFieldValue("")) }

    var duracion by remember { mutableStateOf(1.0) } // 1 hora por defecto
    var expandedDropdown by remember { mutableStateOf(false) }
    var horarioSeleccionado by remember { mutableStateOf("") }

    // Generar horarios según la duración seleccionada
    val horariosDisponibles = generarHorariosDisponibles(duracion)

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
                text = "Crear Partido",
                style = TextStyle(color = Color.White, fontSize = 25.sp, fontWeight = FontWeight.W400),
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )

            Spacer(modifier = Modifier.height(32.dp))

            Text(
                text = "Introduzca la Siguiente Información",
                style = TextStyle(color = Color.White, fontSize = 20.sp, fontWeight = FontWeight.W400)
            )

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = canchaName,
                onValueChange = { canchaName = it },
                label = { Text("NOMBRE DE LA CANCHA") },
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.White, shape = RoundedCornerShape(8.dp))
            )

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = cantidadParticipantes,
                onValueChange = { cantidadParticipantes = it },
                label = { Text("CANTIDAD DE PARTICIPANTES") },
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.White, shape = RoundedCornerShape(8.dp))
            )

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = ubicacion,
                onValueChange = { ubicacion = it },
                label = { Text("UBICACIÓN") },
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.White, shape = RoundedCornerShape(8.dp))
            )

            Spacer(modifier = Modifier.height(16.dp))

            Row(
                horizontalArrangement = Arrangement.SpaceEvenly,
                modifier = Modifier.fillMaxWidth()
            ) {
                Button(
                    onClick = { duracion = 1.0 },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = if (duracion == 1.0) Color(0xFF00C853) else Color.Gray
                    ),
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Text(text = "1 Hora")
                }

                Button(
                    onClick = { duracion = 1.5 },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = if (duracion == 1.5) Color(0xFF00C853) else Color.Gray
                    ),
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Text(text = "1.5 Horas")
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            ExposedDropdownMenuBox(
                expanded = expandedDropdown,
                onExpandedChange = { expandedDropdown = !expandedDropdown }
            ) {
                OutlinedTextField(
                    value = horarioSeleccionado,
                    onValueChange = {},
                    readOnly = true,
                    label = { Text("Horario") },
                    trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expandedDropdown) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color.White, shape = RoundedCornerShape(8.dp))
                        .clickable { expandedDropdown = !expandedDropdown }
                )
                ExposedDropdownMenu(
                    expanded = expandedDropdown,
                    onDismissRequest = { expandedDropdown = false }
                ) {
                    horariosDisponibles.forEach { horario ->
                        DropdownMenuItem(
                            onClick = {
                                horarioSeleccionado = horario
                                expandedDropdown = false
                            },
                            text = { Text(horario) }
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(32.dp))

            Button(
                onClick = {
                    guardarPartidoEnFirebase(
                        canchaName.text,
                        ubicacion.text,
                        horarioSeleccionado
                    )
                },
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF009951)),
                shape = RoundedCornerShape(8.dp),
                modifier = Modifier.align(Alignment.CenterHorizontally)
            ) {
                Text(text = "CREAR PARTIDO", color = Color.White)
            }

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = { navController.navigate("menu") },
                modifier = Modifier.align(Alignment.CenterHorizontally)
            ) {
                Text(text = "Menu")
            }
        }
    }
}

// Función para generar horarios disponibles según la duración seleccionada
fun generarHorariosDisponibles(duracion: Double): List<String> {
    val horarios = mutableListOf<String>()
    val intervalo = (duracion * 60).toInt() // Convertir horas a minutos
    var horaInicio = 8 * 60 // 8:00 AM en minutos

    while (horaInicio + intervalo <= 22 * 60) { // Hasta las 10:00 PM
        val horaInicioStr = String.format("%02d:%02d", horaInicio / 60, horaInicio % 60)
        val horaFin = horaInicio + intervalo
        val horaFinStr = String.format("%02d:%02d", horaFin / 60, horaFin % 60)
        horarios.add("$horaInicioStr - $horaFinStr")
        horaInicio += intervalo
    }

    return horarios
}

// Función para guardar el partido en Firebase
fun guardarPartidoEnFirebase(canchaName: String, ubicacion: String, horario: String) {
    val db = FirebaseFirestore.getInstance()
    val partido = hashMapOf(
        "canchaName" to canchaName,
        "ubicacion" to ubicacion,
        "horario" to horario
    )
    db.collection("partidos")
        .add(partido)
        .addOnSuccessListener { /* éxito */ }
        .addOnFailureListener { /* error */ }
}
