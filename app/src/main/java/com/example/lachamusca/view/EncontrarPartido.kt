package com.example.lachamusca.view

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.location.LocationRequest
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.navigation.NavController
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapProperties
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState

@Composable
fun EncontrarPartidoScreen(navController: NavController, context: Context) {
    var userLocation by remember { mutableStateOf<LatLng?>(null) }
    var mostrarMapa by remember { mutableStateOf(false) }

    val locationPermissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestMultiplePermissions()
    ) { permissions ->
        if (permissions[Manifest.permission.ACCESS_FINE_LOCATION] == true) {
            Log.d("MapsDebug", "Permisos de ubicación concedidos")
            obtenerUbicacion(context) { location ->
                userLocation = LatLng(location.latitude, location.longitude)
                mostrarMapa = true
                Log.d("MapsDebug", "Ubicación obtenida: $userLocation")
            }
        } else {
            Log.d("MapsDebug", "Permisos de ubicación denegados")
        }
    }

    fun solicitarPermisosYMostrarMapa() {
        if (ContextCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            locationPermissionLauncher.launch(
                arrayOf(
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                )
            )
        } else {
            obtenerUbicacion(context) { location ->
                userLocation = LatLng(location.latitude, location.longitude)
                mostrarMapa = true
                Log.d("MapsDebug", "Permisos ya otorgados. Ubicación: $userLocation")
            }
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(text = "Encontrar Partido")

            Spacer(modifier = Modifier.height(16.dp))

            Button(onClick = {
                solicitarPermisosYMostrarMapa()
                Log.d("MapsDebug", "Botón 'Encontrar Canchas Cercanas' presionado")
            }) {
                Text(text = "Encontrar Canchas Cercanas")
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Mostrar el mapa si la ubicación es válida
            if (mostrarMapa && userLocation != null) {
                Log.d("MapsDebug", "Mostrando el mapa con ubicación: $userLocation")
                GoogleMap(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp),
                    properties = MapProperties(isMyLocationEnabled = true)
                ) {
                    userLocation?.let { location ->
                        Marker(
                            state = MarkerState(position = location),
                            title = "Tu ubicación"
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Button(onClick = {
                navController.navigate("menu")
            }) {
                Text(text = "Menu")
            }
        }
    }
}

@SuppressLint("MissingPermission")
fun obtenerUbicacion(context: Context, onResult: (android.location.Location) -> Unit) {
    val fusedLocationClient = LocationServices.getFusedLocationProviderClient(context)
    fusedLocationClient.lastLocation.addOnSuccessListener { location ->
        if (location != null) {
            onResult(location)
        } else {
            Log.d("MapsDebug", "Ubicación no encontrada, solicitando nueva ubicación")

            // Solicitar una nueva ubicación en tiempo real si no hay una ubicación guardada
            val locationRequest = com.google.android.gms.location.LocationRequest.Builder(1000)
                .setPriority(com.google.android.gms.location.Priority.PRIORITY_HIGH_ACCURACY)
                .build()

            fusedLocationClient.requestLocationUpdates(locationRequest, object :
                com.google.android.gms.location.LocationCallback() {
                override fun onLocationResult(locationResult: com.google.android.gms.location.LocationResult) {
                    val updatedLocation = locationResult.lastLocation
                    if (updatedLocation != null) {
                        onResult(updatedLocation)
                        fusedLocationClient.removeLocationUpdates(this)
                    }
                }
            }, null)
        }
    }.addOnFailureListener { exception ->
        Log.d("MapsDebug", "Error al obtener la ubicación: ${exception.message}")
    }
}
