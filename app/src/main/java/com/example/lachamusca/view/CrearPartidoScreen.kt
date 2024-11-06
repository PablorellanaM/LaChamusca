package com.example.lachamusca.view

import android.content.Context
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.google.firebase.firestore.FirebaseFirestore
import java.text.SimpleDateFormat
import java.util.*

@Composable
fun CrearPartidoScreen(navController: NavController, context: Context) {
    var canchaName by remember { mutableStateOf("") }
    var ubicacion by remember { mutableStateOf("") }
    var duracion by remember { mutableStateOf(60) } // Duración en minutos (60 = 1 hora, 90 = 1.5 horas)
    var horarioSeleccionado by remember { mutableStateOf("") }
    val horarioOptions = remember { mutableStateOf(listOf<String>()) }

    // Generar listado de horarios al cambiar la duración
    LaunchedEffect(duracion) {
        horarioOptions.value = generarHorariosDisponibles(duracion)
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

            // Campo para el nombre de la cancha
            OutlinedTextField(
                value = canchaName,
                onValueChange = { canchaName = it },
                label = { Text("NOMBRE DE LA CANCHA") },
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.White, shape = RoundedCornerShape(8.dp))
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Campo para la ubicación
            OutlinedTextField(
                value = ubicacion,
                onValueChange = { ubicacion = it },
                label = { Text("UBICACIÓN") },
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.White, shape = RoundedCornerShape(8.dp))
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Selección de duración del partido
            Text(text = "Duración del Partido", color = Color.White, fontSize = 18.sp)
            Row {
                Button(
                    onClick = { duracion = 60 },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = if (duracion == 60) Color(0xFF009951) else Color.Gray
                    ),
                    modifier = Modifier.padding(4.dp)
                ) {
                    Text(text = "1 Hora", color = Color.White)
                }

                Button(
                    onClick = { duracion = 90 },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = if (duracion == 90) Color(0xFF009951) else Color.Gray
                    ),
                    modifier = Modifier.padding(4.dp)
                ) {
                    Text(text = "1.5 Horas", color = Color.White)
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Selección de horario basado en la duración
            Text(text = "Seleccione el Horario", color = Color.White, fontSize = 18.sp)
            horarioOptions.value.forEach { horario ->
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp)
                ) {
                    RadioButton(
                        selected = horarioSeleccionado == horario,
                        onClick = { horarioSeleccionado = horario }
                    )
                    Text(text = horario, color = Color.White, fontSize = 16.sp)
                }
            }

            Spacer(modifier = Modifier.height(32.dp))

            // Botón para crear el partido
            Button(
                onClick = {
                    guardarPartidoEnFirebase(canchaName, ubicacion, horarioSeleccionado, context)
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

// Función para generar horarios disponibles
fun generarHorariosDisponibles(duracion: Int): List<String> {
    val horarios = mutableListOf<String>()
    val formatoHora = SimpleDateFormat("HH:mm", Locale.getDefault())
    var horaInicial = Calendar.getInstance().apply {
        set(Calendar.HOUR_OF_DAY, 9) // Hora de inicio
        set(Calendar.MINUTE, 0)
    }

    val horaFinal = Calendar.getInstance().apply {
        set(Calendar.HOUR_OF_DAY, 22) // Hora de finalización
        set(Calendar.MINUTE, 0)
    }

    while (horaInicial.before(horaFinal)) {
        val inicio = formatoHora.format(horaInicial.time)
        horaInicial.add(Calendar.MINUTE, duracion)
        val fin = formatoHora.format(horaInicial.time)
        horarios.add("$inicio - $fin")
    }
    return horarios
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
            // Muestra un mensaje de éxito
        }
        .addOnFailureListener {
            // Manejo del error
        }
}
