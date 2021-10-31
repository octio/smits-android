package solutions.octio.smits.features.appstate

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import solutions.octio.smits.core.archcomponents.BaseViewModel

class AppStateViewModel : BaseViewModel() {

    val components: LiveData<Components> get() = _components

    private val _components: MutableLiveData<Components> =
        MutableLiveData<Components>().also {
            it.value = hasComponents
        }

    var hasComponents: Components = Components()
        set(value) {
            field = value
            _components.value = value
        }

    class Components(
        val appBar: Boolean = false,
        val backButton: Boolean = false
    )
}