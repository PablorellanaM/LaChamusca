package com.example.lachamusca.view

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.background
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
import java.text.SimpleDateFormat
import java.util.*

@Composable
fun CrearPartidoScreen(navController: NavController, context: Context) {
    var canchaName by remember { mutableStateOf(TextFieldValue("")) }
    var cantidadParticipantes by remember { mutableStateOf(TextFieldValue("")) }
    var duracion by remember { mutableStateOf(60) } // Duración en minutos (1 hora)
    var horarioSeleccionado by remember { mutableStateOf("") }
    var ubicacion by remember { mutableStateOf(TextFieldValue("")) }

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

            // Campo de texto para el nombre de la cancha
            OutlinedTextField(
                value = canchaName,
                onValueChange = { canchaName = it },
                label = { Text("NOMBRE DE LA CANCHA") },
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.White, shape = RoundedCornerShape(8.dp))
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Campo de texto para la ubicación
            OutlinedTextField(
                value = ubicacion,
                onValueChange = { ubicacion = it },
                label = { Text("UBICACIÓN") },
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.White, shape = RoundedCornerShape(8.dp))
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Selección de duración
            Text(text = "Duración del Partido", color = Color.White)
            Row(
                horizontalArrangement = Arrangement.SpaceEvenly,
                modifier = Modifier.fillMaxWidth()
            ) {
                Button(
                    onClick = { duracion = 60 },
                    colors = ButtonDefaults.buttonColors(containerColor = if (duracion == 60) Color.Green else Color.Gray)
                ) {
                    Text("1 Hora")
                }
                Button(
                    onClick = { duracion = 90 },
                    colors = ButtonDefaults.buttonColors(containerColor = if (duracion == 90) Color.Green else Color.Gray)
                ) {
                    Text("1.5 Horas")
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Selector de horario
            Text(text = "Seleccione el Horario", color = Color.White)
            DropdownMenu(
                expanded = horariosDisponibles.isNotEmpty(),
                onDismissRequest = { /* No necesitamos cerrarlo aquí */ }
            ) {
                horariosDisponibles.forEach { horario ->
                    DropdownMenuItem(
                        onClick = { horarioSeleccionado = horario },
                        text = { Text(horario) }
                    )

                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Botón para crear el partido
            Button(
                onClick = {
                    guardarPartidoEnFirebase(canchaName.text, ubicacion.text, horarioSeleccionado, context)
                },
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF009951)),
                shape = RoundedCornerShape(8.dp),
                modifier = Modifier.align(Alignment.CenterHorizontally)
            ) {
                Text(text = "CREAR PARTIDO", color = Color.White)
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Botón de menú en la parte inferior
            Button(
                onClick = { navController.navigate("menu") },
                modifier = Modifier.align(Alignment.CenterHorizontally)
            ) {
                Text(text = "Menu")
            }
        }
    }
}

// Función para guardar el partido en Firebase
fun guardarPartidoEnFirebase(canchaName: String, ubicacion: String, horario: String, context: Context) {
    val db = FirebaseFirestore.getInstance()
    val partido = hashMapOf(
        "canchaName" to canchaName,
        "ubicacion" to ubicacion,
        "horario" to horario
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

// Función para generar los horarios disponibles según la duración
fun generarHorariosDisponibles(duracion: Int): List<String> {
    val horarios = mutableListOf<String>()
    val formato = SimpleDateFormat("HH:mm", Locale.getDefault())
    val calendario = Calendar.getInstance()
    calendario.set(Calendar.HOUR_OF_DAY, 8) // Horario de inicio: 8:00 AM
    calendario.set(Calendar.MINUTE, 0)

    while (calendario.get(Calendar.HOUR_OF_DAY) < 22) { // Horario de cierre: 10:00 PM
        val horaInicio = formato.format(calendario.time)
        calendario.add(Calendar.MINUTE, duracion)
        val horaFin = formato.format(calendario.time)
        horarios.add("$horaInicio - $horaFin")
    }

    return horarios
}
