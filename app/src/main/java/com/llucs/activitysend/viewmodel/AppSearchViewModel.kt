package com.llucs.activitysend.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.llucs.activitysend.data.AppInfoModel
import com.llucs.activitysend.data.AppRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class AppSearchViewModel(private val repository: AppRepository) : ViewModel() {

    private val _allApps = MutableStateFlow<List<AppInfoModel>>(emptyList())
    val allApps: StateFlow<List<AppInfoModel>> = _allApps.asStateFlow()

    private val _filteredApps = MutableStateFlow<List<AppInfoModel>>(emptyList())
    val filteredApps: StateFlow<List<AppInfoModel>> = _filteredApps.asStateFlow()

    init {
        viewModelScope.launch {
            val apps = repository.getAllApps() // função do AppRepository que pega apps
            _allApps.value = apps
            _filteredApps.value = apps
        }
    }

    fun search(query: String) {
        _filteredApps.update { apps ->
            if (query.isBlank()) {
                _allApps.value
            } else {
                _allApps.value.filter { it.label.contains(query, ignoreCase = true) }
            }
        }
    }
}