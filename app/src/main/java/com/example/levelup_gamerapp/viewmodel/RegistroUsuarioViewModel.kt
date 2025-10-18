package com.example.levelup_gamerapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.levelup_gamerapp.model.data.RegistroUsuarioEntity
import com.example.levelup_gamerapp.model.repository.RegistroUsuarioRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch


class RegistroUsuarioViewModel(
    private val repository: RegistroUsuarioRepository
) : ViewModel() {

    private val _mensaje = MutableStateFlow("")
    val mensaje: StateFlow<String> = _mensaje

    fun registrar(
        nombre: String,
        apellido: String,
        correo: String,
        contrasena: String,
        edad: Int
    ) {
        viewModelScope.launch {

            // 🔹 PRUEBA TEMPORAL: para verificar que el ViewModel recibe los datos
            _mensaje.value = "Procesando registro de: $nombre"


            //Validación: campos vacíos
            if (nombre.isBlank() || apellido.isBlank() || correo.isBlank() || contrasena.isBlank()) {
                _mensaje.value = "Completa todos los campos del formulario"
                return@launch
            }

            //Validación: nombre y apellido deben contener solo letras y espacios
            val soloLetras = Regex("^[A-Za-zÁÉÍÓÚáéíóúÑñ ]+$")
            if (!soloLetras.matches(nombre)) {
                _mensaje.value = "El nombre solo puede contener letras"
                return@launch
            }
            if (!soloLetras.matches(apellido)) {
                _mensaje.value = "El apellido solo puede contener letras"
                return@launch
            }

            //Validación: formato correcto de correo electrónico
            val correoRegex = Regex("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}$")
            if (!correoRegex.matches(correo)) {
                _mensaje.value = "Correo electrónico inválido"
                return@launch
            }

            // Validación: contraseña segura
            // Debe tener al menos 6 caracteres y combinar letras y números
            val contrasenaSegura = Regex("^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{6,}$")
            if (!contrasenaSegura.matches(contrasena)) {
                _mensaje.value =
                    "La contraseña debe tener al menos 6 caracteres e incluir letras y números"
                return@launch
            }

            // Validación: edad mínima (mayor o igual a 18 años)
            if (edad < 18) {
                _mensaje.value = "Debes ser mayor de 18 años para registrarte"
                return@launch
            }

            // Validación: correo duplicado
            val existente = repository.verificarCorreo(correo)
            if (existente != null) {
                _mensaje.value = "El correo ya está registrado en el sistema"
                return@launch
            }

            // Regla de negocio: descuento por correo institucional
            val dominiosDescuento = listOf(
                "@duocuc.cl",
                "@profesor.duocuc.cl",
                "@alumnos.duoc.cl",
                "@duoc.cl"
            )

            val descuento = if (dominiosDescuento.any {
                    correo.endsWith(it, ignoreCase = true)
                }) 20 else 0

            // Crear entidad con descuento aplicado (si corresponde)
            val usuario = RegistroUsuarioEntity(
                nombre = nombre.trim(),
                apellido = apellido.trim(),
                correo = correo.trim(),
                contrasena = contrasena,
                edad = edad,
                descuentoAplicado = descuento
            )

            // Registrar usuario en base de datos local
            repository.registrarUsuario(usuario)
            delay(100)

            // Mensaje de confirmación visible en la UI
            _mensaje.value = if (descuento > 0)
                "Registro exitoso. Se aplicó un descuento del 20 % por correo institucional"
            else
                "Registro exitoso"
        }
    }
}
