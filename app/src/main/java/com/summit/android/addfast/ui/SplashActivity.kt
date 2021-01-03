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
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.kodein
import org.kodein.di.generic.instance

class SplashActivity : BaseActivity(), KodeinAware {
    override val kodein: Kodein by kodein()
    lateinit var viewModel: AuthViewModel
    private val factory: AuthViewModelFactory by instance()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel= run{
            ViewModelProvider(this,factory).get(AuthViewModel::class.java)
        }
        Handler().postDelayed({
            verifyDataLogged()
        },1500L)
    }

    private fun verifyDataLogged() {
        /*
        if (!restorePrefData()) {
            val mainActivity = Intent(applicationContext, IntroActivity::class.java)
            startActivity(mainActivity)
            finish()
        }else{

        }*/
       /* val userData=viewModel.getDataDBstatic()
        if (userData!=null){
            navigateToActivity(Intent(this, MainActivity::class.java))
        }else{
            navigateToActivity(Intent(this,AuthActivity::class.java))
        }*/
        navigateToActivity(Intent(this,MainActivity::class.java))

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