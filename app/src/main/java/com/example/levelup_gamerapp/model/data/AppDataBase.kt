package com.example.levelup_gamerapp.model.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

import com.example.levelup_gamerapp.model.data.RegistroUsuarioEntity
import  com.example.levelup_gamerapp.model.data.RegistroUsuarioDAO

@Database(entities = [RegistroUsuarioEntity::class], version = 1, exportSchema = false)]
})
abstract class AppDataBase: RoomDatabase() {

    abstract fun RegistroUsuarioDAO(): RegistroUsuarioDAO

    companion object {
        @Volatile
        private var INSTANCE: AppDataBase? = null

        fun getDatabase(context: Context): AppDataBase =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?:  Room.databaseBuilder(
                    context.applicationContext,
                    AppDataBase::class.java,
                    "levelup_db"
                ).build()
                    .also { INSTANCE = it}
            }
        }
    }
