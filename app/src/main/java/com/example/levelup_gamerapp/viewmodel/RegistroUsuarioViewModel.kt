package com.example.levelup_gamerapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import com.example.levelup_gamerapp.model.data.RegistroUsuarioEntity
import com.example.levelup_gamerapp.model.repository.RegistroUsuarioRepository
import kotlinx.coroutines.launch
import kotlinx.coroutines.flow.*


class RegistroUsuarioViewModel(private val repository: RegistroUsuarioRepository): ViewModel(){

    private val _mensaje = MutableStateFlow("")
    val mensaje: MutableStateFlow<String> = _mensaje

    fun registrar(nombre: String, apellido: String, correo: String, contrasena: String,edad: Int){
        viewModelScope.launch {

            if(nombre.isBlank() || apellido.isBlank() || correo.isBlank() || contrasena.isBlank()){
                _mensaje.value = "Por favor, complete todos los campos"
                return@launch
            }

            if(edad < 18){
                _mensaje.value = "Debes ser mayor de edad"
                return@launch
            }

            val existente = repository.verificarCorreo(correo)
            if(existente != null){
                _mensaje.value = "El correo ya está registrado"
                return@launch
            }
            val usuario = RegistroUsuarioEntity(
                nombre=nombre,
                apellido=apellido,
                correo=correo,
                contrasena=contrasena,
                edad=edad
            )
            repository.registrarUsuario(usuario)
            _mensaje.value = "Registro exitoso"
        }
    }
}