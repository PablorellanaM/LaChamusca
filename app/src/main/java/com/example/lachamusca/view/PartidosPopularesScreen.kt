package com.example.lachamusca.view

import android.content.Context
import android.util.Log
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
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.google.firebase.firestore.FirebaseFirestore

data class Partido(
    val canchaName: String = "Desconocido",
    val horario: String = "Fecha no disponible",
    val ubicacion: String = "Desconocida"
)

@Composable
fun PartidosPopularesScreen(navController: NavController) {
    val db = FirebaseFirestore.getInstance()
    var partidosList by remember { mutableStateOf<List<Partido>>(emptyList()) }

    // Obtener los datos de Firestore al inicializar la pantalla
    LaunchedEffect(Unit) {
        db.collection("partidos")
            .get()
            .addOnSuccessListener { documents ->
                val partidos = mutableListOf<Partido>()
                for (document in documents) {
                    val canchaName = document.getString("canchaName") ?: "Desconocido"
                    val horario = document.getString("horario") ?: "Fecha no disponible"
                    val ubicacion = document.getString("ubicacion") ?: "Desconocida"
                    partidos.add(Partido(canchaName, horario, ubicacion))
                }
                partidosList = partidos
            }
            .addOnFailureListener { exception ->
                Log.e("FirestoreError", "Error al obtener documentos: ", exception)
            }
    }

    // Diseño de la pantalla
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
                text = "Partidos Populares",
                style = TextStyle(color = Color.White, fontSize = 25.sp),
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )

            Spacer(modifier = Modifier.height(16.dp))

            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(partidosList) { partido ->
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp),
                        colors = CardDefaults.cardColors(containerColor = Color(0xFFDDDDDD))
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Text(text = "Nombre: ${partido.canchaName}", color = Color.Black)
                            Text(text = "Dirección: ${partido.ubicacion}", color = Color.Gray)
                            Text(text = "Horario: ${partido.horario}", color = Color.Gray)
                        }
                    }
                }
            }
        }
    }
}
