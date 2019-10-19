package solutions.octio.smits.core.extension

import android.animation.LayoutTransition
import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.*
import androidx.annotation.DimenRes
import androidx.annotation.LayoutRes
import androidx.core.content.getSystemService
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.textfield.TextInputLayout
import solutions.octio.smits.R
import solutions.octio.smits.core.android.recyclerview.ItemOffsetDecoration

// region ViewGroup
/**
 * Creates a [LayoutInflater] from `this` context and calls [LayoutInflater.inflate], with `this`
 * as the inflated view root.
 *
 * @param layoutResource [LayoutRes] of the layout to be inflated
 * @param attachToRoot if the view should be attached to root or not, default is false
 */
@JvmOverloads
fun ViewGroup.inflate(@LayoutRes layoutResource: Int, attachToRoot: Boolean = false): View =
    LayoutInflater.from(context).inflate(layoutResource, this, attachToRoot)

/**
 * @return `this` children as an [Iterable] of [View]
 */
val ViewGroup.children: Iterable<View> get() = (0 until childCount).map(::getChildAt)

/**
 * Updates `this` margin values. It attempts to cast `this` layoutParams to
 * [ViewGroup.MarginLayoutParams] and will only update the values if the cast is succesful.
 *
 * @param left [DimenRes] of left margin
 * @param top [DimenRes] of top margin
 * @param right [DimenRes] of right margin
 * @param bottom [DimenRes] of bottom margin
 */
fun ViewGroup.setMargin(
    @DimenRes left: Int? = null,
    @DimenRes top: Int? = null,
    @DimenRes right: Int? = null,
    @DimenRes bottom: Int? = null
) {
    (layoutParams as? ViewGroup.MarginLayoutParams)?.apply {
        leftMargin = left?.let(resources::getDimensionPixelSize) ?: 0
        topMargin = top?.let(resources::getDimensionPixelSize) ?: 0
        rightMargin = right?.let(resources::getDimensionPixelSize) ?: 0
        bottomMargin = bottom?.let(resources::getDimensionPixelSize) ?: 0
    }
}

/**
 * Updates `this` layoutTransition to allow [LayoutTransition.CHANGING] transition types,
 * which is needed to animate height changes, for instance.
 */
fun ViewGroup.animateChangingTransitions() {
    layoutTransition = LayoutTransition().apply { enableTransitionType(LayoutTransition.CHANGING) }
}
// endregion

// region Components
fun TextInputLayout.showError(errorMessage: String) {
    error = errorMessage
}

fun TextInputLayout.clearError() {
    error = null
}

fun TextInputLayout.disableError() {
    isErrorEnabled = false
}

fun TextInputLayout.clearAndDisableError() {
    error = null
    isErrorEnabled = false
}

fun EditText.textAsString(): String = text.toString()

fun EditText.clearText() {
    setText("")
}

inline fun EditText.addTextChangedListener(
    crossinline beforeTextChanged: (charSequence: CharSequence, start: Int, count: Int, after: Int) -> Unit = { _, _, _, _ -> },
    crossinline onTextChanged: (charSequence: CharSequence, start: Int, before: Int, count: Int) -> Unit = { _, _, _, _ -> },
    crossinline afterTextChanged: (text: String) -> Unit = {}
) {
    addTextChangedListener(object : TextWatcher {
        override fun beforeTextChanged(
            charSequence: CharSequence,
            start: Int,
            count: Int,
            after: Int
        ) {
            beforeTextChanged(charSequence, start, count, after)
        }

        override fun onTextChanged(
            charSequence: CharSequence,
            start: Int,
            before: Int,
            count: Int
        ) {
            onTextChanged(charSequence, start, before, count)
        }

        override fun afterTextChanged(editable: Editable) {
            afterTextChanged(editable.toString())
        }
    })
}

fun View.gone() {
    visibility = View.GONE
}

fun View.invisible() {
    visibility = View.INVISIBLE
}

fun View.visible() {
    visibility = View.VISIBLE
}

fun View.visibleIf(predicate: Boolean, otherwiseVisibility: Int = View.GONE) {
    visibility = if (predicate) View.VISIBLE else otherwiseVisibility
}

fun View.isVisible(): Boolean = visibility == View.VISIBLE

fun View.heightCollapse() {
    val params = layoutParams
    params.height = 0
    layoutParams = params
    requestLayout()
}

fun View.heightWrapContent() {
    val params = layoutParams
    params.height = when (layoutParams) {
        is LinearLayout -> LinearLayout.LayoutParams.WRAP_CONTENT
        is RelativeLayout -> RelativeLayout.LayoutParams.WRAP_CONTENT
        is FrameLayout -> FrameLayout.LayoutParams.WRAP_CONTENT
        else -> ViewGroup.LayoutParams.WRAP_CONTENT
    }

    layoutParams = params
    requestLayout()
}

fun RecyclerView.withDefaultDecoration(): RecyclerView = apply {
    addItemDecoration(ItemOffsetDecoration(context, R.dimen.margin_small))
}

fun RecyclerView.withGridLayoutManager(spanCount: Int): RecyclerView = apply {
    layoutManager = GridLayoutManager(context, spanCount)
}

fun RecyclerView.withLinearLayoutManager(): RecyclerView = apply {
    layoutManager = LinearLayoutManager(context)
}
// endregion

// region Keyboard
fun isKeyboardSubmit(actionId: Int, event: KeyEvent?): Boolean =
    actionId == EditorInfo.IME_ACTION_GO ||
            actionId == EditorInfo.IME_ACTION_DONE ||
            (event != null && event.action == KeyEvent.ACTION_UP && event.keyCode == KeyEvent.KEYCODE_ENTER)

inline fun EditText.onKeyboardSubmit(crossinline block: EditText.() -> Unit) {
    setOnEditorActionListener { _, actionId, event ->
        return@setOnEditorActionListener if (isKeyboardSubmit(actionId, event)) {
            block()
            true
        } else {
            false
        }
    }
}

fun View.showKeyboard() {
    context.getSystemService<InputMethodManager>()?.showSoftInput(this, 0)
}

fun View.hideKeyboard() {
    context.getSystemService<InputMethodManager>()?.hideSoftInputFromWindow(windowToken, 0)
}
// endregion

@JvmOverloads
fun Context.toast(message: String, duration: Int = Toast.LENGTH_SHORT) {
    Toast.makeText(this, message, duration).show()
}