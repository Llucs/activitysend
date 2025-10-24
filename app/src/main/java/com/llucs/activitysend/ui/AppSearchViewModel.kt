package com.llucs.activitysend.ui

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.llucs.activitysend.data.AppInfoModel
import com.llucs.activitysend.data.AppRepository
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class AppSearchViewModel(private val repository: AppRepository) : ViewModel() {

    private val _allApps = MutableStateFlow<List<AppInfoModel>>(emptyList())
    val allApps: StateFlow<List<AppInfoModel>> = _allApps.asStateFlow()

    private val _filteredApps = MutableStateFlow<List<AppInfoModel>>(emptyList())
    val filteredApps: StateFlow<List<AppInfoModel>> = _filteredApps.asStateFlow()

    var searchQuery by mutableStateOf("")
        private set

    private var searchJob: Job? = null

    init {
        loadApps()
    }

    private fun loadApps() {
        viewModelScope.launch {
            _allApps.value = repository.getInstalledApps()
            _filteredApps.value = _allApps.value
        }
    }

    fun onSearchQueryChange(query: String) {
        searchQuery = query
        searchJob?.cancel()
        searchJob = viewModelScope.launch {
            delay(300) // Debounce
            filterApps(query)
        }
    }

    private fun filterApps(query: String) {
        val trimmedQuery = query.trim().lowercase()
        _filteredApps.value = if (trimmedQuery.isEmpty()) {
            _allApps.value
        } else {
            _allApps.value.filter { app ->
                app.label.lowercase().contains(trimmedQuery) || app.packageName.lowercase().contains(trimmedQuery)
            }
        }
    }
}

