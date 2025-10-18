package com.example.levelup_gamerapp.model.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [RegistroUsuarioEntity::class], version = 2, exportSchema = false)
abstract class AppDataBase : RoomDatabase() {

    abstract fun registroUsuarioDao(): RegistroUsuarioDAO

    companion object {
        @Volatile
        private var INSTANCE: AppDataBase? = null

        fun getDatabase(context: Context): AppDataBase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDataBase::class.java,
                    "levelup_database"
                )
                    .fallbackToDestructiveMigration() // ✅ evita crash al cambiar schema
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}
