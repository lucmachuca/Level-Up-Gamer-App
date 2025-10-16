package com.example.levelup_gamerapp.model.data

import androidx.room.*

@Dao
interface RegistroUsuarioDAO {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertarUsuario(usuario: RegistroUsuarioEntity)

    @Query("SELECT * FROM usuario WHERE correo = :correo LIMIT 1")
    suspend fun buscarPorCorreo(correo: String): RegistroUsuarioEntity?
}