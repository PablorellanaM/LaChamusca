package com.example.lachamusca.view

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.compose.ui.graphics.Brush
import androidx.compose.foundation.background
import androidx.compose.ui.unit.sp
import androidx.compose.ui.text.font.FontFamily

@Composable
fun EncontrarPartidoScreen(navController: NavController) {
    val partidos = remember {
        mutableStateListOf(
            Partido("Partido en Futeca Cayala", "De 4:00 p.m a 5:00 p.m", 9, 10),
            Partido("Partido en Campo Marte", "De 2:00 p.m a 3:00 p.m", 5, 10),
            Partido("Partido La Cantera Atlantico", "De 5:00 p.m a 6:00 p.m", 7, 10),
            Partido("Partido en Campos del Roosevelt", "De 6:00 p.m a 7:00 p.m", 8, 10),
            Partido("Partido en BRIO FUTBOL", "De 5:00 pm a 6:00 p.m.", 6, 10)
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
                text = "Encontrar Partido",
                style = TextStyle(color = Color.White, fontSize = 25.sp, fontFamily = FontFamily.Default, fontWeight = FontWeight.W400),
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "Partidos Disponibles",
                style = TextStyle(fontWeight = FontWeight.Bold, fontSize = 18.sp, color = Color.Black)
            )

            Spacer(modifier = Modifier.height(8.dp))

            LazyColumn(modifier = Modifier.fillMaxWidth()) {
                items(partidos) { partido ->
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
                            Text(text = partido.nombre, fontWeight = FontWeight.W400)
                            Text(text = partido.horario, style = TextStyle(color = Color.Gray, fontSize = 14.sp))
                            Text(text = "${partido.participantes}/${partido.maxParticipantes}")
                        }
                        Button(
                            onClick = {
                                if (partido.participantes < partido.maxParticipantes) {
                                    partido.participantes++
                                }
                            }
                        ) {
                            Text(text = "Unirse")
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

data class Partido(
    val nombre: String,
    val horario: String,
    var participantes: Int,
    val maxParticipantes: Int
)
