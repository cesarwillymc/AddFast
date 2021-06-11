
package com.summit.commons.ui.extension

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer


fun <T> LifecycleOwner.observe(liveData: LiveData<T>, EventObserver: (T) -> Unit) {
    liveData.observe(
        this,
        Observer {
            it?.let { t -> EventObserver(t) }
        }
    )
}

/**
 * Adds the given observer to the observers list within the lifespan of the given
 * owner. The events are dispatched on the main thread. If LiveData already has data
 * set, it will be delivered to the observer.
 *
 * @param liveData The mutableLiveData to observe.
 * @param observer The observer that will receive the events.
 * @see MutableLiveData.observe
 */
fun <T> LifecycleOwner.observe(liveData: MutableLiveData<T>, observer: (T) -> Unit) {
    liveData.observe(
        this,
        Observer {
            it?.let { t -> observer(t) }
        }
    )
}
