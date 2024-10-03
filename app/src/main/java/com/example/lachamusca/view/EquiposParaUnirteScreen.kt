package com.example.lachamusca.view

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

data class Equipo(
    val nombre: String,
    val rating: String
)

@Composable
fun EquiposParaUnirteScreen(navController: NavController) {
    // Lista de equipos disponibles
    val equipos = remember {
        mutableStateListOf(
            Equipo("Guatemala FC", "Rating Mínimo 4 Estrellas"),
            Equipo("Garra Charrua FC", "Rating Mínimo 3 Estrellas"),
            Equipo("Quetzaltecos FC", "Rating Mínimo 4 Estrellas"),
            Equipo("Messilovers FC", "Rating Mínimo 2 Estrellas"),
            Equipo("Bicholovers FC", "Rating Mínimo 4 Estrellas")
        )
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
                text = "Equipos para Unirte",
                style = TextStyle(color = Color.White, fontSize = 25.sp),
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "EQUIPOS",
                style = TextStyle(fontWeight = FontWeight.Bold, fontSize = 18.sp, color = Color.Black)
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Lista de equipos usando LazyColumn
            LazyColumn(modifier = Modifier.fillMaxWidth()) {
                items(equipos) { equipo ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp)
                            .border(BorderStroke(1.dp, Color.Gray), RoundedCornerShape(8.dp))
                            .padding(8.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Column {
                            Text(text = equipo.nombre, fontWeight = FontWeight.W400)
                            Text(text = equipo.rating, style = TextStyle(color = Color.Gray, fontSize = 14.sp))
                        }
                        Button(
                            onClick = { /* Acción al unirse */ },
                            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF009951)),
                            shape = RoundedCornerShape(8.dp)
                        ) {
                            Text(text = "Unirse", color = Color.White)
                        }
                    }
                }
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
