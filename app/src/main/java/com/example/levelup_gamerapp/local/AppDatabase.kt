package com.example.levelup_gamerapp.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.levelup_gamerapp.local.RegistroUsuarioEntity
import com.example.levelup_gamerapp.local.RegistroUsuarioDAO

@Database(
    entities = [
        ProductosEntity::class,
        CarritoEntity::class,
        RegistroUsuarioEntity::class // 🔹 Añadimos el registro de usuarios
    ],
    version = 3, // 🔹 Subimos la versión para forzar recreación
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun productosDao(): ProductosDao
    abstract fun carritoDao(): CarritoDao
    abstract fun registroUsuarioDao(): RegistroUsuarioDAO

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun obtenerBaseDatos(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "levelup_db" // ✅ Mantenemos un único nombre de BD
                )
                    .fallbackToDestructiveMigration() // 🔹 Reconstruye si cambió el esquema
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}
