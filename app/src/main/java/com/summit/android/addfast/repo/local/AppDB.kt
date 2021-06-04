/*
package com.summit.android.addfast.repo.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.summit.android.addfast.app.MyApp.Companion.getContextApp
import com.summit.android.addfast.repo.model.Usuario
import com.summit.android.addfast.repo.model.departamento.UbicacionModel
import com.summit.android.addfast.utils.Constants


@Database(entities = [Usuario::class, UbicacionModel::class], version = 5)

abstract class AppDB : RoomDatabase() {
    abstract val usuarioDao: UsuarioDao
    abstract val ubicacionModelDao: UbicacionModelDao

    companion object {
        @Volatile
        private var INSTANCE: AppDB? = null
        private val LOCK = Any()
        operator fun invoke(context: Context) = INSTANCE ?: synchronized(LOCK) {
            INSTANCE ?: buildDatabase(context)
        }

        private fun buildDatabase(context: Context) = Room.databaseBuilder(context, AppDB::class.java, Constants.NAME_DATABASE)
                .allowMainThreadQueries()
                .fallbackToDestructiveMigration()
                .build()



    }
}
 */