package com.example.lachamusca

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

class MainActivity : ComponentActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var db: FirebaseFirestore
    private lateinit var placesClient: PlacesClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Initialize Firebase Auth
        auth = FirebaseAuth.getInstance()

        // Initialize Firestore
        db = FirebaseFirestore.getInstance()

        // Initialize Google Places
        if (!Places.isInitialized()) {
            Places.initialize(applicationContext, "YOUR_API_KEY_HERE")
        }
        placesClient = Places.createClient(this)

        // Enable edge-to-edge mode
        WindowCompat.setDecorFitsSystemWindows(window, false)

        setContent {
            LaChamuscaTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Navigation(modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }

    private fun getCurrentUser(): FirebaseUser? {
        return auth.currentUser
    }

    fun getPlacesClient(): PlacesClient {
        return placesClient
    }
}
