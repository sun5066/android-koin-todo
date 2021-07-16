package github.sun5066.kointodo.binding

import android.view.View
import androidx.databinding.BindingAdapter

object CommonBindings {

    @BindingAdapter("clickListener")
    @JvmStatic
    fun onClickListener(view: View, clickListener: View.OnClickListener?) {
        clickListener?.let { view.setOnClickListener(it) }
    }
}