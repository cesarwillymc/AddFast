package com.summit.core.di.modules

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.huawei.agconnect.auth.AGConnectAuth
import com.summit.core.db.AppDB
import com.summit.core.db.dao.UbicacionModelDao
import com.summit.core.db.dao.UsuarioDao
import com.summit.core.network.repository.*
import com.summit.core.network.repository.AdRepositoryImpl
import com.summit.core.network.repository.AuthRepositoryImpl
import com.summit.core.network.repository.CategoryRepositoryImpl
import com.summit.core.network.repository.GpsRepositoryImpl
import com.summit.core.network.repository.OfferRepositoryImpl
import com.summit.core.network.repository.PostulateRepositoryImpl
import com.summit.core.network.repository.UserRepositoryImpl
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class RepositoryModule {
    @Singleton
    @Provides
    fun provideAdRepository(
        firestore: FirebaseFirestore,
        db: UbicacionModelDao,
        storage: FirebaseStorage
    ): AdRepository = AdRepositoryImpl(firestore, db, storage)

    @Singleton
    @Provides
    fun provideAuthRepository(
        api: AGConnectAuth,
        firebase: FirebaseFirestore,
        storage: FirebaseStorage
    ): AuthRepository = AuthRepositoryImpl(api, firebase, storage)

    @Singleton
    @Provides
    fun provideCategoryRepository(
        db: UbicacionModelDao, firestore: FirebaseFirestore,
    ): CategoryRepository = CategoryRepositoryImpl(db, firestore)

    @Singleton
    @Provides
    fun provideGpsRepository(
        db: UbicacionModelDao
    ): GpsRepository = GpsRepositoryImpl(db)

    @Singleton
    @Provides
    fun provideOfferRepository(
        db: UbicacionModelDao, firestore: FirebaseFirestore, storage: FirebaseStorage
    ): OfferRepository = OfferRepositoryImpl(db, firestore, storage)

    @Singleton
    @Provides
    fun providePostulateRepository(
        db: UbicacionModelDao, firestore: FirebaseFirestore, storage: FirebaseStorage
    ): PostulateRepository = PostulateRepositoryImpl(db, firestore, storage)

    @Singleton
    @Provides
    fun provideUserRepository(
        db: UsuarioDao, firestore: FirebaseFirestore, storage: FirebaseStorage, api: AGConnectAuth
    ): UserRepository = UserRepositoryImpl(db, firestore, storage, api)


}