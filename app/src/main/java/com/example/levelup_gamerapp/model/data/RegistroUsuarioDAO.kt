package com.example.levelup_gamerapp.model.data

import androidx.room.*

@Dao
interface RegistroUsuarioDAO {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertarUsuario(usuario: RegistroUsuarioEntity)

    @Query("SELECT * FROM levelup_database WHERE correo = :correo LIMIT 1")
    suspend fun buscarPorCorreo(correo: String): RegistroUsuarioEntity?

    @Query("DELETE FROM levelup_database WHERE correo = :correo")
    suspend fun eliminarPorCorreo(correo: String)
}
