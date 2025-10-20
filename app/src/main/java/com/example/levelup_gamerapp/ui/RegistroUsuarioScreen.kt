package com.example.levelup_gamerapp.ui

import android.app.Application
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.levelup_gamerapp.local.AppDatabase
import com.example.levelup_gamerapp.repository.RegistroUsuarioRepository
import com.example.levelup_gamerapp.viewmodel.RegistroUsuarioViewModel
import com.example.levelup_gamerapp.viewmodel.RegistroUsuarioViewModelFactory

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegistroUsuarioScreen(
    navController: NavController? = null
) {
    val app = LocalContext.current.applicationContext as Application
    val dao = AppDatabase.obtenerBaseDatos(app).registroUsuarioDao() // ✅ cambio aquí
    val repo = RegistroUsuarioRepository(dao)
    val vm: RegistroUsuarioViewModel = viewModel(factory = RegistroUsuarioViewModelFactory(repo))

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("Level Up") },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = Color.Black
                )
            )
        },
        containerColor = Color.Black
    ) { padding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .background(Color.Black),
            contentAlignment = Alignment.Center
        ) {
            Surface(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(24.dp),
                color = MaterialTheme.colorScheme.background
            ) {
                FormScreen(
                    vm = vm,
                    onSaved = {
                        navController?.navigate("login") {
                            popUpTo("registro_usuario") { inclusive = true }
                        }
                    }
                )
            }
        }
    }
}
