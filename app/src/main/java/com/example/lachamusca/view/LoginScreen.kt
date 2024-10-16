package com.example.lachamusca.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
            // Imagen decorativa en la parte superior (con el link de Google Drive)
            Image(
                painter = rememberImagePainter("https://drive.google.com/uc?export=view&id=19k0Od8DGxXuoBRzyDgE6bqwf4jSlsSqN"),
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
                label = { Text("Correo Electrónico") },
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

            // Texto "O"
            Text(
                text = "O",
                style = TextStyle(
                    color = Color.White,
                    fontSize = 25.sp,
                    fontWeight = FontWeight.W400
                )
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Botón "Registrarte"
            Button(
                onClick = {
                    // Navegar a la pantalla de registro
                    navController.navigate("register")
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Registrarte", color = Color.Black)
            }
        }
    }
}
