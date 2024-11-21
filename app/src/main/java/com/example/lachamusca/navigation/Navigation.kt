package com.example.lachamusca.navigation

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.lachamusca.view.*

@Composable
fun Navigation(modifier: Modifier = Modifier) {
    val navController = rememberNavController()
    val context = LocalContext.current  // Obtener el contexto actual

    NavHost(
        navController = navController,
        startDestination = "loginScreen",  // La pantalla inicial será la de login
        modifier = modifier
    ) {
        composable("loginScreen") {
            LoginScreen(navController)  // Pantalla de login
        }
        composable(route = "findMatch") {
            EncontrarPartidoScreen(navController, context)  // Pantalla de encontrar partido
        }
        composable(route = "createMatch") {
            CrearPartidoScreen(navController = navController, context)  // Pantalla de crear partido
        }
        composable("popularMatches") {
            PartidosPopularesScreen(navController)  // Pantalla de partidos populares
        }
        composable("teamsToJoin") {
            UnirteEquipoScreen(navController)  // Pantalla de equipos a los que puedes unirte
        }
        composable("createTeam") {
            CrearEquipoScreen(navController)  // Pantalla para crear equipos
        }
        composable("menu") {
            MenuScreen(navController = navController)  // Pantalla del menú
        }
        composable(route = "profile") {
            ProfileScreen(navController = navController, context)  // Pantalla del perfil
        }
        composable("registerScreen") {
            RegisterScreen(navController = navController)  // Pantalla para el registro de usuarios
        }

        composable("equipos") {
            EquiposScreen(navController) // Pantalla para las opciones de equipos
        }

        composable(route = "crearEquipo") {
            CrearEquipoScreen(navController = navController) // Pantalla para crear equipos
        }

    }
}

