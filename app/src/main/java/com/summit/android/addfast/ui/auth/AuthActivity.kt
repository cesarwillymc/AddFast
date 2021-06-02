package com.summit.android.addfast.ui.auth

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import com.summit.android.addfast.R
import com.summit.android.addfast.base.BaseActivity
import com.summit.android.addfast.ui.camera.CameraViewModel
import com.summit.android.addfast.utils.verifyPermissionStatus
import kotlinx.android.synthetic.main.activity_auth.*
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.kodein
import org.kodein.di.generic.instance


class AuthActivity :BaseActivity() {
    lateinit var viewModel: CameraViewModel


    private lateinit var navController: NavController
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel= run{
            ViewModelProvider(this).get(CameraViewModel::class.java)
        }
        setSupportActionBar(toolbar_inicio)

        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.inicioFragment
            )
        )
        navController= findNavController(R.id.nav_frag_auth)
        setupActionBarWithNavController(navController, appBarConfiguration)
        navController.addOnDestinationChangedListener { controller, destination, arguments ->
            try{
                verifyPermissionStatus()
                    name_lbl_toolbar.text= destination.label
                    when(destination.id){

                        R.id.inicioFragment -> {
                            viewModel.imageSelect.postValue("")
                            appBarLayout.hide()
                        }
                        R.id.galleryFragment -> {
                            appBarLayout.hide()
                        }
                        R.id.cameraFragment -> {
                            appBarLayout.hide()
                        }
                        R.id.registerInfoFragment->{
                            supportActionBar!!.setHomeAsUpIndicator(R.drawable.ic_arrow_back_ios_white)
                            appBarLayout.show()
                        }
                        else->{
                            viewModel.imageSelect.postValue("")
                            supportActionBar!!.setHomeAsUpIndicator(R.drawable.ic_arrow_back_ios_white)
                            appBarLayout.show()
                        }
                    }

            }catch (e: Exception){
                toast("Necesitas Activar los permisos manualmente")
            }
        }

    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp()
    }


    override fun getLayout()= R.layout.activity_auth
}