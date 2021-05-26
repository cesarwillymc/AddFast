package com.summit.android.addfast.ui.main.admin

import android.os.Bundle
import android.os.Handler
import android.util.Log
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.summit.android.addfast.R
import com.summit.android.addfast.base.BaseActivity
import com.summit.android.addfast.repo.model.departamento.UbicacionModel
import com.summit.android.addfast.ui.camera.CameraViewModel
import com.summit.android.addfast.ui.main.user.SelectPlaceDialog
import com.summit.android.addfast.utils.verifyPermission
import kotlinx.android.synthetic.main.activity_admin.*
import kotlinx.android.synthetic.main.activity_admin.imageView23
import org.koin.androidx.viewmodel.ext.android.viewModel

class AdminActivity : BaseActivity() {
    val viewModel: AdminViewModel by viewModel()
    lateinit var navController: NavController
    lateinit var cameraViewModel: CameraViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        cameraViewModel= run{
            ViewModelProvider(this).get(CameraViewModel::class.java)
        }
        verifyPermission()
        setSupportActionBar(tollbar_admin)
        observarPermisos()
        inicializarVariables()
        navController = findNavController(R.id.am_fragment)
        val appBarConfig = AppBarConfiguration(setOf(
            R.id.nav_admin_anuncios, R.id.nav_admin_usuarios, R.id.nav_admin_perfil,R.id.nav_promocion))
        setupActionBarWithNavController(navController, appBarConfig)
        nav_view_admin.setupWithNavController(navController)
        supportActionBar!!.setDisplayShowTitleEnabled(false)



        navController.addOnDestinationChangedListener { controller, destination, arguments ->
            supportActionBar!!.setHomeAsUpIndicator(R.drawable.ic_arrow_back_ios_black)
            admin_text.text=destination.label
            when (destination.id) {
                R.id.nav_admin_anuncios -> {
                    admin_text.hide()
                    admin_linear.show()
                    nav_view_admin.show()
                    appBarLayout_admin.show()
                }
                R.id.nav_admin_usuarios -> {
                    admin_text.hide()
                    admin_linear.show()
                    nav_view_admin.show()
                    appBarLayout_admin.show()
                }
                R.id.nav_promocion -> {
                    admin_text.hide()
                    admin_linear.show()
                    nav_view_admin.show()
                    appBarLayout_admin.show()
                    cameraViewModel.imageSelect.postValue("")
                }

                R.id.nav_admin_perfil -> {
                    admin_text.hide()
                    admin_linear.show()
                    nav_view_admin.show()
                    appBarLayout_admin.show()
                }

                R.id.galleryFragment3->{
                    nav_view_admin.hide()
                    appBarLayout_admin.hide()
                }

                else -> {
                    nav_view_admin.hide()
                    appBarLayout_admin.show()
                    admin_linear.hide()
                    admin_text.show()
                }
            }


        }







    }
    private fun inicializarVariables(){
        viewModel.getUbicacion().observe(this, Observer {
            if(it!=null){
                admin_ubicacion.text = "${it.departamento}-${it.provincia}"
            }else{
                viewModel.saveUbicacion(UbicacionModel("Puno","Puno",0))
            }

        })
        imageView23.setOnClickListener {
            try {
                toast("Cargando..")
                val newFragment = SelectPlaceDialog()
                newFragment.isCancelable=true
                newFragment!!.show(
                    supportFragmentManager.beginTransaction(),
                    "dialog"
                )
            }catch (e: IllegalStateException){
                Log.e("ilegal nav", e.message!!)
            }
        }
        admin_ubicacion.setOnClickListener {
            try {
                toast("Cargando..")
                val newFragment = SelectPlaceDialog()
                newFragment.isCancelable=true
                newFragment!!.show(
                    supportFragmentManager.beginTransaction(),
                    "dialog"
                )
            }catch (e: IllegalStateException){
                Log.e("ilegal nav", e.message!!)
            }
        }


    }
    //Find Nav controller UP
    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp()
    }
    var permissosVariables=false
    private fun observarPermisos() {
        Handler().postDelayed({
            viewModel.getDataPermission.observe(this, Observer {
                Log.e("permissos", it.toString())
                permissosVariables = it
            })
        }, 1000L)
    }
    override fun getLayout() = R.layout.activity_admin
}