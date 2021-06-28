package com.summit.core.di.modules

import android.content.Context
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.huawei.agconnect.auth.AGConnectAuth
import com.summit.core.BuildConfig
import com.summit.core.db.AppDB
import com.summit.core.network.api.RestApi
import dagger.Module
import dagger.Provides
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
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


    @Singleton
    @Provides
    fun provideHttpClient(): OkHttpClient {
        val clientBuilder = OkHttpClient.Builder()
        clientBuilder.addInterceptor {chain->
            val url = chain.request().url
                .newBuilder()
                .build()
            val request =
                chain.request().newBuilder().url(url).build()
            chain.proceed(request)
        }

        return clientBuilder.build()
    }

    @Singleton
    @Provides
    fun provideRetrofitBuilder(): Retrofit =
        Retrofit.Builder()
            .baseUrl(BuildConfig.FIREBASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    @Singleton
    @Provides
    fun provideApiService(retrofit: Retrofit): RestApi = retrofit.create(RestApi::class.java)


}