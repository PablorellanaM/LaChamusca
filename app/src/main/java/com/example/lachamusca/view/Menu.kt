package com.example.lachamusca.view

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

@Composable
fun MenuScreen(navController: NavController) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    colors = listOf(Color(0xFFA10202), Color(0xFF351111)) // Fondo con degradado
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
            // Título del menú
            Text(
                text = "Menú Principal",
                style = TextStyle(
                    fontSize = 28.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                ),
                modifier = Modifier.padding(top = 16.dp)
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Botones del menú organizados en dos filas
            Column(
                verticalArrangement = Arrangement.spacedBy(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.fillMaxWidth()
            ) {
                MenuButton(
                    text = "Encontrar Partido",
                    icon = Icons.Default.Search,
                    onClick = { navController.navigate("findMatch") }
                )
                MenuButton(
                    text = "Crear Partido",
                    icon = Icons.Default.AddCircle,
                    onClick = { navController.navigate("createMatch") }
                )
                MenuButton(
                    text = "Partidos Populares",
                    icon = Icons.Default.Star,
                    onClick = { navController.navigate("popularMatches") }
                )
                MenuButton(
                    text = "Equipos Disponibles",
                    icon = Icons.Default.People, // Icono alternativo para equipos
                    onClick = { navController.navigate("teamsToJoin") }
                )
            }

            // Botón de perfil en la parte inferior derecha
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp, end = 16.dp),
                horizontalArrangement = Arrangement.End
            ) {
                MenuButton(
                    text = "Perfil",
                    icon = Icons.Default.Person,
                    onClick = { navController.navigate("profile") }
                )
            }
        }
    }
}

// Botón reutilizable con texto e ícono
@Composable
fun MenuButton(text: String, icon: androidx.compose.ui.graphics.vector.ImageVector, onClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color(0xFFD9D9D9)) // Fondo claro para el botón
            .clickable(onClick = onClick)
            .padding(16.dp)
            .height(60.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = Color.Black,
            modifier = Modifier.size(24.dp)
        )
        Spacer(modifier = Modifier.width(16.dp))
        Text(
            text = text,
            style = TextStyle(
                color = Color.Black,
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium
            )
        )
    }
}
