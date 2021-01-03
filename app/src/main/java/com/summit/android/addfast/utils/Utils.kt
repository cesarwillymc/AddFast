package com.summit.android.addfast.utils

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.Canvas
import android.net.Uri
import android.os.Handler
import android.provider.Settings
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.annotation.DrawableRes
import androidx.core.content.ContextCompat
import androidx.datastore.DataStore
import androidx.datastore.preferences.Preferences
import androidx.datastore.preferences.createDataStore
import androidx.datastore.preferences.edit
import androidx.datastore.preferences.preferencesKey
import androidx.viewpager2.widget.ViewPager2
import com.google.android.gms.maps.model.BitmapDescriptor
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.material.snackbar.Snackbar
import com.karumi.dexter.Dexter
import com.karumi.dexter.MultiplePermissionsReport
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.multi.MultiplePermissionsListener
import com.summit.android.addfast.R
import com.summit.android.addfast.app.MyApp
import com.summit.android.addfast.utils.lifeData.Coroutines
import com.summit.android.addfast.utils.system.OnSingleClickListener
import kotlinx.coroutines.*
import java.lang.Runnable
import java.lang.reflect.InvocationTargetException
import java.lang.reflect.Method
import java.net.URLEncoder
import java.security.InvalidParameterException
import java.util.*

val dataStore: DataStore<Preferences> = MyApp.getContextApp().createDataStore(
        name = "settings"
)

suspend fun activePermission() {
    Log.e("activePermission", "entro")

    val permission = preferencesKey<Boolean>("permissionComplete")
    dataStore.edit { settings ->
        settings[permission] = true
    }
}

suspend fun desactivePermission() {
    Log.e("desactivePermission", "entro")
    val permission = preferencesKey<Boolean>("permissionComplete")
    dataStore.edit { settings ->
        settings[permission] = false
    }
}

fun Context.verifyPermission() {

    Dexter.withContext(this).withPermissions(
            listOf(
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE,
                    Manifest.permission.READ_EXTERNAL_STORAGE,
                    Manifest.permission.RECORD_AUDIO,
                    Manifest.permission.CAMERA
            )
    ).withListener(object : MultiplePermissionsListener {
        override fun onPermissionsChecked(p0: MultiplePermissionsReport?) {

            if (p0!!.areAllPermissionsGranted()) {
                Coroutines.io {
                    activePermission()
                }
            }
        }

        override fun onPermissionRationaleShouldBeShown(
                p0: MutableList<com.karumi.dexter.listener.PermissionRequest>?,
                p1: PermissionToken?
        ) {
            Coroutines.io {
                desactivePermission()
            }
            p1!!.continuePermissionRequest()

        }
    }).check()

}

fun Context.verifyPermissionStatus(): Boolean {
    var value = false
    Dexter.withContext(this).withPermissions(
            listOf(
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE,
                    Manifest.permission.READ_EXTERNAL_STORAGE,
                    Manifest.permission.RECORD_AUDIO,
                    Manifest.permission.CAMERA
            )
    ).withListener(object : MultiplePermissionsListener {
        override fun onPermissionsChecked(p0: MultiplePermissionsReport?) {

            if (p0!!.areAllPermissionsGranted()) {
                value = true
            }
        }

        override fun onPermissionRationaleShouldBeShown(
                p0: MutableList<com.karumi.dexter.listener.PermissionRequest>?,
                p1: PermissionToken?
        ) {
            p1!!.continuePermissionRequest()

        }
    }).check()
    return value
}


fun View.setOnSingleClickListener(l: View.OnClickListener) {
    setOnClickListener(OnSingleClickListener(l))
}

fun View.setOnSingleClickListener(l: (View) -> Unit) {
    setOnClickListener(OnSingleClickListener(l))
}

//Version EMIU
fun readEMUIVersion(): String {
    try {
        val propertyClass = Class.forName("android.os.SystemProperties")
        val method: Method = propertyClass.getMethod("get", String::class.java)
        var versionEmui = method.invoke(propertyClass, "ro.build.version.emui") as String
        if (versionEmui.startsWith("EmotionUI_")) {
            versionEmui = versionEmui.substring(10, versionEmui.length)
        }
        return versionEmui + "0"
    } catch (e: ClassNotFoundException) {
        e.printStackTrace()
    } catch (e: NoSuchMethodException) {
        e.printStackTrace()
    } catch (e: IllegalAccessException) {
        e.printStackTrace()
    } catch (e: InvocationTargetException) {
        e.printStackTrace()
    } catch (e: InvalidParameterException) {
        e.printStackTrace()
    }
    return "0.0"
}


fun ViewPager2.autoScroll(interval: Long) {
    var scrollPosition = 0
    val handler= Handler()
    val runnable = object : Runnable {

        override fun run() {

            /**
             * Calculate "scroll position" with
             * adapter pages count and current
             * value of scrollPosition.
             */
            try{
                val count = adapter?.itemCount ?: 0

                setCurrentItem(scrollPosition++ % count, true)
                Log.e("autoScroll","$count   scrollPosition: $scrollPosition")
                handler.postDelayed(this, interval)
            }catch (e:Exception){

            }
        }
    }
    val registerCallbak = object: ViewPager2.OnPageChangeCallback() {
        override fun onPageSelected(position: Int) {
            // Updating "scroll position" when user scrolls manually
            scrollPosition = position + 1
            Log.e("autoScroll","change page scrollPosition: $scrollPosition")
        }

        override fun onPageScrollStateChanged(state: Int) {
            // Not necessary
        }

        override fun onPageScrolled(
            position: Int,
            positionOffset: Float,
            positionOffsetPixels: Int
        ) {
            // Not necessary
        }
    }
    registerCallbak?.let {
        registerOnPageChangeCallback(it)
    }

    runnable?.let {
        handler.post(it)
    }

}
fun <T> lazyDeferred(block: suspend CoroutineScope.() -> T): Lazy<Deferred<T>>{
    return lazy {
        GlobalScope.async(start = CoroutineStart.LAZY){
            block.invoke(this)
        }
    }
}

fun Context.sendMessageWhatsApp(phone: String, message: String){
    val packageManager: PackageManager = packageManager
    val i = Intent(Intent.ACTION_VIEW)

    try {
        val url =
            "https://api.whatsapp.com/send?phone=51$phone&text=" + URLEncoder.encode(
                message,
                "UTF-8"
            )
        i.setPackage("com.whatsapp")
        i.data = Uri.parse(url)
        if (i.resolveActivity(packageManager) != null) {
            startActivity(i)
        }
    } catch (e: Exception) {
        e.printStackTrace()
    }
}

fun Context.callNumber(telefono: String){
    startActivity(Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", telefono, null)))
}
fun Context.bitmapDescriptorFromVector(
    @DrawableRes vectorDrawableResourceId: Int
): BitmapDescriptor? {
    val background =
        ContextCompat.getDrawable(this, vectorDrawableResourceId)
    background!!.setBounds(0, 0, background.intrinsicWidth, background.intrinsicHeight)
    val vectorDrawable = ContextCompat.getDrawable(this, vectorDrawableResourceId)
    vectorDrawable!!.setBounds(
        40,
        20,
        vectorDrawable.intrinsicWidth + 40,
        vectorDrawable.intrinsicHeight + 20
    )
    val bitmap = Bitmap.createBitmap(
        background.intrinsicWidth,
        background.intrinsicHeight,
        Bitmap.Config.ARGB_8888
    )
    val canvas = Canvas(bitmap)
    background.draw(canvas)
    vectorDrawable.draw(canvas)
    return BitmapDescriptorFactory.fromBitmap(bitmap)
}

fun isFetchNeeded(savedAt: Date, hour: Long): Boolean {
    val diff: Long = Date().time - savedAt.time
    val seconds = diff / 1000
    val minutes = seconds / 60
    val hours = minutes / 60
    val days = hours / 24
    Log.e("hours", "$hours $days $minutes")
    return if (days != 0L) {
        true
    } else hours >= hour
}
const val ANIMATION_FAST_MILLIS = 50L
fun View.simulateClick(delay: Long = ANIMATION_FAST_MILLIS) {
    performClick()
    isPressed = true
    invalidate()
    postDelayed({
        invalidate()
        isPressed = false
    }, delay)
}
suspend fun changeLoggedUser() {
    val loggedVerify = preferencesKey<Boolean>("isLogged")
    dataStore.edit { settings ->
        settings[loggedVerify] = true
    }
}

suspend fun changeLogoutUser() {
    val loggedVerify = preferencesKey<Boolean>("isLogged")
    dataStore.edit { settings ->
        settings[loggedVerify] = true
    }
}

fun Context.getLocationMode(): Int {
    return Settings.Secure.getInt(
        contentResolver,
        Settings.Secure.LOCATION_MODE
    )
}

