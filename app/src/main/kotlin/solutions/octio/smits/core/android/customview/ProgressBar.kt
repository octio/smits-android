package solutions.octio.smits.core.android.customview

import android.content.Context
import android.graphics.PorterDuff
import android.util.AttributeSet
import android.widget.ProgressBar
import androidx.core.content.ContextCompat
import solutions.octio.smits.R

class ProgressBar @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = R.attr.progressBarStyle
) : ProgressBar(context, attrs, defStyleAttr) {

    init {
        indeterminateDrawable?.setColorFilter(
            ContextCompat.getColor(context, R.color.colorPrimary),
            PorterDuff.Mode.SRC_IN
        )
    }
}