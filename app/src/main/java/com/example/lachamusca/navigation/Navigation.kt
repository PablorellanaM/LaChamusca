package com.example.lachamusca.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.lachamusca.view.*

@Composable
fun AppNavigation(modifier: Modifier = Modifier) {
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
            val context = LocalContext.current // Obtener el contexto actual
            EncontrarPartidoScreen(navController, context)
        }
        composable(route = "createMatch") {
            CrearPartidoScreen(navController = navController, context)
        }
        composable("popularMatches") {
            PartidosPopularesScreen(navController)  // Pantalla de partidos populares
        }
        composable("teamsToJoin") {
            EquiposParaUnirteScreen(navController)  // Pantalla de equipos a los que puedes unirte
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
    }
}
