package com.example.lachamusca.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter
import androidx.navigation.NavController
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.sp
import androidx.compose.foundation.text.BasicTextField

@Composable
fun LoginScreen(navController: NavController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        // Imagen superior
        Image(
            painter = rememberImagePainter("https://via.placeholder.com/386x384"),
            contentDescription = "Background image",
            modifier = Modifier
                .size(386.dp, 384.dp),
            contentScale = ContentScale.FillBounds
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Texto principal
        Text(
            text = "Inicia sesión o Regístrate",
            style = TextStyle(
                color = Color.White,
                fontSize = 25.sp,
                fontFamily = androidx.compose.ui.text.font.FontFamily.Serif,
                fontWeight = FontWeight.W400
            ),
            modifier = Modifier.padding(8.dp),
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Campos de entrada (Correo Electrónico)
        BasicTextField(
            value = "",
            onValueChange = {},
            modifier = Modifier
                .width(304.dp)
                .height(32.dp)
                .background(Color.White, RoundedCornerShape(8.dp))
                .padding(8.dp)
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Campos de entrada (Contraseña)
        BasicTextField(
            value = "",
            onValueChange = {},
            modifier = Modifier
                .width(304.dp)
                .height(32.dp)
                .background(Color.White, RoundedCornerShape(8.dp))
                .padding(8.dp)
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Botón para iniciar sesión
        Button(
            onClick = { /* Aquí iría la lógica para iniciar sesión */ },
            modifier = Modifier
                .width(128.dp)
                .height(48.dp),
            colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFFE0B6B9))
        ) {
            Text(text = "Iniciar Sesión", color = Color.Black)
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Texto "o"
        Text(
            text = "o",
            style = TextStyle(
                color = Color.White,
                fontSize = 25.sp,
                fontWeight = FontWeight.W400
            )
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Texto de registro
        Text(
            text = "Regístrate con",
            style = TextStyle(
                color = Color.White,
                fontSize = 25.sp,
                fontWeight = FontWeight.W400
            )
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Íconos para redes sociales
        Row(
            horizontalArrangement = Arrangement.SpaceEvenly,
            modifier = Modifier.fillMaxWidth()
        ) {
            Image(
                painter = rememberImagePainter("https://via.placeholder.com/38x38"),
                contentDescription = "Social Icon 1",
                modifier = Modifier.size(38.dp)
            )
            Image(
                painter = rememberImagePainter("https://via.placeholder.com/43x45"),
                contentDescription = "Social Icon 2",
                modifier = Modifier.size(43.dp)
            )
            Image(
                painter = rememberImagePainter("https://via.placeholder.com/37x45"),
                contentDescription = "Social Icon 3",
                modifier = Modifier.size(37.dp)
            )
        }
    }
}
