package com.example.levelup_gamerapp.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.material3.OutlinedTextField
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.text.input.VisualTransformation
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.ui.platform.LocalContext
import com.example.levelup_gamerapp.model.data.AppDataBase
import com.example.levelup_gamerapp.model.repository.RegistroUsuarioRepository
import com.example.levelup_gamerapp.viewmodel.RegistroUsuarioViewModel
import com.example.levelup_gamerapp.viewmodel.RegistroUsuarioViewModelFactory

@Composable
fun RegistroUsuarioScreen() {
    // Inicialización MVVM
    val context = LocalContext.current
    val dao = AppDataBase.getDatabase(context).registroUsuarioDao()
    val repository = RegistroUsuarioRepository(dao)
    val vm: RegistroUsuarioViewModel = viewModel(
        factory = RegistroUsuarioViewModelFactory(repository)
    )

    // Estados de los campos
    var nombre by remember { mutableStateOf("") }
    var apellido by remember { mutableStateOf("") }
    var correo by remember { mutableStateOf("") }
    var contrasena by remember { mutableStateOf("") }
    var edad by remember { mutableStateOf("") }

    val mensaje by vm.mensaje.collectAsState()

    // Interfaz principal
    Surface(
        modifier = Modifier.fillMaxSize().padding(24.dp),
        color = MaterialTheme.colorScheme.background
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxSize()
        ) {
            Text(
                "Registro de Usuario",
                style = MaterialTheme.typography.headlineMedium,
                color = MaterialTheme.colorScheme.secondary
            )
            Spacer(Modifier.height(16.dp))

            // Campos básicos (más compactos)
            CampoTexto("Nombre", nombre) { nombre = it }
            CampoTexto("Apellido", apellido) { apellido = it }
            CampoTexto("Correo", correo) { correo = it }
            CampoTexto(
                "Contraseña",
                contrasena,
                esPassword = true
            ) { contrasena = it }
            CampoTexto("Edad", edad) { edad = it }

            Spacer(Modifier.height(24.dp))

            Button(
                onClick = {
                    val edadInt = edad.toIntOrNull() ?: 0
                    vm.registrar(nombre, apellido, correo, contrasena, edadInt)
                },
                modifier = Modifier.fillMaxWidth().height(50.dp)
            ) {
                Text("Registrarse")
            }

            if (mensaje.isNotEmpty()) {
                Spacer(Modifier.height(12.dp))
                Text(
                    text = mensaje,
                    color = when {
                        mensaje.contains("descuento", ignoreCase = true) ||
                                mensaje.contains("%", ignoreCase = true) -> // detecta descuento con o sin emoji
                            MaterialTheme.colorScheme.secondary
                        mensaje.startsWith("Registro exitoso") ->
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

// 🔹 Composable auxiliar para simplificar campos
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
        modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp),
        visualTransformation = if (esPassword) PasswordVisualTransformation() else VisualTransformation.None
    )
}