package com.example.levelup_gamerapp.local

import androidx.room.*

@Dao
interface InventarioProductoDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertarInventario(inventario: InventarioProductoEntity)

    @Query("SELECT * FROM inventario ORDER BY id DESC")
    suspend fun obtenerInventario(): List<InventarioProductoEntity>

    @Update
    suspend fun actualizarInventario(inventario: InventarioProductoEntity)

    @Delete
    suspend fun eliminarInventario(inventario: InventarioProductoEntity)
}
