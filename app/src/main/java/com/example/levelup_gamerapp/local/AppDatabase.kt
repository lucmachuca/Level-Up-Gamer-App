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

        /**
         * Obtiene una instancia singleton de la base de datos. Este método se
         * mantiene para compatibilidad con versiones anteriores del código. Internamente
         * delega a [getDatabase].
         */
        fun obtenerBaseDatos(context: Context): AppDatabase = getDatabase(context)

        /**
         * Devuelve la instancia única de [AppDatabase]. Si ya existe, la retorna,
         * de lo contrario la crea utilizando [Room.databaseBuilder]. Se utiliza
         * el mismo nombre de base de datos en toda la aplicación para evitar
         * inconsistencias.
         */
        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "levelup_db"
                )
                    .fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}