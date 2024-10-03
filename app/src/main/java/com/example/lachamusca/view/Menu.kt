package com.example.lachamusca.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
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
            // Imagen de fondo o decorativa superior (Logo)
            Image(
                painter = rememberImagePainter("https://via.placeholder.com/211x173"),
                contentDescription = "Imagen superior",
                modifier = Modifier
                    .size(211.dp, 173.dp)
                    .offset(x = (-52).dp, y = (-16).dp), // Reemplaza padding por offset
                contentScale = ContentScale.FillBounds
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Texto del menú (Ejemplo de encabezado)
            Text(
                text = "MENU",
                style = TextStyle(
                    color = Color.White,
                    fontSize = 40.sp,
                    fontWeight = FontWeight.Bold
                )
            )

            Spacer(modifier = Modifier.height(32.dp))

            // Botones de las opciones de menú
            MenuOption(text = "ENCONTRAR PARTIDO", onClick = { navController.navigate("findMatch") })
            MenuOption(text = "CREAR PARTIDO", onClick = { navController.navigate("createMatch") })
            MenuOption(text = "PARTIDOS POPULARES", onClick = { navController.navigate("popularMatches") })
            MenuOption(text = "EQUIPOS A LOS QUE PUEDES UNIRTE", onClick = { navController.navigate("teamsToJoin") })

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
fun MenuOption(text: String, onClick: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .background(Color(0xFFD9D9D9))
            .clickable(onClick = onClick)
            .height(60.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = text,
            style = TextStyle(
                color = Color.Black,
                fontSize = 15.sp,
                fontFamily = androidx.compose.ui.text.font.FontFamily.Default,
                fontWeight = FontWeight.W400
            )
        )
    }
}

