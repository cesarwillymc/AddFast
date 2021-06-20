package com.summit.core.di

import android.content.Context
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.huawei.agconnect.auth.AGConnectAuth
import com.summit.core.db.dao.UbicacionModelDao
import com.summit.core.db.dao.UsuarioDao
import com.summit.core.di.modules.*
import com.summit.core.network.repository.*
import com.summit.core.style.ThemeUtils
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        ContextModule::class,
        NetworkModule::class,
        DatabaseModule::class,
        RepositoryModule::class,
        UtilsModule::class
    ]
)
interface CoreComponent {
    fun context(): Context

    fun userDao(): UsuarioDao
    fun ubicationDao(): UbicacionModelDao

    fun adRepository(): AdRepository
    fun authRepository(): AuthRepository
    fun categoryRepository(): CategoryRepository
    fun gpsRepository(): GpsRepository
    fun offerRepository(): OfferRepository
    fun postulateRepository(): PostulateRepository
    fun userRepository(): UserRepository

    fun firestore(): FirebaseFirestore
    fun storage(): FirebaseStorage
    fun huaweiAuth(): AGConnectAuth

    fun themeUtils(): ThemeUtils
}