package com.example.levelup_gamerapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.levelup_gamerapp.model.repository.LoginRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

// 🧩 ViewModel que maneja la lógica de inicio de sesión
class LoginViewModel(private val repository: LoginRepository) : ViewModel() {

    private val _mensaje = MutableStateFlow("")
    val mensaje: StateFlow<String> = _mensaje

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

    fun limpiarMensaje() {
        _mensaje.value = ""
    }
}
