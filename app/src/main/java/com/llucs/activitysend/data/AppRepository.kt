
package com.llucs.activitysend.data

import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.content.pm.ResolveInfo
import android.os.Build
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class AppRepository(private val context: Context) {

    private val packageManager: PackageManager = context.packageManager

    suspend fun getInstalledApps(): List<AppInfoModel> = withContext(Dispatchers.IO) {
        val launcherIntent = Intent(Intent.ACTION_MAIN, null).apply {
            addCategory(Intent.CATEGORY_LAUNCHER)
        }

        val launcherResolveInfos = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            packageManager.queryIntentActivities(launcherIntent, PackageManager.ResolveInfoFlags.of(PackageManager.MATCH_ALL.toLong()))
        } else {
            @Suppress("DEPRECATION")
            packageManager.queryIntentActivities(launcherIntent, PackageManager.MATCH_ALL)
        }

        val sendIntent = Intent(Intent.ACTION_SEND).apply { type = "*/*" }

        val sendResolveInfos = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            packageManager.queryIntentActivities(sendIntent, PackageManager.ResolveInfoFlags.of(PackageManager.MATCH_DEFAULT_ONLY.toLong()))
        } else {
            @Suppress("DEPRECATION")
            packageManager.queryIntentActivities(sendIntent, PackageManager.MATCH_DEFAULT_ONLY)
        }

        val allResolveInfos = (launcherResolveInfos + sendResolveInfos).distinctBy { it.activityInfo.packageName }

        allResolveInfos
            .mapNotNull { resolveInfo ->
                val packageName = resolveInfo.activityInfo.packageName
                val appLabel = resolveInfo.loadLabel(packageManager).toString()
                val appIcon = resolveInfo.loadIcon(packageManager)
                val activities = getActivitiesForPackageInternal(packageName, sendResolveInfos)
                AppInfoModel(packageName, appLabel, appIcon, activities)
            }
            .sortedBy { it.label.lowercase() }
    }

    private fun getActivitiesForPackageInternal(packageName: String, allSendResolveInfos: List<ResolveInfo>): List<ActivityInfoModel> {
        return allSendResolveInfos
            .filter { it.activityInfo.packageName == packageName }
            .map { resolveInfo ->
                ActivityInfoModel(
                    label = resolveInfo.loadLabel(packageManager).toString(),
                    packageName = resolveInfo.activityInfo.packageName,
                    className = resolveInfo.activityInfo.name,
                    icon = resolveInfo.loadIcon(packageManager),
                    isExported = resolveInfo.activityInfo.exported
                )
            }
            .sortedBy { it.label.lowercase() }
    }
}

