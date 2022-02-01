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
