package com.llucs.activitysend

import android.content.Context
import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.*
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
import com.llucs.activitysend.core.IntentSender
import com.llucs.activitysend.core.LocaleHelper
import com.llucs.activitysend.data.ActivityInfoModel
import com.llucs.activitysend.data.AppRepository
import com.llucs.activitysend.ui.AppNavigation
import com.llucs.activitysend.ui.viewmodel.AppSearchViewModel
import com.llucs.activitysend.ui.viewmodel.AppSearchViewModelFactory
import com.llucs.activitysend.ui.theme.ActivitySendTheme

class MainActivity : ComponentActivity() {

    private val appRepository by lazy { AppRepository(this) }

    private var selectedFileUri by mutableStateOf<Uri?>(null)
    private var selectedActivityInfo by mutableStateOf<ActivityInfoModel?>(null)

    private val filePickerLauncher = registerForActivityResult(ActivityResultContracts.GetContent()) {
        selectedFileUri = it
    }

    override fun attachBaseContext(newBase: Context?) {
        super.attachBaseContext(newBase?.let { LocaleHelper.onAttach(it) })
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            ActivitySendTheme {
                val navController = rememberNavController()

                // Inicializa o ViewModel com a Factory
                val viewModel: AppSearchViewModel = viewModel(
                    factory = AppSearchViewModelFactory(appRepository)
                )

                // Coleta apps filtrados
                val apps by viewModel.filteredApps.collectAsState()
                val allApps by viewModel.allApps.collectAsState()

                AppNavigation(
                    navController = navController,
                    apps = apps,
                    viewModel = viewModel,
                    onAppClick = { /* navegação é tratada no Navigation.kt */ },
                    activitiesForApp = { appName ->
                        allApps.find { it.label == appName }?.activities ?: emptyList()
                    },
                    onActivityClick = { activityInfo ->
                        selectedActivityInfo = activityInfo
                    },
                    selectedActivity = selectedActivityInfo,
                    onPickFile = { filePickerLauncher.launch("*/*") },
                    onSend = {
                        selectedFileUri?.let { uri ->
                            selectedActivityInfo?.let { activity ->
                                IntentSender.sendFileToActivity(
                                    this@MainActivity,
                                    uri,
                                    activity.toComponentName()
                                )
                            }
                        }
                    }
                )
            }
        }
    }
}