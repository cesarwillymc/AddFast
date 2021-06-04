package com.summit.core.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.summit.core.network.model.Usuario
import com.summit.core.network.model.departamento.UbicacionModel
import com.summit.core.BuildConfig
import com.summit.core.db.dao.UbicacionModelDao
import com.summit.core.db.dao.UsuarioDao
import com.summit.core.db.migration.MIGRATION_1_2

@Database(entities = [Usuario::class, UbicacionModel::class],
    exportSchema = BuildConfig.ADDFAST_DATABASE_EXPORT_SCHEMA,
    version = BuildConfig.ADDFAST_DATABASE_VERSION,)

abstract class AppDB : RoomDatabase() {
    abstract fun usuarioDao(): UsuarioDao
    abstract fun ubicacionModelDao(): UbicacionModelDao

}