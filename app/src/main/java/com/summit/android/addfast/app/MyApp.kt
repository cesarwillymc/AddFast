package com.summit.android.addfast.app

import android.app.Application
import android.content.Context
import android.os.StrictMode
import com.bugsnag.android.Bugsnag
import com.summit.android.addfast.BuildConfig
import com.summit.android.addfast.di.DaggerAppComponent
import com.summit.core.di.CoreComponent
import com.summit.core.di.DaggerCoreComponent
import com.summit.core.di.modules.ContextModule
import com.summit.core.style.ThemeUtils
import javax.inject.Inject


class MyApp : Application() {
    lateinit var coreComponent: CoreComponent

    @Inject
    lateinit var themeUtils: ThemeUtils
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
        //themeUtils.setNightMode(true)

    }
    private fun turnOnStrictMode() {
        if (BuildConfig.DEBUG) {
            StrictMode.setThreadPolicy(
                StrictMode.ThreadPolicy.Builder()
                    .detectAll()
                    .penaltyLog()
                    .penaltyDeath().build())
            StrictMode.setVmPolicy(
                StrictMode.VmPolicy.Builder()
                    .detectAll()
                    .penaltyLog()
                    .penaltyDeath().build())
        }
    }

    private fun permitDiskReads(func: () -> Any) : Any {
        return if (BuildConfig.DEBUG) {
            val oldThreadPolicy = StrictMode.getThreadPolicy()
            StrictMode.setThreadPolicy(
                StrictMode.ThreadPolicy.Builder(oldThreadPolicy)
                    .permitDiskReads().build())
            val anyValue = func()
            StrictMode.setThreadPolicy(oldThreadPolicy)

            anyValue
        } else {
            func()
        }
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