package com.summit.android.addfast.ui.main

import androidx.appcompat.app.AppCompatActivity
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
import com.summit.android.addfast.databinding.ActivityMainBinding
import com.summit.android.addfast.repo.model.departamento.UbicacionModel
import com.summit.android.addfast.ui.camera.CameraViewModel
import com.summit.android.addfast.ui.main.user.SelectPlaceDialog
import com.summit.android.addfast.utils.verifyPermission
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.kodein
import org.kodein.di.generic.instance
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : BaseActivity<ActivityMainBinding>() {
    val viewModel: MainViewModel by viewModel()
    lateinit var navController: NavController
    private lateinit var binding: ActivityMainBinding
    lateinit var cameraViewModel: CameraViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        cameraViewModel= run{
            ViewModelProvider(this).get(CameraViewModel::class.java)
        }
        bindView(R.layout.activity_main)
        verifyPermission()
        setSupportActionBar(dataBinding.tollbar_main)
        observarPermisos()
        inicializarVariables()
        navController = findNavController(R.id.am_fragment)
        val appBarConfig = AppBarConfiguration(setOf(
            R.id.nav_postulaciones, R.id.nav_inicio, R.id.nav_perfil,R.id.nav_anuncios))
        setupActionBarWithNavController(navController, appBarConfig)
        dataBinding.nav_view.setupWithNavController(navController)
        supportActionBar!!.setDisplayShowTitleEnabled(false)



        navController.addOnDestinationChangedListener { controller, destination, arguments ->
            supportActionBar!!.setHomeAsUpIndicator(R.drawable.ic_arrow_back_ios_black)
            dataBinding.main_text.text=destination.label
            when (destination.id) {
                R.id.nav_postulaciones -> {
                    dataBinding.main_text.hide()
                    dataBinding.nav_view.show()
                    dataBinding.main_linear.show()
                    dataBinding.appBarLayout.hide()
                }
                R.id.nav_inicio -> {
                    dataBinding.main_text.hide()
                    dataBinding.main_linear.show()
                    dataBinding.nav_view.show()
                    dataBinding.appBarLayout.show()
                }
                R.id.galleryFragment2->{
                    nav_view.hide()
                    dataBinding.appBarLayout.hide()
                }
                R.id.nav_perfil -> {
                    dataBinding.main_text.hide()
                    dataBinding.main_linear.show()
                    dataBinding.appBarLayout.show()
                    dataBinding.nav_view.show()
                }

                R.id.nav_anuncios -> {
                    dataBinding.main_text.hide()
                    dataBinding.appBarLayout.hide()
                    dataBinding.nav_view.show()
                    dataBinding.main_linear.show()
                    cameraViewModel.imageSelect.postValue("")
                }

                else -> {
                    dataBinding.nav_view.hide()
                    dataBinding.appBarLayout.show()
                    dataBinding.main_linear.hide()
                    dataBinding.main_text.show()
                }
            }


        }

        verificarAnuncios()






    }
    private fun inicializarVariables(){
        viewModel.getUbicacion().observe(this, Observer {
            if(it!=null){
                dataBinding.main_ubicacion.text = "${it.departamento}-${it.provincia}"
            }else{
                viewModel.saveUbicacion(UbicacionModel("Puno","Puno",0))
            }

        })
        dataBinding.imageView23.setOnClickListener {
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
        dataBinding.main_ubicacion.setOnClickListener {
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
    private fun verificarAnuncios(){
        val usuario=viewModel.getStaticDataUser()
        if (usuario==null){
            dataBinding.nav_view.menu.getItem(2).isVisible = false
            dataBinding.nav_view.menu.getItem(1).isVisible = false
            //R.id.nav_anuncios
            //R.id.nav_postulaciones
        }else{
            dataBinding.nav_view.menu.getItem(1).isVisible = true
            dataBinding.nav_view.menu.getItem(2).isVisible = usuario.ruc != ""
        }
    }
    //Find Nav controller UP
    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp()
    }
    //Layout get data




    //Obserbar permisos de datastore
    var permissosVariables=false
    private fun observarPermisos() {
        Handler().postDelayed({
            viewModel.getDataPermission.observe(this, Observer {
                Log.e("permissos", it.toString())
                permissosVariables = it
            })
        }, 1000L)
    }

    override fun getLayout(): Int? {
        return null
    }
}