package com.devmike.repository.screen

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.devmike.data.sources.RepoSearchRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import javax.inject.Inject

@HiltViewModel
class RepositorySearchViewModel
    @Inject
    constructor(
        private val repoSearchRepository: RepoSearchRepository,
    ) : ViewModel() {
        private val debounceDuration = 1000L
        var searchQuery by mutableStateOf("")
            private set

        @OptIn(FlowPreview::class, ExperimentalCoroutinesApi::class)
        val searchResults =
            snapshotFlow { searchQuery }
                .debounce(debounceDuration)
                .flatMapLatest { query ->
                    if (query.isEmpty()) {
                        flowOf(PagingData.empty())
                    } else {
                        repoSearchRepository.searchRepositories(query.trim())
                    }
                }.cachedIn(viewModelScope)

        fun modifySearchQuery(query: String) {
            searchQuery = query
        }
    }
