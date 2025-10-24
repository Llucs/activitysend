
package com.llucs.activitysend.data

import android.graphics.drawable.Drawable

data class AppInfoModel(
    val packageName: String,
    val label: String,
    val icon: Drawable?,
    val activities: List<ActivityInfoModel>
)

