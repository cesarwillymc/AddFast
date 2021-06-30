
package com.summit.commons.ui.extension

import android.content.Context
import androidx.annotation.StringRes


fun Context.getString(@StringRes resId: Int?) =
    resId?.let {
        getString(it)
    } ?: run {
        ""
    }
