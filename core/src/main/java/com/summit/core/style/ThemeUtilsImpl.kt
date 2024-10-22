
package com.summit.core.style

import android.content.Context
import android.content.res.Configuration
import android.os.Handler
import androidx.appcompat.app.AppCompatDelegate
import javax.inject.Inject


class ThemeUtilsImpl @Inject constructor() : ThemeUtils {


    override fun isDarkTheme(context: Context) = context.resources.configuration.uiMode and
        Configuration.UI_MODE_NIGHT_MASK == Configuration.UI_MODE_NIGHT_YES


    override fun isLightTheme(context: Context) = !isDarkTheme(context)


    override fun setNightMode(forceNight: Boolean, delay: Long) {
        Handler().postDelayed(
            {
                AppCompatDelegate.setDefaultNightMode(
                    if (forceNight) {
                        AppCompatDelegate.MODE_NIGHT_YES
                    } else {
                        AppCompatDelegate.MODE_NIGHT_NO
                    }
                )
            },
            delay
        )
    }
}
