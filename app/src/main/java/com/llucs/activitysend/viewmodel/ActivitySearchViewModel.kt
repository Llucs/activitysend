package com.llucs.activitysend.ui

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.llucs.activitysend.data.ActivityInfoModel

class ActivitySearchViewModel : ViewModel() {

    var searchQuery by mutableStateOf("")
        private set

    fun onSearchQueryChange(query: String) {
        searchQuery = query
    }

    fun filterActivities(activities: List<ActivityInfoModel>): List<ActivityInfoModel> {
        val trimmedQuery = searchQuery.trim().lowercase()
        return if (trimmedQuery.isEmpty()) {
            activities
        } else {
            activities.filter { activity ->
                activity.label.lowercase().contains(trimmedQuery) || activity.className.lowercase().contains(trimmedQuery)
            }
        }
    }
}

