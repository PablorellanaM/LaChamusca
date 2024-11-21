import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
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
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import androidx.compose.foundation.clickable
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CrearEquipoScreen(navController: NavHostController) {
    var nombreEquipo by remember { mutableStateOf("") }
    var posicionSolicitada by remember { mutableStateOf("") }
    val opcionesPosiciones = listOf("Arquero", "Defensa", "Mediocampista", "Delantero", "Todas")

    // Instancia de Firebase Firestore
    val db = Firebase.firestore

    // Estado para mostrar mensajes de éxito o error
    var mensaje by remember { mutableStateOf("") }

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
                .padding(16.dp)
                .verticalScroll(rememberScrollState()), // Habilitar scroll
            horizontalAlignment = Alignment.CenterHorizontally
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
                text = "Selecciona una posición solicitada:",
                color = Color.White
            )

            Spacer(modifier = Modifier.height(8.dp))

            opcionesPosiciones.forEach { opcion ->
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { posicionSolicitada = opcion }
                        .padding(vertical = 8.dp)
                ) {
                    RadioButton(
                        selected = posicionSolicitada == opcion,
                        onClick = { posicionSolicitada = opcion },
                        colors = RadioButtonDefaults.colors(selectedColor = Color.White)
                    )
                    Text(text = opcion, color = Color.White)
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Botón para guardar equipo
            Button(
                onClick = {
                    if (nombreEquipo.isNotEmpty() && posicionSolicitada.isNotEmpty()) {
                        // Lógica para guardar el equipo
                        db.collection("equipos")
                            .add(
                                mapOf(
                                    "nombre" to nombreEquipo,
                                    "posicionSolicitada" to posicionSolicitada, // Guardar posición
                                    "miembros" to emptyList<String>() // Miembros iniciales vacíos
                                )
                            )
                            .addOnSuccessListener {
                                mensaje = "Equipo guardado exitosamente."
                                nombreEquipo = ""
                                posicionSolicitada = ""
                            }
                            .addOnFailureListener { e ->
                                mensaje = "Error al guardar equipo: ${e.message}"
                            }
                    } else {
                        mensaje = "Por favor, completa todos los campos."
                    }
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

            Spacer(modifier = Modifier.height(16.dp))

            // Mostrar mensaje de éxito o error
            if (mensaje.isNotEmpty()) {
                Text(text = mensaje, color = Color.Yellow, fontSize = 16.sp)
            }
        }
    }
}
