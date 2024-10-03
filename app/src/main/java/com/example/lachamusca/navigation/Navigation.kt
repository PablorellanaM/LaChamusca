package com.example.lachamusca.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.lachamusca.view.*

@Composable
fun AppNavigation(modifier: Modifier = Modifier) {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = "loginScreen",  // La pantalla inicial será la de login
        modifier = modifier
    ) {
        composable("loginScreen") {
            LoginScreen(navController)  // Pantalla de login
        }
        composable("findMatch") {
            EncontrarPartidoScreen(navController)  // Pantalla para encontrar partidos
        }
        composable("createMatch") {
            CrearPartidoScreen(navController)  // Pantalla para crear partidos
        }
        composable("popularMatches") {
            PartidosPopularesScreen(navController)  // Pantalla de partidos populares
        }
        composable("teamsToJoin") {
            EquiposUnirteScreen(navController)  // Pantalla de equipos a los que puedes unirte
        }
        composable("menu") {
            MenuScreen(navController = navController)  // Pantalla del menú
        }
        composable(route = "profile") {
            ProfileScreen(navController = navController)  // Pantalla del perfil
        }
    }
}

fun ProfileScreen(navController: NavHostController) {
    // Implementación de la pantalla de perfil
}

fun CrearPartidoScreen(navController: NavHostController) {
    // Implementación de la pantalla para crear partidos
}

fun PartidosPopularesScreen(navController: NavHostController) {
    // Implementación de la pantalla de partidos populares
}

fun EquiposUnirteScreen(navController: NavHostController) {
    // Implementación de la pantalla para equipos a los que puedes unirte
}
