package com.example.levelup_gamerapp.model.data

import androidx.room.*

@Entity(tableName = "usuario")
data class RegistroUsuarioEntity(@PrimaryKey(autoGenerate = true)
       val id: Int = 0,
       val nombre: String,
       val apellido: String,
       val correo: String,
       val contrasena: String,
       val edad : Int
)