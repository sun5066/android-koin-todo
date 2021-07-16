package github.sun5066.kointodo.extentions

import android.app.Activity
import android.content.Context
import android.widget.Toast

internal fun Activity.showToast(msg: String, length: Int) {
    showToast(this, msg, length)
}

internal fun Activity.showToast(resId: Int, length: Int) {
    showToast(this, getString(resId), length)
}

internal fun showToast(context: Context, msg: String, length: Int) = Toast.makeText(context, msg, length)