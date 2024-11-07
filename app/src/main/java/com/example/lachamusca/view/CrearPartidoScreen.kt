package com.example.lachamusca.view

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
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
import androidx.compose.ui.text.input.TextFieldValue
import androidx.navigation.NavController
import com.google.firebase.firestore.FirebaseFirestore
import java.text.SimpleDateFormat
import java.util.*

@Composable
fun CrearPartidoScreen(navController: NavController, context: Context) {
    var canchaName by remember { mutableStateOf(TextFieldValue("")) }
    var cantidadParticipantes by remember { mutableStateOf(TextFieldValue("")) }
    var ubicacion by remember { mutableStateOf(TextFieldValue("")) }
    var duracion by remember { mutableStateOf(1.0) }
    var horariosDisponibles by remember { mutableStateOf(listOf<String>()) }
    var horarioSeleccionado by remember { mutableStateOf("") }
    var expanded by remember { mutableStateOf(false) }

    // Actualizar la lista de horarios cuando la duración cambia
    LaunchedEffect(duracion) {
        horariosDisponibles = generarHorariosDisponibles(duracion)
    }

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
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                Button(
                    onClick = { duracion = 1.0 },
                    colors = ButtonDefaults.buttonColors(containerColor = if (duracion == 1.0) Color(0xFF009951) else Color.Gray)
                ) {
                    Text(text = "1 Hora")
                }

                Button(
                    onClick = { duracion = 1.5 },
                    colors = ButtonDefaults.buttonColors(containerColor = if (duracion == 1.5) Color(0xFF009951) else Color.Gray)
                ) {
                    Text(text = "1.5 Horas")
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Dropdown para seleccionar horario
            OutlinedTextField(
                value = horarioSeleccionado,
                onValueChange = { },
                readOnly = true,
                label = { Text("Horario", color = Color.White) },
                trailingIcon = {
                    Icon(
                        Icons.Default.ArrowDropDown,
                        contentDescription = null,
                        modifier = Modifier.clickable { expanded = true }
                    )
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color(0xFF6200EE), shape = RoundedCornerShape(8.dp)) // Fondo morado
                    .clickable { expanded = true }
            )

            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false }
            ) {
                horariosDisponibles.forEach { horario ->
                    DropdownMenuItem(
                        text = { Text(horario) },
                        onClick = {
                            horarioSeleccionado = horario
                            expanded = false
                        }
                    )
                }
            }

            Spacer(modifier = Modifier.height(32.dp))

            Button(
                onClick = {
                    guardarPartidoEnFirebase(
                        canchaName.text,
                        ubicacion.text,
                        horarioSeleccionado,
                        context
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

// Función para generar horarios disponibles
fun generarHorariosDisponibles(duracion: Double): List<String> {
    val horarios = mutableListOf<String>()
    val formatoHora = SimpleDateFormat("HH:mm", Locale.getDefault())
    val calendario = Calendar.getInstance()

    calendario.set(Calendar.HOUR_OF_DAY, 8)
    calendario.set(Calendar.MINUTE, 0)

    while (calendario.get(Calendar.HOUR_OF_DAY) < 22) {
        val inicio = formatoHora.format(calendario.time)
        calendario.add(Calendar.MINUTE, (duracion * 60).toInt())
        val fin = formatoHora.format(calendario.time)
        horarios.add("$inicio - $fin")
    }
    return horarios
}

// Función para guardar el partido en Firestore
fun guardarPartidoEnFirebase(canchaName: String, ubicacion: String, horarioSeleccionado: String, context: Context) {
    val db = FirebaseFirestore.getInstance()
    val partido = hashMapOf(
        "canchaName" to canchaName,
        "ubicacion" to ubicacion,
        "horario" to horarioSeleccionado
    )

    db.collection("partidos")
        .add(partido)
        .addOnSuccessListener {
            Toast.makeText(context, "Partido creado exitosamente", Toast.LENGTH_SHORT).show()
        }
        .addOnFailureListener { e ->
            Toast.makeText(context, "Error al crear el partido: ${e.message}", Toast.LENGTH_SHORT).show()
        }
}
