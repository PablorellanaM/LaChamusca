package com.example.lachamusca.view
import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import androidx.navigation.NavController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import com.google.firebase.storage.FirebaseStorage
import kotlinx.coroutines.launch
import java.io.ByteArrayOutputStream
import java.net.HttpURLConnection
import java.net.URL

@Composable
fun ProfileScreen(navController: NavController, context: Context) {
    var name by remember { mutableStateOf("") }
    var profileImage by remember { mutableStateOf<Bitmap?>(null) }
    var isImageSelected by remember { mutableStateOf(false) }
    val storage = FirebaseStorage.getInstance()
    val auth = FirebaseAuth.getInstance()
    val coroutineScope = rememberCoroutineScope()

    // Launcher para seleccionar la imagen
    val imagePickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri ->
        uri?.let {
            val bitmap = loadBitmapFromUri(context, uri)
            if (bitmap != null) {
                profileImage = bitmap
                isImageSelected = true
                uploadProfileImageToFirebase(bitmap, auth.currentUser?.uid, context)
            } else {
                Toast.makeText(context, "Error al cargar la imagen", Toast.LENGTH_SHORT).show()
            }
        }
    }

    // Verificar permisos y lanzar el selector de imágenes
    val storagePermissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (isGranted) {
            imagePickerLauncher.launch("image/*")
        } else {
            Toast.makeText(context, "Permiso de almacenamiento necesario", Toast.LENGTH_SHORT).show()
        }
    }

    // Recuperar datos al cargar la pantalla
    LaunchedEffect(Unit) {
        val userId = auth.currentUser?.uid ?: return@LaunchedEffect
        val userRef = FirebaseFirestore.getInstance().collection("users").document(userId)

        // Cargar el nombre
        userRef.get().addOnSuccessListener { document ->
            name = document.getString("name") ?: ""
            // Cargar la imagen de perfil
            document.getString("profileImageUrl")?.let { imageUrl ->
                val bitmap = loadImageFromUrl(imageUrl)
                profileImage = bitmap
                isImageSelected = bitmap != null
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFB71C1C)) // Fondo rojo oscuro
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Box(
            modifier = Modifier
                .size(120.dp)
                .background(Color.Gray, shape = CircleShape),
            contentAlignment = Alignment.Center
        ) {
            if (profileImage != null) {
                Image(
                    bitmap = profileImage!!.asImageBitmap(),
                    contentDescription = "Foto de perfil",
                    modifier = Modifier.size(120.dp)
                )
            } else {
                Text("Sin imagen", color = Color.White)
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        Button(onClick = {
            if (ContextCompat.checkSelfPermission(context, Manifest.permission.READ_EXTERNAL_STORAGE)
                == PackageManager.PERMISSION_GRANTED
            ) {
                imagePickerLauncher.launch("image/*")
            } else {
                storagePermissionLauncher.launch(Manifest.permission.READ_EXTERNAL_STORAGE)
            }
        }) {
            Text("Cambiar foto de perfil")
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Campo para escribir el nombre de usuario
        TextField(
            value = name,
            onValueChange = { name = it },
            label = { Text("Nombre de usuario") },
            modifier = Modifier.fillMaxWidth(0.8f)
        )

        Spacer(modifier = Modifier.height(8.dp))

        Button(onClick = {
            // Guarda el nombre de usuario en Firestore
            saveNameToFirestore(name, auth.currentUser?.uid, context)
        }) {
            Text("Guardar Nombre")
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Mostrar la valoración predeterminada de 5 estrellas
        Text(
            text = "Valoración: 5 estrellas",
            fontSize = 18.sp,
            color = Color.Yellow,
            textAlign = TextAlign.Center
        )
        Row(
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxWidth()
        ) {
            repeat(5) {
                Icon(
                    imageVector = Icons.Filled.Star,
                    contentDescription = null,
                    tint = Color.Yellow,
                    modifier = Modifier.size(24.dp)
                )
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        // Mostrar cuánto gana el usuario
        Text(
            text = "Gana: Q50 por partido",
            fontSize = 18.sp,
            color = Color.White,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(32.dp))

        // Botón de navegación al menú
        Button(
            onClick = { navController.navigate("menu") },
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF6200EA)),
            modifier = Modifier
                .align(Alignment.End)
                .padding(end = 16.dp)
        ) {
            Text("Menu", color = Color.White)
        }
    }
}

// Función para cargar el bitmap desde el URI
fun loadBitmapFromUri(context: Context, uri: Uri): Bitmap? {
    return try {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            val source = ImageDecoder.createSource(context.contentResolver, uri)
            ImageDecoder.decodeBitmap(source)
        } else {
            MediaStore.Images.Media.getBitmap(context.contentResolver, uri)
        }
    } catch (e: Exception) {
        e.printStackTrace()
        null
    }
}

// Función para cargar la imagen desde una URL
fun loadImageFromUrl(url: String): Bitmap? {
    return try {
        val connection = URL(url).openConnection() as HttpURLConnection
        connection.doInput = true
        connection.connect()
        val input = connection.inputStream
        BitmapFactory.decodeStream(input)
    } catch (e: Exception) {
        e.printStackTrace()
        null
    }
}

// Función para subir la imagen de perfil a Firebase Storage
fun uploadProfileImageToFirebase(bitmap: Bitmap, userId: String?, context: Context) {
    if (userId == null) return
    val storageRef = FirebaseStorage.getInstance().reference.child("profile_images/$userId.jpg")

    val baos = ByteArrayOutputStream()
    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos)
    val data = baos.toByteArray()

    val uploadTask = storageRef.putBytes(data)
    uploadTask.addOnSuccessListener {
        storageRef.downloadUrl.addOnSuccessListener { uri ->
            // Guarda la URL de la imagen en Firestore para recuperarla más tarde
            val userRef = FirebaseFirestore.getInstance().collection("users").document(userId)
            userRef.set(mapOf("profileImageUrl" to uri.toString()), SetOptions.merge())
        }
    }.addOnFailureListener {
        Toast.makeText(context, "Error al subir la imagen", Toast.LENGTH_SHORT).show()
    }
}

// Función para guardar el nombre en Firestore
fun saveNameToFirestore(name: String, userId: String?, context: Context) {
    if (userId == null) return
    val userRef = FirebaseFirestore.getInstance().collection("users").document(userId)

    userRef.set(mapOf("name" to name), SetOptions.merge())
        .addOnSuccessListener {
            Toast.makeText(context, "Nombre guardado correctamente", Toast.LENGTH_SHORT).show()
        }
        .addOnFailureListener {
            Toast.makeText(context, "Error al guardar el nombre", Toast.LENGTH_SHORT).show()
        }
}
