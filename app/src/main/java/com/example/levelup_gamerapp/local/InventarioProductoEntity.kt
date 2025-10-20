package com.example.levelup_gamerapp.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "inventario")
data class InventarioProductoEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val productoId: Int,
    val cantidad: Int,
    val estado: String
)
