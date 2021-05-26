package com.summit.android.addfast.base

import android.app.Activity
import android.content.ActivityNotFoundException
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.ViewDataBinding
import com.google.android.material.snackbar.Snackbar
import com.summit.android.addfast.R
import java.io.File
import androidx.databinding.DataBindingUtil




abstract  class BaseActivity<B: ViewDataBinding>: AppCompatActivity(){
    protected lateinit var dataBinding: B
    protected open fun bindView(layoutId: Int) {
        dataBinding = DataBindingUtil.setContentView(this, layoutId)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        if(getLayout()!=null){
            setContentView(getLayout()!!)
        }

       // window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN)
    }
    @LayoutRes
    protected abstract fun getLayout():Int?
    fun hideKeyboard(){
        try{
            val view = this.currentFocus
            view!!.clearFocus()
            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(view.windowToken, 0)
        }catch (e :Exception){

        }
    }
    fun View.show(){
        visibility=View.VISIBLE
    }
    fun View.hide(){
        visibility=View.GONE
    }
    fun View.snackbar(message:String){
        Snackbar.make(this,message, Snackbar.LENGTH_LONG).also { snackbar ->
            snackbar.setAction("Ok"){
                snackbar.dismiss()
            }.show()
        }
    }
    fun navigateToActivity(intent:Intent) {
        Log.e("intent",intent.toString())
        intent.flags= Intent.FLAG_ACTIVITY_CLEAR_TASK
        intent.flags= Intent.FLAG_ACTIVITY_NEW_TASK
        startActivity(intent)
        finish()
    }
    fun showUpdateVersion(){
        val dialogBuilder= AlertDialog.Builder(this)
        dialogBuilder.setMessage("Existe una nueva actualizacion de esta aplicacion, por favor actualizala")
            .setCancelable(true)
            .setPositiveButton("Actualizar ahora",DialogInterface.OnClickListener { dialog, which ->
                openAppInPlayStore("com.thesummitdev.daloo.user")
                dialog.dismiss()
            })
        val alertDialog= dialogBuilder.create()
        alertDialog.setTitle("Actualizacion Disponible")
        alertDialog.show()
    }
    fun toast(mensaje:String){
        Toast.makeText(this,mensaje,Toast.LENGTH_LONG).show()
    }

    private fun openAppInPlayStore(appPackageName:String ){
        try {
            startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=$appPackageName")))
        }catch (e:ActivityNotFoundException){
            startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=$appPackageName")))
        }
    }
    companion object {

        /** Use external media if it is available, our app's file directory otherwise */
        fun getOutputDirectory(context: Context): File {
            val appContext = context.applicationContext
            val mediaDir = context.externalMediaDirs.firstOrNull()?.let {
                File(it, appContext.resources.getString(R.string.app_name)).apply { mkdirs() } }
            return if (mediaDir != null && mediaDir.exists())
                mediaDir else appContext.filesDir
        }
    }

}