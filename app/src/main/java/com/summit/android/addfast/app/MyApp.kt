package com.summit.android.addfast.app

import android.app.Application
import android.content.Context
import com.bugsnag.android.Bugsnag
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.huawei.agconnect.auth.AGConnectAuth
import com.summit.android.addfast.repo.conexion.AdminRepository
import com.summit.android.addfast.repo.conexion.AuthRepository
import com.summit.android.addfast.repo.conexion.MainRepository
import com.summit.android.addfast.repo.local.AppDB
import com.summit.android.addfast.ui.auth.AuthViewModel
import com.summit.android.addfast.ui.main.MainViewModel
import com.summit.android.addfast.ui.main.admin.AdminViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.startKoin
import org.koin.dsl.module


class MyApp : Application() {

    companion object{
        private lateinit var instance: MyApp
        fun getInstanceApp(): MyApp = instance
        fun getContextApp(): Context = instance
        fun setInstance(instance: MyApp){
            Companion.instance =instance
        }

    }

    //mRtcEngine.enableVideo();
    //private void joinChannel() {
    //  // if you do not specify the uid, Agora will assign one.
    //  mRtcEngine.joinChannel(null, "demoChannel1", "Extra Optional Data", 0);
    //}
    override fun onCreate() {
        super.onCreate()
        setInstance(this)
        Bugsnag.start(this)
        startKoin {
            // Koin Android logger
            androidLogger()
            //inject Android context
            androidContext(this@MyApp)
            // use modules

            modules(module(override = true) {
                // Singleton ComponentA
                single <FirebaseFirestore> { FirebaseFirestore.getInstance()  }
                single<FirebaseStorage> { FirebaseStorage.getInstance() }
                single<AppDB> {  AppDB(get()) }
                single <AGConnectAuth>{ AGConnectAuth.getInstance()  }
                single<MainRepository> { MainRepository(get(), get(), get(), get()) }
                    factory<MainRepository> { MainRepository(get(), get(), get(), get()) }
                single<AuthRepository> { AuthRepository(get(), get(), get(), get())  }
                single<AdminRepository> { AdminRepository(get(), get(), get(), get()) }
                viewModel<MainViewModel> { MainViewModel(get()) }
                viewModel<AdminViewModel> { AdminViewModel(get()) }
                viewModel<AuthViewModel> { AuthViewModel(get()) }
            })
        }

    }
}