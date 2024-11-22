// Paquete principal de la aplicación
package com.example.lachamusca

// Importaciones necesarias para las funcionalidades de la aplicación
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.core.view.WindowCompat
import com.example.lachamusca.navigation.Navigation
import com.example.lachamusca.ui.theme.LaChamuscaTheme
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.net.PlacesClient
import com.google.firebase.firestore.FirebaseFirestore

// Actividad principal de la aplicación
class MainActivity : ComponentActivity() {

    // Declaración de variables para autenticación, base de datos y cliente de Google Places
    private lateinit var auth: FirebaseAuth
    private lateinit var db: FirebaseFirestore
    private lateinit var placesClient: PlacesClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Inicialización del servicio de autenticación de Firebase
        auth = FirebaseAuth.getInstance()

        // Inicialización de la base de datos Firestore
        db = FirebaseFirestore.getInstance()

        // Inicialización del cliente de Google Places
        if (!Places.isInitialized()) {
            // Configuración del API Key para Google Places
            Places.initialize(applicationContext, "YOUR_API_KEY_HERE")
        }
        placesClient = Places.createClient(this)

        // Configuración para un diseño de pantalla de borde a borde
        WindowCompat.setDecorFitsSystemWindows(window, false)

        // Configuración del contenido de la interfaz de usuario
        setContent {
            // Uso del tema personalizado de la aplicación
            LaChamuscaTheme {
                // Scaffold para manejar el diseño general de la pantalla
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    // Navegación de la aplicación con margen interno
                    Navigation(modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }

    // Método para obtener el usuario actualmente autenticado
    private fun getCurrentUser(): FirebaseUser? {
        return auth.currentUser
    }

    // Método para obtener el cliente de Google Places
    fun getPlacesClient(): PlacesClient {
        return placesClient
    }
}

