package com.example.levelup_gamerapp.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable

// 📘 Este componente controla la navegación entre pantallas Compose.
// Define las rutas (destinos) y qué pantalla se muestra en cada una.
@Composable
fun LevelUpNavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination = "registro_usuario", // pantalla inicial
        modifier = modifier
    ) {
        //Pantalla de registro
        composable("registro_usuario") {
            RegistroUsuarioScreen()
        }

        //  Posibilidad de agregar mas pantallas

        // composable("login") { LoginScreen() }
        // composable("home") { HomeScreen() }
    }
}
