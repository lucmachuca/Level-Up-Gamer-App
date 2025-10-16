package com.example.levelup_gamerapp.model.repository

import com.example.levelup_gamerapp.model.data.RegistroUsuarioDAO
import com.example.levelup_gamerapp.model.data.RegistroUsuarioEntity

class RegistroUsuarioRepository(private val dao: RegistroUsuarioDAO) {

    suspend fun registrarUsuario(usuario: RegistroUsuarioEntity){
        dao.insertarUsuario(usuario)
    }

    suspend fun verificarCorreo(correo: String): RegistroUsuarioEntity?{
    return dao.buscarPorCorreo(correo)
    }
}