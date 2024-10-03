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


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Obtener el Window actual de la actividad
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