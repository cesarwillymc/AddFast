
package com.summit.android.addfast.di

import android.content.Context
import com.summit.android.addfast.app.MyApp
import dagger.Module
import dagger.Provides


@Module
class AppModule {


    @Provides
    fun provideContext(application: MyApp): Context = application.applicationContext
}
