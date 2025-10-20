package com.example.levelup_gamerapp.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.levelup_gamerapp.local.AppDatabase
import com.example.levelup_gamerapp.repository.LoginRepository
import com.example.levelup_gamerapp.viewmodel.LoginViewModel
import com.example.levelup_gamerapp.viewmodel.LoginViewModelFactory
import com.example.levelup_gamerapp.viewmodel.SesionViewModel

@Composable
fun LoginScreen(
    onLoginSuccess: () -> Unit = {},
    onNavigateRegistro: () -> Unit = {}
) {
    val context = LocalContext.current
    val dao = AppDatabase.obtenerBaseDatos(context).registroUsuarioDao()
    val repository = LoginRepository(dao)
    val vm: LoginViewModel = viewModel(factory = LoginViewModelFactory(repository))
    val sesionViewModel: SesionViewModel = viewModel() // ✅ ViewModel de sesión

    var correo by remember { mutableStateOf("") }
    var contrasena by remember { mutableStateOf("") }
    val mensaje by vm.mensaje.collectAsState()

    Surface(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        color = MaterialTheme.colorScheme.background
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxSize()
        ) {
            Text(
                text = "Iniciar Sesión",
                style = MaterialTheme.typography.headlineMedium,
                color = MaterialTheme.colorScheme.secondary
            )

            Spacer(Modifier.height(16.dp))

            CampoTexto("Correo", correo) { correo = it }
            CampoTexto("Contraseña", contrasena, esPassword = true) { contrasena = it }

            Spacer(Modifier.height(24.dp))

            Button(
                onClick = {
                    vm.iniciarSesion(correo, contrasena)
                    if (mensaje.contains("exitoso", ignoreCase = true)) {
                        sesionViewModel.login() // ✅ marca sesión iniciada
                        onLoginSuccess()
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp)
            ) {
                Text("Ingresar")
            }

            TextButton(onClick = onNavigateRegistro) {
                Text("¿No tienes cuenta? Regístrate aquí")
            }

            if (mensaje.isNotEmpty()) {
                Spacer(Modifier.height(12.dp))
                Text(
                    text = mensaje,
                    color = when {
                        mensaje.contains("exitoso", ignoreCase = true) ->
                            MaterialTheme.colorScheme.primary
                        else ->
                            MaterialTheme.colorScheme.error
                    },
                    style = MaterialTheme.typography.bodyLarge
                )
            }
        }
    }
}

// 🔹 Campo de texto reutilizable
@Composable
fun CampoTexto(
    etiqueta: String,
    valor: String,
    esPassword: Boolean = false,
    onValorCambio: (String) -> Unit
) {
    OutlinedTextField(
        value = valor,
        onValueChange = onValorCambio,
        label = { Text(etiqueta) },
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        visualTransformation = if (esPassword) PasswordVisualTransformation() else VisualTransformation.None
    )
}
