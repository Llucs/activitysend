package com.llucs.activitysend.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.llucs.activitysend.data.AppRepository

class AppSearchViewModelFactory(private val repository: AppRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AppSearchViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return AppSearchViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}

