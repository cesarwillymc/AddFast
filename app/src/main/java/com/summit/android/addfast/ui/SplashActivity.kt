package com.summit.android.addfast.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.lifecycle.ViewModelProvider
import com.summit.android.addfast.R
import com.summit.android.addfast.base.BaseActivity
import com.summit.android.addfast.ui.auth.AuthActivity
import com.summit.android.addfast.ui.auth.AuthViewModel
import com.summit.android.addfast.ui.auth.AuthViewModelFactory
import com.summit.android.addfast.ui.main.MainActivity
import com.summit.android.addfast.ui.main.MainViewModel
import com.summit.android.addfast.ui.main.admin.AdminActivity
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.kodein
import org.kodein.di.generic.instance
import org.koin.androidx.viewmodel.ext.android.viewModel

class SplashActivity : BaseActivity() {
    val viewModel: AuthViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Handler().postDelayed({
            verifyDataLogged()
        },1500L)
    }

    private fun verifyDataLogged() {
        val userData=viewModel.getDataDBstatic()
        if (userData!=null){
            if(userData.admin!!){
                navigateToActivity(Intent(this, AdminActivity::class.java))
            }else{
                navigateToActivity(Intent(this, MainActivity::class.java))
            }
        }else{
            navigateToActivity(Intent(this,MainActivity::class.java))
        }
    }
    private fun restorePrefData(): Boolean {
        val pref = applicationContext.getSharedPreferences(
                "myPrefs",
                Context.MODE_PRIVATE
        )
        return pref.getBoolean("isIntroOpnend", false)
    }

    override fun getLayout()= R.layout.activity_splash
}