
package com.llucs.activitysend.data

import android.content.ComponentName
import android.graphics.drawable.Drawable

data class ActivityInfoModel(
    val label: String,
    val packageName: String,
    val className: String,
    val icon: Drawable?,
    val isExported: Boolean = false // Adicionado para indicar se a activity é exportada
) {
    fun toComponentName(): ComponentName {
        return ComponentName(packageName, className)
    }
}

