package com.example.lachamusca.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
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
import com.google.firebase.firestore.FirebaseFirestore

@Composable
fun RegisterScreen(navController: NavController) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var firstName by remember { mutableStateOf("") }
    var lastName by remember { mutableStateOf("") }
    var position by remember { mutableStateOf("") }
    var expanded by remember { mutableStateOf(false) }
    var registerError by remember { mutableStateOf<String?>(null) }

    val auth = FirebaseAuth.getInstance()
    val firestore = FirebaseFirestore.getInstance()

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

            Text(
                text = "Introduzca la Siguiente Información",
                style = TextStyle(
                    color = Color.White,
                    fontSize = 25.sp,
                    fontWeight = FontWeight.W400
                )
            )

            Spacer(modifier = Modifier.height(16.dp))

            TextField(
                value = firstName,
                onValueChange = { firstName = it },
                label = { Text("Nombre") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )

            Spacer(modifier = Modifier.height(16.dp))

            TextField(
                value = lastName,
                onValueChange = { lastName = it },
                label = { Text("Apellido") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )

            Spacer(modifier = Modifier.height(16.dp))

            TextField(
                value = email,
                onValueChange = { email = it },
                label = { Text("Correo Electrónico") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )

            Spacer(modifier = Modifier.height(16.dp))

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

            // Dropdown para seleccionar la posición
            Text(
                text = "Seleccione su posición:",
                style = TextStyle(
                    color = Color.White,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.W400
                )
            )

            Spacer(modifier = Modifier.height(8.dp))

            Box {
                OutlinedButton(
                    onClick = { expanded = true },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(text = if (position.isEmpty()) "Seleccionar posición" else position)
                }

                DropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false }
                ) {
                    val positions = listOf("Portero", "Defensa", "Mediocampista", "Delantero")
                    positions.forEach { pos ->
                        DropdownMenuItem(onClick = {
                            position = pos
                            expanded = false
                        }) {
                            Text(text = pos)
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            registerError?.let {
                Text(text = it, color = Color.Red)
            }

            Button(
                onClick = {
                    if (email.isNotEmpty() && password.isNotEmpty() && position.isNotEmpty()) {
                        auth.createUserWithEmailAndPassword(email, password)
                            .addOnCompleteListener { task ->
                                if (task.isSuccessful) {
                                    val userId = task.result?.user?.uid
                                    if (userId != null) {
                                        val userMap = mapOf(
                                            "firstName" to firstName,
                                            "lastName" to lastName,
                                            "email" to email,
                                            "position" to position
                                        )
                                        firestore.collection("users").document(userId)
                                            .set(userMap)
                                            .addOnSuccessListener {
                                                navController.navigate("menu")
                                            }
                                            .addOnFailureListener { exception ->
                                                registerError = "Error al guardar usuario: ${exception.message}"
                                            }
                                    }
                                } else {
                                    registerError = "Error al crear usuario: ${task.exception?.message}"
                                }
                            }
                    } else {
                        registerError = "Por favor, rellene todos los campos y seleccione una posición."
                    }
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Crear Usuario", color = Color.Black)
            }

            Spacer(modifier = Modifier.height(32.dp))

            Text(
                text = "Regístrate con",
                style = TextStyle(
                    color = Color.White,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.W400
                )
            )

            Spacer(modifier = Modifier.height(16.dp))

            Row(
                horizontalArrangement = Arrangement.SpaceEvenly,
                modifier = Modifier.fillMaxWidth()
            ) {
                SocialIcon(imageUrl = "https://drive.google.com/uc?export=view&id=1Sp-C9BH6Z3qjyWxjv9bmiEupHWUGQZIF", onClick = {
                    println("Registro con Facebook")
                })
                SocialIcon(imageUrl = "https://drive.google.com/uc?export=view&id=1DcFglvTwiKG_7DJ4qU9K9f1ebGs-2ShG", onClick = {
                    println("Registro con Google")
                })
                SocialIcon(imageUrl = "https://drive.google.com/uc?export=view&id=14b_qojH_QipLsK9LCEukZ12RO14uFivQ", onClick = {
                    println("Registro con Apple")
                })
                SocialIcon(imageUrl = "https://drive.google.com/uc?export=view&id=1SWT8khOMLvzmsH18a27FHt5Voe17JZlq", onClick = {
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
