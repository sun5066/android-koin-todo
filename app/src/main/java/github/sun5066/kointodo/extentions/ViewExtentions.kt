package github.sun5066.kointodo.extentions

import android.app.Service
import android.view.View
import android.view.inputmethod.InputMethodManager

internal fun View.setVisibility(visibility: Int) {
    this.visibility = visibility
}

internal fun View.keyBoardSwitch(flag: Boolean) {
    if (flag) showKeyboard(this)
    else hideKeyboard(this)
}

private fun showKeyboard(view: View) =
    (view.context.getSystemService(Service.INPUT_METHOD_SERVICE) as? InputMethodManager)
        ?.showSoftInput(view, 0)

private fun hideKeyboard(view: View) =
    (view.context.getSystemService(Service.INPUT_METHOD_SERVICE) as? InputMethodManager)
        ?.hideSoftInputFromWindow(view.windowToken, 0)