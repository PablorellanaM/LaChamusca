package com.example.lachamusca.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.People
import androidx.compose.material.icons.filled.SportsSoccer
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Groups
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.rememberImagePainter

@Composable
fun MenuScreen(navController: NavController) {
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
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {
            // Imagen decorativa superior (Logo)
            Image(
                painter = rememberImagePainter("https://drive.google.com/uc?export=view&id=1xFfXfdH4sUk8c4Bp6ljRp7RxSN9b5kj7"),
                contentDescription = "Imagen superior",
                modifier = Modifier
                    .size(211.dp, 173.dp)
                    .padding(top = 16.dp),
                contentScale = ContentScale.FillBounds
            )

            Spacer(modifier = Modifier.height(32.dp))

            // Botones del menú
            MenuOption(
                text = "ENCONTRAR PARTIDO",
                icon = Icons.Default.SportsSoccer,
                onClick = { navController.navigate("findMatch") }
            )
            MenuOption(
                text = "CREAR PARTIDO",
                icon = Icons.Default.Add,
                onClick = { navController.navigate("createMatch") }
            )
            MenuOption(
                text = "PARTIDOS POPULARES",
                icon = Icons.Default.Star,
                onClick = { navController.navigate("popularMatches") }
            )
            MenuOption(
                text = "EQUIPOS",
                onClick = { navController.navigate("equipos") },
                icon = Icons.Default.Groups // Usa el ícono apropiado para "Equipos"
            )

            Spacer(modifier = Modifier.height(32.dp))

            // Botón de perfil en la parte inferior derecha
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(end = 16.dp, bottom = 16.dp),
                horizontalArrangement = Arrangement.End,
                verticalAlignment = Alignment.Bottom
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Image(
                        painter = rememberImagePainter("https://via.placeholder.com/75x110"),
                        contentDescription = "Imagen de perfil",
                        modifier = Modifier
                            .size(60.dp)
                            .clickable {
                                // Navega a la pantalla de perfil
                                navController.navigate("profile")
                            },
                        contentScale = ContentScale.Crop
                    )

                    // Texto "Perfil" debajo de la imagen
                    Text(
                        text = "Perfil",
                        style = TextStyle(
                            color = Color.White,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.W400
                        )
                    )
                }
            }
        }
    }
}

@Composable
fun MenuOption(text: String, icon: androidx.compose.ui.graphics.vector.ImageVector, onClick: () -> Unit) {
    Button(
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        colors = ButtonDefaults.buttonColors(containerColor = Color.White)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = Color.Black,
                modifier = Modifier.size(24.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = text,
                style = TextStyle(
                    color = Color.Black,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )
            )
        }
    }
}
