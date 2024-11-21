package com.example.lachamusca.view
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EquiposScreen(navController: NavHostController) {
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
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceAround
        ) {
            // Título
            Text(
                text = "Equipos",
                fontSize = 24.sp,
                color = Color.White
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Botón para crear equipo
            Button(
                onClick = { navController.navigate("createTeam") },
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF6200EA)),
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Crear Equipo", color = Color.White)
            }

            // Botón para unirte a un equipo
            Button(
                onClick = { navController.navigate("teamsToJoin") },
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF6200EA)),
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Unirte a un Equipo", color = Color.White)
            }

            // Botón para ver el equipo al que pertenece el usuario
            Button(
                onClick = { navController.navigate("myTeam") },
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF6200EA)),
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Ver mi Equipo", color = Color.White)
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Botón para regresar al menú
            Button(
                onClick = { navController.navigate("menu") },
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF6200EA)),
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Volver al Menú", color = Color.White)
            }
        }
    }
}
