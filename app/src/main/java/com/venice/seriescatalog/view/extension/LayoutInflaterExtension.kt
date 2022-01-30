package com.venice.seriescatalog.view.extension

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.core.view.children

/*
 * Created by Andre Arruda on 1/28/22.
 */
fun LayoutInflater.safeInflate(@LayoutRes viewResourceId: Int, parent: ViewGroup?, attachToRoot: Boolean = false): View {
    val existingView = parent?.children?.find { it.id == viewResourceId }
    parent?.clearDisappearingChildren()

    return if (existingView == null) {
        this.inflate(viewResourceId, parent, attachToRoot)
    } else {
        existingView.removeFromParent()
        existingView
    }
}