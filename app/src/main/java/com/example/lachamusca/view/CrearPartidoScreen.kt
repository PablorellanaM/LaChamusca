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
import com.example.lachamusca.repository.Partido
import com.example.lachamusca.repository.PartidoRepository
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.GeoPoint
import kotlinx.coroutines.launch

@Composable
fun CrearPartidoScreen(navController: NavController, context: Context) {
    var canchaName by remember { mutableStateOf(TextFieldValue("")) }
    var cantidadParticipantes by remember { mutableStateOf(TextFieldValue("")) }
    var horario by remember { mutableStateOf(TextFieldValue("")) }
    var ubicacion by remember { mutableStateOf(TextFieldValue("")) }

    // Inicializa Firestore y PartidoRepository
    val db = FirebaseFirestore.getInstance()
    val partidoRepository = PartidoRepository(db)

    val coroutineScope = rememberCoroutineScope()

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
                value = horario,
                onValueChange = { horario = it },
                label = { Text("HORARIO") },
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

            Spacer(modifier = Modifier.height(32.dp))

            Button(
                onClick = {
                    if (canchaName.text.isNotEmpty() && cantidadParticipantes.text.isNotEmpty() && horario.text.isNotEmpty() && ubicacion.text.isNotEmpty()) {
                        val partido = Partido(
                            nombre = canchaName.text,
                            ubicacion = GeoPoint(14.6349, -90.5069), // Ejemplo de ubicación, cambiar a real si tienes el GPS
                            descripcion = "Participantes: ${cantidadParticipantes.text}, Horario: ${horario.text}",
                            fecha = horario.text
                        )
                        coroutineScope.launch {
                            partidoRepository.agregarPartido(
                                partido,
                                onSuccess = {
                                    Toast.makeText(context, "Partido creado exitosamente", Toast.LENGTH_SHORT).show()
                                    navController.navigate("menu")
                                },
                                onFailure = { exception ->
                                    Toast.makeText(context, "Error al crear el partido: ${exception.message}", Toast.LENGTH_LONG).show()
                                }
                            )
                        }
                    } else {
                        Toast.makeText(context, "Completa todos los campos", Toast.LENGTH_SHORT).show()
                    }
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
