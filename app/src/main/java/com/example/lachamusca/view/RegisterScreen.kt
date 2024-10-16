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
import com.google.firebase.auth.FirebaseAuth

@Composable
fun RegisterScreen(navController: NavController) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var registerError by remember { mutableStateOf<String?>(null) }

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
            Spacer(modifier = Modifier.height(16.dp))

            // Texto "Introduzca la Siguiente Información"
            Text(
                text = "Introduzca la Siguiente Información",
                style = TextStyle(
                    color = Color.White,
                    fontSize = 25.sp,
                    fontWeight = FontWeight.W400
                )
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Campo de texto para Nombre de Usuario (Correo Electrónico)
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

            // Mostrar error si hay problemas con el registro
            registerError?.let {
                Text(text = it, color = Color.Red)
            }

            // Botón "Crear Usuario"
            Button(
                onClick = {
                    auth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener { task ->
                            if (task.isSuccessful) {
                                navController.navigate("menu")  // Navegar al menú si el registro es exitoso
                            } else {
                                registerError = "Error al crear usuario: ${task.exception?.message}"
                            }
                        }
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Crear Usuario", color = Color.Black)
            }

            Spacer(modifier = Modifier.height(32.dp))

            // Texto "Regístrate con"
            Text(
                text = "Regístrate con",
                style = TextStyle(
                    color = Color.White,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.W400
                )
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Íconos de redes sociales
            Row(
                horizontalArrangement = Arrangement.SpaceEvenly,
                modifier = Modifier.fillMaxWidth()
            ) {
                SocialIcon(imageUrl = "https://drive.google.com/uc?export=view&id=1Sp-C9BH6Z3qjyWxjv9bmiEupHWUGQZIF", onClick = {
                    // Acción de registro con Facebook
                    println("Registro con Facebook")
                })
                SocialIcon(imageUrl = "https://drive.google.com/uc?export=view&id=1DcFglvTwiKG_7DJ4qU9K9f1ebGs-2ShG", onClick = {
                    // Acción de registro con Google
                    println("Registro con Google")
                })
                SocialIcon(imageUrl = "https://drive.google.com/uc?export=view&id=14b_qojH_QipLsK9LCEukZ12RO14uFivQ", onClick = {
                    // Acción de registro con Apple
                    println("Registro con Apple")
                })
                SocialIcon(imageUrl = "https://drive.google.com/uc?export=view&id=1SWT8khOMLvzmsH18a27FHt5Voe17JZlq", onClick = {
                    // Acción de registro con Microsoft
                    println("Registro con Microsoft")
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
