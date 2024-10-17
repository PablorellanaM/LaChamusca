package com.example.lachamusca.view

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import androidx.navigation.NavController
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.api.net.FindCurrentPlaceRequest

@Composable
fun PartidosPopularesScreen(navController: NavController) {
    // Obtén el contexto local
    val context = LocalContext.current

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(brush = Brush.verticalGradient(colors = listOf(Color(0xFFA10202), Color(0xFF351111))))
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            // Título de la pantalla
            Text(
                text = "Partidos Populares",
                style = TextStyle(color = Color.White, fontSize = 25.sp),
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Botón para ver los partidos populares usando Google Places
            Button(
                onClick = { buscarCanchasPopulares(context) },
                modifier = Modifier.align(Alignment.CenterHorizontally)
            ) {
                Text(text = "Ver Partidos Populares", color = Color.Black)
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Botón para volver al menú
            Button(
                onClick = { navController.navigate("menu") },
                modifier = Modifier.align(Alignment.CenterHorizontally)
            ) {
                Text(text = "Menú")
            }
        }
    }
}

// Función para manejar la llamada a la API de Google Places en Partidos Populares
fun buscarCanchasPopulares(context: Context) {
    val placesClient = Places.createClient(context)
    val placeFields = listOf(Place.Field.NAME, Place.Field.ADDRESS)
    val request = FindCurrentPlaceRequest.newInstance(placeFields)

    // Verificar permisos antes de hacer la solicitud
    if (ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
        val placeResult = placesClient.findCurrentPlace(request)
        placeResult.addOnSuccessListener { response ->
            for (place in response.placeLikelihoods) {
                Log.i("PlacesAPI", "Place found: ${place.place.name}, ${place.place.address}")
            }
        }.addOnFailureListener { exception ->
            Log.e("PlacesAPI", "Error: ${exception.message}")
        }
    } else {
        Log.e("PlacesAPI", "Permission denied")
    }
}
