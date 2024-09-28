package com.devmike.issues.screen

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.devmike.domain.appdestinations.AppDestinations
import com.devmike.domain.helper.IssueState
import com.devmike.domain.models.AssigneeModel
import com.devmike.domain.models.IssueSearchModel
import com.devmike.domain.models.LabelModel
import com.devmike.domain.repository.IssuesRepository
import com.devmike.issues.toggleItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class IssuesViewModel
    @Inject
    constructor(
        savedStateHandle: SavedStateHandle,
        private val issuesRepository: IssuesRepository,
    ) : ViewModel() {
        private val debounceDuration = 500L
        val repoDetails: AppDestinations.Issues = savedStateHandle.toRoute()
        private val _selectedAssignees = MutableStateFlow<Set<AssigneeModel>>(setOf())
        val selectedAssignees = _selectedAssignees.asStateFlow()

        private val _selectedLabels = MutableStateFlow<Set<LabelModel>>(setOf())
        val selectedLabels = _selectedLabels.asStateFlow()

        private val _selectedIssueState = MutableStateFlow(IssueState.ALL)
        val selectedIssueState = _selectedIssueState.asStateFlow()

        val repositoryLabels =
            issuesRepository
                .getRepositoryLabels(
                    repository = repoDetails.repository,
                    owner = repoDetails.owner,
                ).cachedIn(viewModelScope)
                .stateIn(viewModelScope, SharingStarted.Lazily, PagingData.empty())

        val repositoryAssignees =
            issuesRepository
                .getRepositoryAssignees(
                    repository = repoDetails.repository,
                    owner = repoDetails.owner,
                ).cachedIn(viewModelScope)
                .stateIn(viewModelScope, SharingStarted.Lazily, PagingData.empty())

        var searchQuery by mutableStateOf("")

        private val issueSearchModelState =
            MutableStateFlow(
                IssueSearchModel(
                    repository = "${repoDetails.owner}/${repoDetails.repository}",
                    labels = null,
                    assignees = null,
                    issueState = IssueState.ALL.state,
                    query = null,
                    sortBy = "created-desc",
                ),
            )

        @OptIn(ExperimentalCoroutinesApi::class, FlowPreview::class)
        val issuesResults =
            combine(
                snapshotFlow {
                    searchQuery
                }.debounce(debounceDuration),
                issueSearchModelState,
                selectedLabels,
                selectedAssignees,
                selectedIssueState,
            ) {
                    searchQuery,
                    issueSearchModel,
                    selectedLabels,
                    selectedAssignees,
                    selectedIssueState,
                ->

                issuesRepository.getPagedIssues(
                    issueSearchModel.copy(
                        query = searchQuery,
                        labels = selectedLabels.toList().map { it.name }.ifEmpty { null },
                        assignees = selectedAssignees.map { it.username }.ifEmpty { null },
                        issueState = selectedIssueState.state,
                    ),
                )
            }.debounce(debounceDuration).flatMapLatest { it }.cachedIn(viewModelScope)

        fun modifySearchQuery(query: String) {
            searchQuery = query
        }

        fun modifyIssueState(issueState: IssueState) {
            _selectedIssueState.update {
                issueState
            }
        }

        fun modifySelectedAssignees(assignee: AssigneeModel) {
            _selectedAssignees.update { assignees ->

                assignees.toMutableSet().apply {
                    toggleItem(assignee)
                }
            }
        }

        fun modifySelectedLabels(label: LabelModel) {
            _selectedLabels.update { labels ->
                labels.toMutableSet().apply {
                    toggleItem(label)
                }
            }
        }

        fun clearFilters() {
            _selectedAssignees.update { setOf() }
            _selectedLabels.update { setOf() }
            _selectedIssueState.update { IssueState.ALL }
            searchQuery = ""
        }
    }
