package com.summit.android.addfast.app

import android.app.Application
import android.content.Context
import com.bugsnag.android.Bugsnag
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.huawei.agconnect.auth.AGConnectAuth
import com.huawei.agconnect.config.AGConnectServicesConfig
import com.huawei.agconnect.config.LazyInputStream
import com.summit.android.addfast.R
import com.summit.android.addfast.repo.conexion.AdminRepository
import com.summit.android.addfast.repo.conexion.AuthRepository
import com.summit.android.addfast.repo.conexion.MainRepository
import com.summit.android.addfast.repo.local.AppDB
import com.summit.android.addfast.ui.auth.AuthViewModelFactory
import com.summit.android.addfast.ui.main.MainViewModelFactory
import com.summit.android.addfast.ui.main.admin.AdminViewModel
import com.summit.android.addfast.ui.main.admin.AdminViewModelFactory
import io.agora.rtc.RtcEngine
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.androidXModule
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.provider
import org.kodein.di.generic.singleton
import java.io.IOException
import java.io.InputStream


class MyApp : Application(), KodeinAware {
    override val kodein= Kodein.lazy {

        //Module inilize
        import(androidXModule(this@MyApp))

        //Auth viewModel Kodein App
        bind() from provider { AuthRepository(instance(), instance(), instance(), instance()) }
        bind() from provider { AuthViewModelFactory(instance()) }
        bind() from provider { AdminRepository(instance(), instance(), instance(), instance()) }
        bind() from provider { AdminViewModelFactory(instance()) }
      /*  bind() from provider { ServiceViewModelFactory(instance(),instance()) }

        //Main viewModel Kodein App
        bind() from provider { RepositoryHistorial(instance(),instance()) }
        bind() from provider { RespositoryCategories(instance(),instance()) }*/
        bind() from provider { MainViewModelFactory(instance()) }
        bind() from provider { MainRepository(instance(), instance(), instance(), instance()) }

        //Instance App DB ROOM
        bind() from singleton { AppDB(instance()) }

        //Huawei Instance App
        bind() from singleton { AGConnectAuth.getInstance() }
        bind() from singleton { FirebaseStorage.getInstance() }

        //Firestore Instance App
        bind() from singleton { FirebaseFirestore.getInstance() }

    }
    companion object{
        private lateinit var instance: MyApp
        fun getInstanceApp(): MyApp = instance
        fun getContextApp(): Context = instance
        fun setInstance(instance: MyApp){
            Companion.instance =instance
        }

    }
    private fun inicializeCallingAgora(){
        try {
            //mRtcEngine = RtcEngine.create(baseContext, getString(R.string.agora_app_id), mRtcEventHandler)
        }catch (e:Exception){

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
    }

}