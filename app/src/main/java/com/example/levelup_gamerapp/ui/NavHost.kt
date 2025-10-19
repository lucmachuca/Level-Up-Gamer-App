package com.example.levelup_gamerapp.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable

@Composable
fun LevelUpNavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination = "login",
        modifier = modifier
    ) {
        composable("login") {
            LoginScreen(
                onLoginSuccess = {
                    // Navegarás a "noticias" más adelante
                },
                onNavigateRegistro = {
                    navController.navigate("registro_usuario")
                }
            )
        }
        // Esta ruta se activará cuando hagas merge con registro
        composable("registro_usuario") {
            RegistroUsuarioScreen()
        }
    }
}
