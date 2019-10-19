package solutions.octio.smits.core.archcomponents.extension

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import solutions.octio.smits.core.archcomponents.Event
import solutions.octio.smits.core.archcomponents.LiveEvent
import solutions.octio.smits.core.archcomponents.MutableLiveEvent

/**
 * Creates an instance of [LiveData] with `this` as its value.
 */
fun <T> T?.asLiveData(): LiveData<T> = MutableLiveData<T>().apply { value = this@asLiveData }

/**
 * Creates an instance of [LiveEvent] with `this` as its value.
 */
fun <T> T?.asLiveEvent(): LiveEvent<T> =
    MutableLiveEvent<T>().apply { value = Event(this@asLiveEvent) }