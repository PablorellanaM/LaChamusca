package com.example.lachamusca

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.view.WindowCompat
import com.example.lachamusca.ui.theme.LaChamuscaTheme
import com.example.lachamusca.navigation.AppNavigation
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.net.PlacesClient
import com.google.firebase.firestore.FirebaseFirestore

class MainActivity : ComponentActivity() {

    // FirebaseAuth and FirebaseFirestore instances
    private lateinit var auth: FirebaseAuth
    private lateinit var db: FirebaseFirestore

    // PlacesClient instance for Places API
    private lateinit var placesClient: PlacesClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Initialize Firebase Auth
        auth = FirebaseAuth.getInstance()

        // Initialize Firestore
        db = FirebaseFirestore.getInstance()

        // Initialize Google Places with the API key
        if (!Places.isInitialized()) {
            Places.initialize(applicationContext, "YOUR_API_KEY_HERE")
        }

        // Initialize PlacesClient
        placesClient = Places.createClient(this)

        // Enable edge-to-edge mode
        val window = this.window
        enableEdgeToEdge(window)

        setContent {
            LaChamuscaTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    AppNavigation(modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }

    // Function to get the current user
    private fun getCurrentUser(): FirebaseUser? {
        return auth.currentUser
    }

    // Function to access the initialized PlacesClient
    fun getPlacesClient(): PlacesClient {
        return placesClient
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

private fun enableEdgeToEdge(window: android.view.Window) {
    WindowCompat.setDecorFitsSystemWindows(window, false)
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    LaChamuscaTheme {
        Greeting("Android")
    }
}
