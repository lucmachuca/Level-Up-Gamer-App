package com.example.levelup_gamerapp.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface RegistroUsuarioDAO {

    @Insert(onConflict = OnConflictStrategy.Companion.IGNORE)
    suspend fun insertarUsuario(usuario: RegistroUsuarioEntity)

    @Query("SELECT * FROM levelup_database WHERE correo = :correo LIMIT 1")
    suspend fun buscarPorCorreo(correo: String): RegistroUsuarioEntity?

    @Query("DELETE FROM levelup_database WHERE correo = :correo")
    suspend fun eliminarPorCorreo(correo: String)
}