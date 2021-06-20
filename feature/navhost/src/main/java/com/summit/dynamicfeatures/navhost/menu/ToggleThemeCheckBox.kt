
package com.summit.dynamicfeatures.navhost.menu

import android.content.Context
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatCheckBox
import com.summit.dynamicfeatures.navhost.R


class ToggleThemeCheckBox @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null
) : AppCompatCheckBox(context, attrs) {

    init {
        setButtonDrawable(R.drawable.asl_theme)
    }
}
