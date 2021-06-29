package com.summit.core.di.modules


import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.huawei.agconnect.auth.AGConnectAuth
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class NetworkModule {
    @Singleton
    @Provides
    fun provideFirestore() = FirebaseFirestore.getInstance()

    @Singleton
    @Provides
    fun provideStorage() = FirebaseStorage.getInstance()

    @Singleton
    @Provides
    fun provideHuaweiAuth(): AGConnectAuth = AGConnectAuth.getInstance()






}