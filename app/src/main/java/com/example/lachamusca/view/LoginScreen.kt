package com.example.lachamusca.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.rememberImagePainter

@Composable
fun LoginScreen(navController: NavController) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFA03939)),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {
            // Imagen decorativa en la parte superior
            Image(
                painter = rememberImagePainter("https://via.placeholder.com/386x384"),
                contentDescription = "Logo superior",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp),
                contentScale = ContentScale.FillBounds
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Texto "Inicia sesión o Regístrate"
            Text(
                text = "Inicia sesión o Regístrate",
                style = TextStyle(
                    color = Color.White,
                    fontSize = 25.sp,
                    fontWeight = FontWeight.W400
                )
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Campo de texto para Correo Electrónico
            TextField(
                value = email,
                onValueChange = { email = it },
                label = { Text("Nombre de Usuario") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Campo de texto para Contraseña
            TextField(
                value = password,
                onValueChange = { password = it },
                label = { Text("Contraseña") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                visualTransformation = PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password)
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Botón "Iniciar Sesión"
            Button(
                onClick = {
                    // Navegar al menú después de iniciar sesión
                    navController.navigate("menu")
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Iniciar Sesión", color = Color.Black)
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

            // Texto "Regístrate con"
            Text(
                text = "Regístrate con",
                style = TextStyle(
                    color = Color.White,
                    fontSize = 25.sp,
                    fontWeight = FontWeight.W400
                )
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Íconos de redes sociales
            Row(
                horizontalArrangement = Arrangement.SpaceEvenly,
                modifier = Modifier.fillMaxWidth()
            ) {
                SocialIcon(imageUrl = "https://via.placeholder.com/38x38", onClick = {
                    // Acción de inicio con Facebook
                    println("Inicio con Facebook")
                })
                SocialIcon(imageUrl = "https://via.placeholder.com/43x45", onClick = {
                    // Acción de inicio con Google
                    println("Inicio con Google")
                })
                SocialIcon(imageUrl = "https://via.placeholder.com/37x45", onClick = {
                    // Acción de inicio con Apple
                    println("Inicio con Apple")
                })
                SocialIcon(imageUrl = "https://via.placeholder.com/37x88", onClick = {
                    // Acción de inicio con Microsoft
                    println("Inicio con Microsoft")
                })
            }
        }
    }
}

@Composable
fun SocialIcon(imageUrl: String, onClick: () -> Unit) {
    Image(
        painter = rememberImagePainter(imageUrl),
        contentDescription = "Icono de red social",
        modifier = Modifier
            .size(50.dp)
            .padding(8.dp)
            .clickable { onClick() },
        contentScale = ContentScale.FillBounds
    )
}
