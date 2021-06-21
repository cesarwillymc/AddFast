package com.summit.android.addfast.ui


import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.summit.android.addfast.R


class SplashActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        Log.e("activity","onCreate")
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
    }

    override fun onStart() {
        Log.e("activity","onStart")
        super.onStart()
    }

    override fun onResume() {
        Log.e("activity","onResume")
        super.onResume()

    }

    override fun onPause() {
        Log.e("activity","onPause")
        super.onPause()

    }

    override fun onStop() {
        Log.e("activity","onStop")
        super.onStop()

    }


    override fun onDestroy() {
        Log.e("activity","onDestroy")
        super.onDestroy()
    }

}