package com.summit.core.style

import android.content.Context


interface ThemeUtils {


    fun isDarkTheme(context: Context): Boolean


    fun isLightTheme(context: Context): Boolean


    fun setNightMode(forceNight: Boolean, delay: Long = 0L)
}
