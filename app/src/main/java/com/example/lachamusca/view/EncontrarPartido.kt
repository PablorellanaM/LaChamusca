package com.example.lachamusca.view

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.unit.sp
import androidx.compose.ui.text.font.FontFamily
import androidx.core.content.ContextCompat
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.api.net.PlacesClient
import com.google.android.libraries.places.api.net.FindCurrentPlaceRequest
import com.google.android.libraries.places.api.net.FindCurrentPlaceResponse

@Composable
fun EncontrarPartidoScreen(navController: NavController, context: Context) {
    val partidos = remember {
        mutableStateListOf(
            Partido("Partido en Futeca Cayala", "De 4:00 p.m a 5:00 p.m", 9, 10),
            Partido("Partido en Campo Marte", "De 2:00 p.m a 3:00 p.m", 5, 10),
            Partido("Partido La Cantera Atlantico", "De 5:00 p.m a 6:00 p.m", 7, 10),
            Partido("Partido en Campos del Roosevelt", "De 6:00 p.m a 7:00 p.m", 8, 10),
            Partido("Partido en BRIO FUTBOL", "De 5:00 pm a 6:00 p.m.", 6, 10)
        )
    }

    // Inicializar Google Places si aún no se ha hecho
    if (!Places.isInitialized()) {
        Places.initialize(context, "AIzaSyBwQiSNqKIny4UH4Z0B1EyZ60hR1Jve9bg") // Reemplazar con tu API Key
    }

    val placesClient: PlacesClient = Places.createClient(context)

    // Manejo de permisos
    val locationPermissionGranted = remember { mutableStateOf(false) }

    // Launcher para pedir permisos en tiempo de ejecución
    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission(),
        onResult = { granted ->
            locationPermissionGranted.value = granted
        }
    )

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
            Text(
                text = "Encontrar Partido",
                style = TextStyle(color = Color.White, fontSize = 25.sp, fontFamily = FontFamily.Default, fontWeight = FontWeight.W400),
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "Partidos Disponibles",
                style = TextStyle(fontWeight = FontWeight.Bold, fontSize = 18.sp, color = Color.Black)
            )

            Spacer(modifier = Modifier.height(8.dp))

            LazyColumn(modifier = Modifier.fillMaxWidth()) {
                items(partidos) { partido ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp)
                            .background(Color.White, shape = RoundedCornerShape(8.dp)) // Fondo blanco
                            .border(BorderStroke(1.dp, Color.Gray), RoundedCornerShape(8.dp)) // Borde gris
                            .padding(8.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Column {
                            Text(text = partido.nombre, fontWeight = FontWeight.W400, color = Color.Black)
                            Text(text = partido.horario, style = TextStyle(color = Color.Gray, fontSize = 14.sp))
                            Text(text = "${partido.participantes}/${partido.maxParticipantes}", color = Color.Black)
                        }
                        Button(
                            onClick = {
                                if (partido.participantes < partido.maxParticipantes) {
                                    partido.participantes++
                                }
                            }
                        ) {
                            Text(text = "Unirse")
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Botón para buscar canchas cercanas usando Google Places API
            Button(
                onClick = {
                    // Verificar permisos antes de realizar la solicitud
                    when (ContextCompat.checkSelfPermission(
                        context,
                        Manifest.permission.ACCESS_FINE_LOCATION
                    )) {
                        PackageManager.PERMISSION_GRANTED -> {
                            locationPermissionGranted.value = true
                            buscarCanchasCercanas(placesClient)
                        }
                        else -> {
                            // Solicitar permisos
                            permissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
                        }
                    }
                },
                modifier = Modifier.align(Alignment.CenterHorizontally)
            ) {
                Text(text = "Buscar Canchas Cercanas")
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Botón de menú en la parte inferior
            Button(
                onClick = { navController.navigate("menu") },
                modifier = Modifier.align(Alignment.CenterHorizontally)
            ) {
                Text(text = "Menu")
            }
        }
    }
}

// Función para buscar canchas cercanas utilizando la API de Google Places
fun buscarCanchasCercanas(placesClient: PlacesClient) {
    val placeFields = listOf(Place.Field.NAME, Place.Field.ADDRESS, Place.Field.LAT_LNG)

    // Crear una solicitud para obtener los lugares actuales
    val request = FindCurrentPlaceRequest.newInstance(placeFields)

    try {
        val placeResult = placesClient.findCurrentPlace(request)

        placeResult.addOnSuccessListener { response: FindCurrentPlaceResponse ->
            for (placeLikelihood in response.placeLikelihoods) {
                Log.i("EncontrarPartido", "Lugar: ${placeLikelihood.place.name}, Dirección: ${placeLikelihood.place.address}")
            }
        }.addOnFailureListener { exception: Exception ->
            Log.e("EncontrarPartido", "Error al buscar lugares: ${exception.message}")
        }
    } catch (e: SecurityException) {
        Log.e("EncontrarPartido", "Permisos no concedidos para buscar lugares.")
    }
}

data class Partido(
    val nombre: String,
    val horario: String,
    var participantes: Int,
    val maxParticipantes: Int
)
