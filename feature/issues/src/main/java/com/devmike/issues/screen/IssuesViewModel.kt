package com.devmike.issues.screen

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
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
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import javax.inject.Inject

/**
 * A ViewModel for managing the issues screen in the application.
 *
 * @param savedStateHandle A handle to retrieve and save the state of the ViewModel across configuration changes.
 * @param issuesRepository The repository for fetching and manipulating issue data.
 */
@HiltViewModel
class IssuesViewModel
    @Inject
    constructor(
        savedStateHandle: SavedStateHandle,
        val issuesRepository: IssuesRepository,
    ) : ViewModel() {
        /**
         * Debounce duration for search queries (in milliseconds).
         */
        private val debounceDuration = 500L

        /*** Navigation arguments for the current screen (assuming it's an Issues screen).
         */
        val repoDetails: AppDestinations.Issues = savedStateHandle.toRoute()

        /**
         * Mutable state flow to hold the currently selected assignees.
         */
        private val _selectedAssignees = MutableStateFlow<Set<AssigneeModel>>(setOf())

        /**
         * Exposes the selected assignees as a read-only state flow.
         */
        val selectedAssignees = _selectedAssignees.asStateFlow()

        /**
         * Mutable state flow to hold the currently selected labels.
         */
        private val _selectedLabels = MutableStateFlow<Set<LabelModel>>(setOf())

        /**
         * Exposes the selected labels as a read-only state flow.
         */
        val selectedLabels = _selectedLabels.asStateFlow()

        /**
         * Mutable state flow to hold the currently selected issuestate (e.g., open, closed, all).
         */
        private val _selectedIssueState = MutableStateFlow(IssueState.ALL)

        /**
         * Exposes the selected issue state as a read-only state flow.
         */
        val selectedIssueState = _selectedIssueState.asStateFlow()

        /**
         * Mutable state holding the current search query entered by the user.
         */
        var searchQuery by mutableStateOf("")

        /**
         * Flow of PagingData containing the labels for the current repository.
         */
        val repositoryLabels =
            issuesRepository
                .getRepositoryLabels(
                    repository = repoDetails.repository,
                    owner = repoDetails.owner,
                ).cachedIn(viewModelScope)

        /**
         * Flow of PagingData containing the assignable users for the current repository.
         */
        val repositoryAssignees =
            issuesRepository
                .getRepositoryAssignees(
                    repository = repoDetails.repository,
                    owner = repoDetails.owner,
                ).cachedIn(viewModelScope)
        // .stateIn(viewModelScope, SharingStarted.Lazily, PagingData.empty())

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
                }
                    // .debounce(debounceDuration) causes tests to fail
                    .map {
                        if (it.length < 3) null else it
                    },
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
                delay(debounceDuration)

                val data =
                    issuesRepository.getPagedIssues(
                        issueSearchModel.copy(
                            query = searchQuery,
                            labels = selectedLabels.toList().map { it.name }.ifEmpty { null },
                            assignees = selectedAssignees.map { it.username }.ifEmpty { null },
                            issueState = selectedIssueState.state,
                        ),
                    )

                data
                    .cachedIn(viewModelScope)
            }.flatMapLatest {
                it
            }

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
