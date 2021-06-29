package com.summit.android.addfast.app

import android.app.Application
import android.content.Context
import com.bugsnag.android.Bugsnag
import com.summit.android.addfast.di.DaggerAppComponent
import com.summit.core.di.CoreComponent
import com.summit.core.di.DaggerCoreComponent
import com.summit.core.di.modules.ContextModule


class MyApp : Application() {
    lateinit var coreComponent: CoreComponent

    companion object {


        @JvmStatic
        fun coreComponent(context: Context) =
            (context.applicationContext as? MyApp)?.coreComponent
    }


    override fun onCreate() {
        super.onCreate()
        Bugsnag.start(this)
        initCoreDependencyInjection()
        initAppDependencyInjection()

    }


    private fun initCoreDependencyInjection() {
        coreComponent = DaggerCoreComponent
            .builder()
            .contextModule(ContextModule(this))
            .build()
    }

    private fun initAppDependencyInjection() {
        DaggerAppComponent
            .builder()
            .coreComponent(coreComponent)
            .build()
            .inject(this)
    }
}