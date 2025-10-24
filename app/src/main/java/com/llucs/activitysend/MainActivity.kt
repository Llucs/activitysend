
package com.llucs.activitysend

import android.net.Uri
import android.os.Bundle
import android.content.Context
import com.llucs.activitysend.core.LocaleHelper
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.LaunchedEffect
import androidx.navigation.compose.rememberNavController
import androidx.lifecycle.viewmodel.compose.viewModel
import com.llucs.activitysend.core.IntentSender
import com.llucs.activitysend.data.ActivityInfoModel
import com.llucs.activitysend.data.AppInfoModel
import com.llucs.activitysend.data.AppRepository
import com.llucs.activitysend.ui.AppNavigation
import com.llucs.activitysend.ui.theme.ActivitySendTheme
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    private val appRepository by lazy { AppRepository(this) }

    override fun attachBaseContext(newBase: Context?) {
        super.attachBaseContext(newBase?.let { LocaleHelper.onAttach(it) })
    }

    
    private var selectedFileUri: Uri? by mutableStateOf(null)
    private var selectedActivityInfo: ActivityInfoModel? by mutableStateOf(null)

    private val filePickerLauncher = registerForActivityResult(ActivityResultContracts.GetContent()) {
        selectedFileUri = it
    }

    @OptIn(ExperimentalMaterial3Api::class, ExperimentalAnimationApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            ActivitySendTheme {
                    val navController = rememberNavController()
                    val viewModel: AppSearchViewModel = viewModel(factory = AppSearchViewModelFactory(appRepository))
                    val apps by viewModel.filteredApps.collectAsState()

                    AppNavigation(
                        navController = navController,
                        apps = apps,
                        viewModel = viewModel,
                        onAppClick = { appInfo ->
                            // No need to do anything here, navigation handles the appName
                        },
                        activitiesForApp = { appName ->
                            viewModel.allApps.value.find { it.label == appName }?.activities ?: emptyList()
                        },
                    onActivityClick = { activityInfo ->
                        selectedActivityInfo = activityInfo
                    },
                    selectedActivity = selectedActivityInfo,
                    onPickFile = { filePickerLauncher.launch("*/*") },
                        onSend = { // Implement send logic here
                            // ... (Send logic remains)
                        }
                        selectedFileUri?.let { uri ->
                            selectedActivityInfo?.let { activity ->
                                IntentSender.sendFileToActivity(this, uri, activity.toComponentName())
                            }
                        }
                    }
                )
            }
        }
    }
}

