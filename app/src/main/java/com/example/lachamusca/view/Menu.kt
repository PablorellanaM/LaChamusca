package com.example.lachamusca.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberImagePainter

@Composable
fun MenuScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        // Imagen 1
        Image(
            painter = rememberImagePainter("https://via.placeholder.com/211x173"),
            contentDescription = "Imagen 1",
            modifier = Modifier
                .size(211.dp, 173.dp),
            contentScale = ContentScale.FillBounds
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Imagen 2
        Image(
            painter = rememberImagePainter("https://via.placeholder.com/500x500"),
            contentDescription = "Imagen 2",
            modifier = Modifier
                .size(500.dp, 500.dp),
            contentScale = ContentScale.FillBounds
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Imagen 3
        Image(
            painter = rememberImagePainter("https://via.placeholder.com/75x110"),
            contentDescription = "Imagen 3",
            modifier = Modifier
                .size(75.dp, 110.dp),
            contentScale = ContentScale.FillBounds
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Texto Perfil
        Text(
            text = "Perfil",
            style = androidx.compose.ui.text.TextStyle(
                color = Color.White,
                fontSize = 25.sp,
                fontWeight = FontWeight.W400
            ),
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Cuadros grises
        Box(
            modifier = Modifier
                .size(217.dp, 60.dp)
                .background(Color(0xFFD9D9D9), RoundedCornerShape(8.dp))
        )

        Spacer(modifier = Modifier.height(8.dp))

        Box(
            modifier = Modifier
                .size(217.dp, 60.dp)
                .background(Color(0xFFD9D9D9), RoundedCornerShape(8.dp))
        )

        Spacer(modifier = Modifier.height(8.dp))

        Box(
            modifier = Modifier
                .size(217.dp, 60.dp)
                .background(Color(0xFFD9D9D9), RoundedCornerShape(8.dp))
        )

        Spacer(modifier = Modifier.height(8.dp))

        Box(
            modifier = Modifier
                .size(217.dp, 60.dp)
                .background(Color(0xFFD9D9D9), RoundedCornerShape(8.dp))
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Texto "Encontrar Partido"
        Text(
            text = "ENCONTRAR PARTIDO",
            style = androidx.compose.ui.text.TextStyle(
                color = Color.Black,
                fontSize = 15.sp,
                fontWeight = FontWeight.W400
            ),
            modifier = Modifier.width(192.dp).height(59.dp),
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(8.dp))

        // Texto "Crear Partido"
        Text(
            text = "CREAR PARTIDO",
            style = androidx.compose.ui.text.TextStyle(
                color = Color.Black,
                fontSize = 15.sp,
                fontWeight = FontWeight.W400
            ),
            modifier = Modifier.width(192.dp).height(59.dp),
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(8.dp))

        // Texto "Partidos Populares"
        Text(
            text = "PARTIDOS POPULARES",
            style = androidx.compose.ui.text.TextStyle(
                color = Color.Black,
                fontSize = 15.sp,
                fontWeight = FontWeight.W400
            ),
            modifier = Modifier.width(192.dp).height(59.dp),
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(8.dp))

        // Texto "Equipos a los que puedes unirte"
        Text(
            text = "EQUIPOS A LOS QUE PUEDES UNIRTE",
            style = androidx.compose.ui.text.TextStyle(
                color = Color.Black,
                fontSize = 15.sp,
                fontWeight = FontWeight.W400
            ),
            modifier = Modifier.width(192.dp).height(59.dp),
            textAlign = TextAlign.Center
        )
    }
}
