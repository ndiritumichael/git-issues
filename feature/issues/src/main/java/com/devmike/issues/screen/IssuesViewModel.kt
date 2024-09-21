package com.devmike.issues.screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.devmike.data.sources.issues.IssuesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class IssuesViewModel
    @Inject
    constructor(
        private val issuesRepository: IssuesRepository,
    ) : ViewModel() {
        val issuesResults =
            issuesRepository
                .getPagedIssues(
                    owner = "flutter",
                    name = "flutter",
                    assignee = null,
                    labels = emptyList(),
                ).cachedIn(viewModelScope)
    }
