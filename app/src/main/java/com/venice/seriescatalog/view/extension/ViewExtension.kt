package com.venice.seriescatalog.view.extension

import android.view.View
import android.view.ViewGroup
import androidx.core.view.contains

/*
 * Created by Andre Arruda on 1/28/22.
 */
fun View.show(isVisible: Boolean) {
    this.visibility = if (isVisible) View.VISIBLE else View.GONE
}

fun ViewGroup.safeAddView(view: View, index: Int = -1) {
    if (!view.removeFromParent() && this.contains(view)){
        this.removeView(view)
        this.endViewTransition(view)
    }

    this.addView(view, index)
}

fun View.removeFromParent(): Boolean {
    val parent = this.parent
    return if (parent != null && parent is ViewGroup) {
        parent.removeView(this)
        parent.endViewTransition(this)
        true
    } else {
        false
    }
}
