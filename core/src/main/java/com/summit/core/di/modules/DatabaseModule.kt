package com.summit.core.di.modules

import android.content.Context
import androidx.room.Room
import com.summit.core.BuildConfig
import com.summit.core.db.AppDB
import com.summit.core.db.migration.MIGRATION_1_2
import dagger.Module
import dagger.Provides
import javax.inject.Singleton


@Module
class DatabaseModule {

    @Singleton
    @Provides
    fun provideMarvelDatabase(context: Context) =
        Room.databaseBuilder(context, AppDB::class.java, BuildConfig.ADDFAST_DATABASE_NAME)
            .allowMainThreadQueries()
            .addMigrations(MIGRATION_1_2)
            .fallbackToDestructiveMigration()
            .build()
    @Singleton
    @Provides
    fun provideUbicacionModelDao(appdb: AppDB) =
        appdb.ubicacionModelDao()

    @Singleton
    @Provides
    fun provideUsuarioDao(appdb: AppDB) =
        appdb.usuarioDao()



}
