package com.llucs.activitysend

import android.net.Uri
import android.os.Bundle
import android.content.Context
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.compose.runtime.*
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
import com.llucs.activitysend.core.LocaleHelper
import com.llucs.activitysend.core.IntentSender
import com.llucs.activitysend.data.ActivityInfoModel
import com.llucs.activitysend.data.AppRepository
import com.llucs.activitysend.ui.AppNavigation
import com.llucs.activitysend.ui.theme.ActivitySendTheme
import com.llucs.activitysend.viewmodel.AppSearchViewModel
import com.llucs.activitysend.viewmodel.AppSearchViewModelFactory
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {

    private val appRepository by lazy { AppRepository(this) }

    override fun attachBaseContext(newBase: Context?) {
        super.attachBaseContext(newBase?.let { LocaleHelper.onAttach(it) })
    }

    private var selectedFileUri by mutableStateOf<Uri?>(null)
    private var selectedActivityInfo by mutableStateOf<ActivityInfoModel?>(null)

    private val filePickerLauncher = registerForActivityResult(ActivityResultContracts.GetContent()) {
        selectedFileUri = it
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            ActivitySendTheme {
                val navController = rememberNavController()

                // Inicializa o ViewModel com a Factory que usa o AppRepository
                val viewModel: AppSearchViewModel = viewModel(
                    factory = AppSearchViewModelFactory(appRepository)
                )

                // Coleta os apps filtrados (atualizados conforme pesquisa)
                val apps by viewModel.filteredApps.collectAsState()

                AppNavigation(
                    navController = navController,
                    apps = apps,
                    viewModel = viewModel,
                    onAppClick = { /* Aqui você pode abrir os activities do app selecionado */ },
                    activitiesForApp = { emptyList() },
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