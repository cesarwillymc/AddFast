
package com.summit.core.style

import android.content.Context
import android.content.res.Configuration


internal class ThemeUtilsImpl : ThemeUtils {


    override fun isDarkTheme(context: Context) = context.resources.configuration.uiMode and
        Configuration.UI_MODE_NIGHT_MASK == Configuration.UI_MODE_NIGHT_YES


    override fun isLightTheme(context: Context) = !isDarkTheme(context)


/*    override fun setNightMode(forceNight: Boolean, delay: Long) {
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
    }*/
}
