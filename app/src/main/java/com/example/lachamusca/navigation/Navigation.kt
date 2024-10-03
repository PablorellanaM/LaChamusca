package com.example.lachamusca.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.lachamusca.view.LoginScreen
import com.example.lachamusca.view.MatchListScreen
import com.example.lachamusca.view.MenuScreen
import com.example.lachamusca.view.ProfileScreen

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
        composable("matchListScreen") {
            MatchListScreen(navController)  // Pantalla de lista de partidos
        }
        composable("menu") {
            composable("menu") {
                MenuScreen(navController = navController)
        }
        composable("profile") {
            ProfileScreen()
        }


    }
}


}
