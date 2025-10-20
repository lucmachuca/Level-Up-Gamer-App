package com.example.levelup_gamerapp.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable

// 🧭 Controla la navegación entre pantallas Compose
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
        // Pantalla de inicio de sesión
        composable("login") {
            LoginScreen(
                onLoginSuccess = {
                    // Aquí puedes ir a la pantalla de noticias después del merge
                    // navController.navigate("noticias")
                },
                onNavigateRegistro = {
                    navController.navigate("registro_usuario")
                }
            )
        }

        // Pantalla de registro (existirá tras el merge)
        composable("registro_usuario") {
            RegistroUsuarioScreen()
        }
    }
}
