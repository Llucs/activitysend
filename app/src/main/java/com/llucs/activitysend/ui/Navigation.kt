package com.llucs.activitysend.ui

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.llucs.activitysend.data.AppInfoModel
import com.llucs.activitysend.data.ActivityInfoModel
import com.llucs.activitysend.viewmodel.AppSearchViewModel

sealed class Screen(val route: String) {
    object Main : Screen("main")
    object Activities : Screen("activities/{appName}/{packageName}") {
        fun createRoute(appName: String, packageName: String) =
            "activities/$appName/$packageName"
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
    NavHost(
        navController = navController,
        startDestination = Screen.Main.route
    ) {
        composable(Screen.Main.route) {
            MainScreen(
                apps = apps,
                viewModel = viewModel,
                onAppClick = { appInfo ->
                    onAppClick(appInfo)
                    navController.navigate(
                        Screen.Activities.createRoute(appInfo.label, appInfo.packageName)
                    )
                },
                onSettingsClick = { navController.navigate(Screen.Settings.route) }
            )
        }

        composable(Screen.Activities.route) { backStackEntry ->
            val appName = backStackEntry.arguments?.getString("appName") ?: ""
            val packageName = backStackEntry.arguments?.getString("packageName") ?: ""

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