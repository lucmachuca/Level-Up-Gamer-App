package com.example.levelup_gamerapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.compose.rememberNavController
import com.example.levelup_gamerapp.ui.LevelUpNavHost
import com.example.levelup_gamerapp.ui.theme.LevelUpGamerAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            // 🎨 Aplica el tema visual gamer
            LevelUpGamerAppTheme {
                // 🧭 Crea un controlador de navegación
                val navController = rememberNavController()

                // 🔹 Carga el host de navegación
                LevelUpNavHost(navController = navController)
            }
        }
    }
}
