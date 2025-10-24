
package com.llucs.activitysend.ui

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.llucs.activitysend.data.AppInfoModel
import com.llucs.activitysend.data.ActivityInfoModel

sealed class Screen(val route: String) {
    object Main : Screen("main")
object Activities : Screen("activities/{appName}/{packageName}") {
    fun createRoute(appName: String, packageName: String) = "activities/$appName/$packageName"
}
    object Send : Screen("send/{activityLabel}") {
        fun createRoute(activityLabel: String) = "send/$activityLabel"
    }
    object Settings : Screen("settings")
}

@Composable
fun AppNavigation(
    navController: NavHostController,
    apps: List<AppInfoModel>,
    viewModel: AppSearchViewModel,
    onAppClick: (AppInfoModel) -> Unit,
    activitiesForApp: (String) -> List<ActivityInfoModel>,
    onActivityClick: (ActivityInfoModel) -> Unit,
    selectedActivity: ActivityInfoModel?,
    onPickFile: () -> Unit,
    onSend: () -> Unit
) {
    NavHost(navController = navController, startDestination = Screen.Main.route) {
        composable(Screen.Main.route) {
            MainScreen(
                apps = apps,
                viewModel = viewModel,
                onAppClick = { appInfo ->
                    onAppClick(appInfo)
                    navController.navigate(Screen.Activities.createRoute(appInfo.label, appInfo.packageName))
                },
                onSettingsClick = { navController.navigate(Screen.Settings.route) }
            )
        }
        composable(Screen.Activities.route) {
            val appName = it.arguments?.getString("appName") ?: ""
            val packageName = it.arguments?.getString("packageName") ?: ""
            ActivitiesScreen(
                appName = appName,
                packageName = packageName,
                activities = activitiesForApp(appName),
                onActivityClick = { activityInfo ->
                    onActivityClick(activityInfo)
                    navController.navigate(Screen.Send.createRoute(activityInfo.label))
                },
                onBackClick = { navController.popBackStack() }
            )
        }
        composable(Screen.Send.route) {
            // The actual activity object needs to be passed, not just the label.
            // For simplicity, we'll use the selectedActivity from the ViewModel/state holder.
            selectedActivity?.let { activity ->
                SendScreen(
                    activity = activity,
                    onPickFile = onPickFile,
                    onSend = onSend
                )
            }
        }
        composable(Screen.Settings.route) {
            SettingsScreen()
        }
    }
}

