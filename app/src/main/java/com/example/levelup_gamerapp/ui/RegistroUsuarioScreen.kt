package com.example.levelup_gamerapp.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.platform.LocalContext
import com.example.levelup_gamerapp.viewmodel.RegistroUsuarioViewModel
import com.example.levelup_gamerapp.model.repository.RegistroUsuarioRepository
import com.example.levelup_gamerapp.model.data.AppDataBase
import com.example.levelup_gamerapp.model.data.RegistroUsuarioDAO
import com.example.levelup_gamerapp.viewmodel.RegistroUsuarioViewModelFactory
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun RegistroUsuarioScreen() {
    val context = LocalContext.current
    val dao = AppDataBase.getDatabase(context).RegistroUsuarioDAO()
    val repository = RegistroUsuarioRepository(dao)
    val viewModel: RegistroUsuarioViewModel = viewModel(
        factory = RegistroUsuarioViewModelFactory(repository)
    )

    var nombre by remember { mutableStateOf("") }
    var apellido by remember { mutableStateOf("") }
    var correo by remember { mutableStateOf("") }
    var contrasena by remember { mutableStateOf("") }
    var edad by remember { mutableStateOf("") }

    val mensaje by viewModel.mensaje.collectAsState()



    RegistroUsuarioScreen()
}