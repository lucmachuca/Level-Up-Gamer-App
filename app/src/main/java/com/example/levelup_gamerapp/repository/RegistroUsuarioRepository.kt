package com.example.levelup_gamerapp.repository

import com.example.levelup_gamerapp.local.RegistroUsuarioDAO
import com.example.levelup_gamerapp.local.RegistroUsuarioEntity

class RegistroUsuarioRepository(private val dao: RegistroUsuarioDAO) {

    suspend fun registrarUsuario(usuario: RegistroUsuarioEntity) =
        dao.insertarUsuario(usuario)

    suspend fun verificarCorreo(correo: String) =
        dao.buscarPorCorreo(correo)

    suspend fun eliminarPorCorreo(correo: String) =
        dao.eliminarPorCorreo(correo)
}