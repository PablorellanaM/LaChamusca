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
import com.google.firebase.auth.FirebaseAuth

@Composable
fun LoginScreen(navController: NavController) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var loginError by remember { mutableStateOf<String?>(null) }

    val auth = FirebaseAuth.getInstance()

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
            // Logo superior
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

            // Mostrar error si hay problemas con el inicio de sesión
            loginError?.let {
                Text(text = it, color = Color.Red)
            }

            // Botón "Iniciar Sesión"
            Button(
                onClick = {
                    if (email.isNotEmpty() && password.isNotEmpty()) {
                        // Iniciar sesión con Firebase
                        auth.signInWithEmailAndPassword(email, password)
                            .addOnCompleteListener { task ->
                                if (task.isSuccessful) {
                                    navController.navigate("menu")  // Navegar al menú si el login es exitoso
                                } else {
                                    loginError = "Error al iniciar sesión: ${task.exception?.message}"
                                }
                            }
                    } else {
                        loginError = "Por favor, rellene todos los campos."
                    }
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

            // Botón "Registrarse" para navegar a la pantalla de registro
            Button(
                onClick = {
                    navController.navigate("registerScreen")  // Navegar a la pantalla de registro
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Registrarse", color = Color.Black)
            }
        }
    }
}
