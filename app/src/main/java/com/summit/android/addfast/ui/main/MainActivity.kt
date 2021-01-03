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
import com.summit.android.addfast.repo.model.departamento.UbicacionModel
import com.summit.android.addfast.ui.camera.CameraViewModel
import com.summit.android.addfast.ui.main.user.SelectPlaceDialog
import com.summit.android.addfast.utils.verifyPermission
import kotlinx.android.synthetic.main.activity_main.*
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.kodein
import org.kodein.di.generic.instance

class MainActivity : BaseActivity(), KodeinAware {
    private lateinit var viewModel: MainViewModel
    override val kodein: Kodein by kodein()
    private val factory: MainViewModelFactory by instance()
    lateinit var navController: NavController
    lateinit var cameraViewModel: CameraViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = run {
            ViewModelProvider(this, factory).get(MainViewModel::class.java)
        }
        cameraViewModel= run{
            ViewModelProvider(this).get(CameraViewModel::class.java)
        }
        verifyPermission()
        setSupportActionBar(tollbar_main)
        observarPermisos()
        navController = findNavController(R.id.am_fragment)
        val appBarConfig = AppBarConfiguration(setOf(
            R.id.nav_postulaciones, R.id.nav_inicio, R.id.nav_perfil,R.id.nav_anuncios))
        setupActionBarWithNavController(navController, appBarConfig)
        nav_view.setupWithNavController(navController)
        supportActionBar!!.setDisplayShowTitleEnabled(false)



        navController.addOnDestinationChangedListener { controller, destination, arguments ->
            supportActionBar!!.setHomeAsUpIndicator(R.drawable.ic_arrow_back_ios_black)
            main_text.text=destination.label
            when (destination.id) {
                R.id.nav_postulaciones -> {
                    main_text.hide()
                    nav_view.show()
                    main_linear.show()
                    appBarLayout.hide()
                }
                R.id.nav_inicio -> {
                    main_text.hide()
                    main_linear.show()
                    nav_view.show()
                    appBarLayout.show()
                }
                R.id.galleryFragment2->{
                    nav_view.hide()
                    appBarLayout.hide()
                }
                R.id.nav_perfil -> {
                    main_text.hide()
                    main_linear.show()
                    appBarLayout.show()
                    nav_view.show()
                }

                R.id.nav_anuncios -> {
                    main_text.hide()
                    appBarLayout.hide()
                    nav_view.show()
                    main_linear.show()
                    cameraViewModel.imageSelect.postValue("")
                }

                else -> {
                    nav_view.hide()
                    appBarLayout.show()
                    main_linear.hide()
                    main_text.show()
                }
            }


        }
        viewModel.getUbicacion().observe(this, Observer {
            if(it!=null){
                main_ubicacion.text = "${it.departamento}-${it.provincia}"
            }else{
                viewModel.saveUbicacion(UbicacionModel("Puno","Puno",0))
            }

        })
        verificarAnuncios()
        main_linear.setOnClickListener {
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
            nav_view.menu.getItem(2).isVisible = false
            nav_view.menu.getItem(1).isVisible = false
            //R.id.nav_anuncios
            //R.id.nav_postulaciones
        }else{
            nav_view.menu.getItem(1).isVisible = true
            nav_view.menu.getItem(2).isVisible = usuario.ruc != ""
        }
    }
    //Find Nav controller UP
    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp()
    }
    //Layout get data
    override fun getLayout(): Int =R.layout.activity_main



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
}