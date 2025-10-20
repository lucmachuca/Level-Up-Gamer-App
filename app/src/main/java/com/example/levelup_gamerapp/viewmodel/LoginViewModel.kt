package com.example.levelup_gamerapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.levelup_gamerapp.repository.LoginRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

// 🧠 ViewModel del Login
// Maneja la lógica de validación y los mensajes que se muestran en pantalla.
class LoginViewModel(private val repository: LoginRepository) : ViewModel() {

    private val _mensaje = MutableStateFlow("") // Estado del mensaje
    val mensaje: StateFlow<String> = _mensaje

    // Inicia sesión validando el correo y la contraseña
    fun iniciarSesion(correo: String, contrasena: String) {
        viewModelScope.launch {
            if (correo.isBlank() || contrasena.isBlank()) {
                _mensaje.value = "Completa todos los campos"
                return@launch
            }

            if (!repository.existeCorreo(correo)) {
                _mensaje.value = "Usuario no encontrado"
                return@launch
            }

            val valido = repository.validarUsuario(correo, contrasena)
            _mensaje.value = if (valido) {
                "Inicio de sesión exitoso 🎮"
            } else {
                "Contraseña incorrecta"
            }
        }
    }

    // Limpia el mensaje (por si el usuario cambia los datos)
    fun limpiarMensaje() {
        _mensaje.value = ""
    }
}
