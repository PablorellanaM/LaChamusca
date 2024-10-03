package com.example.lachamusca.view

import android.net.Uri
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.*
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.activity.compose.rememberLauncherForActivityResult
import com.example.lachamusca.getSharedPrefs
import com.example.lachamusca.saveUserName
import com.example.lachamusca.getUserName

@Composable
fun ProfileScreen(navController: NavController) {
    // Acceso al contexto desde el Composable
    val context = LocalContext.current

    // Recuperamos el nombre de usuario de SharedPreferences
    val prefs = context.getSharedPrefs()
    var userName by remember { mutableStateOf(prefs.getUserName() ?: "") }

    // Estado para la imagen seleccionada
    var selectedImageUri by remember { mutableStateOf<Uri?>(null) }

    // Registro de launcher para seleccionar imagen de la galería
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        selectedImageUri = uri  // Actualizamos la URI seleccionada
    }

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
                .padding(16.dp),  // Ajuste de padding
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            // Logo en la parte superior izquierda
            Image(
                painter = rememberImagePainter("https://via.placeholder.com/211x173"),
                contentDescription = "Logo superior",
                modifier = Modifier
                    .size(211.dp, 173.dp)
                    .padding(start = 0.dp, top = 0.dp),  // Se eliminan valores negativos
                contentScale = ContentScale.FillBounds
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Imagen de perfil con botón para editar (lápiz)
            Box(contentAlignment = Alignment.TopEnd) {
                Image(
                    painter = rememberImagePainter(
                        data = selectedImageUri ?: "https://via.placeholder.com/288x373"
                    ),
                    contentDescription = "Imagen de perfil",
                    modifier = Modifier
                        .size(150.dp)
                        .clip(CircleShape)
                        .clickable {
                            // Abrir la galería al hacer clic en la imagen
                            launcher.launch("image/*")
                        },
                    contentScale = ContentScale.Crop
                )

                Image(
                    painter = rememberImagePainter("https://via.placeholder.com/lapiz.png"), // Ícono del lápiz
                    contentDescription = "Editar imagen",
                    modifier = Modifier
                        .size(24.dp)
                        .align(Alignment.TopEnd)
                        .clickable {
                            // Abrir la galería al hacer clic en el ícono de lápiz
                            launcher.launch("image/*")
                        }
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Campo de texto para que el usuario ingrese su nombre
            BasicTextField(
                value = userName,
                onValueChange = { userName = it },
                textStyle = TextStyle(
                    color = Color.White,
                    fontSize = 25.sp,
                    fontWeight = FontWeight.W400
                ),
                modifier = Modifier
                    .background(Color.Gray, shape = CircleShape)
                    .padding(8.dp)
                    .fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Botón para guardar el nombre en SharedPreferences
            Button(
                onClick = {
                    prefs.saveUserName(userName)  // Guardamos el nombre en SharedPreferences
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
            ) {
                Text("Guardar Nombre")
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Botón de navegación al menú
            Button(
                onClick = { navController.navigate("menu") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
            ) {
                Text("Menu")
            }
        }
    }
}
