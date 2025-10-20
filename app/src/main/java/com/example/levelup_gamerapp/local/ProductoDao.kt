package com.example.levelup_gamerapp.local

import androidx.room.*

@Dao
interface ProductosDao {

    @Query("SELECT * FROM productos")
    suspend fun obtenerTodos(): List<ProductosEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertarProducto(producto: ProductosEntity)

    @Delete
    suspend fun eliminarProducto(producto: ProductosEntity)

    @Query("DELETE FROM productos")
    suspend fun eliminarTodos()

    //nuevo metodo para detalle producto
    @Query("SELECT * FROM productos WHERE id = :id LIMIT 1")
    suspend fun obtenerProductoPorId(id: Int): ProductosEntity?
}
