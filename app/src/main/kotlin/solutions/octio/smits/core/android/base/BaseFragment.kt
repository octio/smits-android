package solutions.octio.smits.core.android.base

import android.os.Bundle
import androidx.fragment.app.Fragment
import solutions.octio.smits.BuildConfig
import solutions.octio.smits.R
import solutions.octio.smits.core.extension.toast
import java.net.ConnectException

abstract class BaseFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    open fun handleError(error: Throwable) {
        if (error is ConnectException) {
            activity?.toast(getString(R.string.generic_msg_connection_error))
        } else {
            activity?.toast(getString(R.string.generic_msg_error))
        }

        if (BuildConfig.DEBUG) error.printStackTrace()
    }
}