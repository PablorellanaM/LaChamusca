package com.example.lachamusca.view

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.navigation.NavController
import com.example.lachamusca.R
import com.example.lachamusca.utils.NetworkUtils
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MapStyleOptions
import com.google.maps.android.compose.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import okhttp3.Request
import org.json.JSONObject

@Composable
fun EncontrarPartidoScreen(navController: NavController, context: Context) {
    val mapStyleOptions = MapStyleOptions.loadRawResourceStyle(context, R.raw.map_style)
    var userLocation by remember { mutableStateOf<LatLng?>(null) }
    var mostrarMapa by remember { mutableStateOf(false) }
    var isConnected by remember { mutableStateOf(true) }
    var nearbyFields by remember { mutableStateOf<List<LatLng>>(emptyList()) }
    var errorMessage by remember { mutableStateOf<String?>(null) }

    val scope = rememberCoroutineScope()

    // Verificar la conexión a internet
    LaunchedEffect(Unit) {
        isConnected = NetworkUtils.isInternetAvailable(context)
    }

    val locationPermissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestMultiplePermissions()
    ) { permissions ->
        if (permissions[Manifest.permission.ACCESS_FINE_LOCATION] == true) {
            obtenerUbicacion(context) { location ->
                userLocation = LatLng(location.latitude, location.longitude)
                mostrarMapa = true
                scope.launch {
                    val canchas = obtenerCanchasCercanas(context, userLocation)
                    nearbyFields = canchas
                }
            }
        } else {
            errorMessage = "Permisos de ubicación denegados"
        }
    }

    fun solicitarPermisosYMostrarMapa() {
        if (!isConnected) {
            errorMessage = "Sin conexión a internet"
            return
        }

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
                scope.launch {
                    val canchas = obtenerCanchasCercanas(context, userLocation)
                    nearbyFields = canchas
                }
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

            Button(onClick = { solicitarPermisosYMostrarMapa() }) {
                Text(text = "Encontrar Canchas Cercanas")
            }

            Spacer(modifier = Modifier.height(16.dp))

            if (errorMessage != null) {
                Text(text = errorMessage ?: "", color = Color.Red)
            } else if (mostrarMapa && userLocation != null) {
                GoogleMap(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp),
                    properties = MapProperties(
                        isMyLocationEnabled = true,
                        mapType = MapType.NORMAL,
                        mapStyleOptions = mapStyleOptions
                    )
                ) {
                    userLocation?.let { location ->
                        Marker(
                            state = MarkerState(position = location),
                            title = "Tu ubicación",
                            snippet = "Aquí te encuentras"
                        )
                    }

                    nearbyFields.forEach { cancha ->
                        Marker(
                            state = MarkerState(position = cancha),
                            title = "Cancha de fútbol",
                            icon = BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)
                        )
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = { navController.navigate("menu") }) {
            Text(text = "Menú")
        }
    }
}

@SuppressLint("MissingPermission")
fun obtenerUbicacion(context: Context, onResult: (android.location.Location) -> Unit) {
    val fusedLocationClient = LocationServices.getFusedLocationProviderClient(context)
    fusedLocationClient.lastLocation.addOnSuccessListener { location ->
        if (location != null) {
            onResult(location)
        }
    }.addOnFailureListener { exception ->
        Log.d("MapsDebug", "Error al obtener la ubicación: ${exception.message}")
    }
}

// Función suspendida para obtener las canchas cercanas usando Google Places API
suspend fun obtenerCanchasCercanas(
    context: Context,
    userLocation: LatLng?
): List<LatLng> {
    if (userLocation == null) return emptyList()

    val apiKey = "TU_CLAVE_API_AQUI"
    val radius = 5000
    val type = "stadium"

    val url = "https://maps.googleapis.com/maps/api/place/nearbysearch/json" +
            "?location=${userLocation.latitude},${userLocation.longitude}" +
            "&radius=$radius&type=$type&key=$apiKey"

    return withContext(Dispatchers.IO) {
        try {
            val client = OkHttpClient()
            val request = Request.Builder().url(url).build()
            val response = client.newCall(request).execute()
            val responseData = response.body?.string() ?: return@withContext emptyList()

            val jsonObject = JSONObject(responseData)
            val results = jsonObject.getJSONArray("results")
            val canchas = mutableListOf<LatLng>()

            for (i in 0 until results.length()) {
                val location = results.getJSONObject(i)
                    .getJSONObject("geometry")
                    .getJSONObject("location")
                val lat = location.getDouble("lat")
                val lng = location.getDouble("lng")
                canchas.add(LatLng(lat, lng))
            }

            canchas
        } catch (e: Exception) {
            Log.e("NearbySearch", "Error al obtener canchas cercanas: ${e.message}")
            emptyList()
        }
    }
}
