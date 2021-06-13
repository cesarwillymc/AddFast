
package com.summit.commons.ui.extension

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer


fun <T> LifecycleOwner.observe(liveData: LiveData<T>, EventObserver: (T) -> Unit) {
    liveData.observe(
        this
    ) {
        it?.let { t -> EventObserver(t) }
    }
}

