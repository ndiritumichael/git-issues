package com.devmike.repository.screen

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.devmike.domain.repository.IRepoSearchRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RepositorySearchViewModel
    @Inject
    constructor(
        private val repoSearchRepository: IRepoSearchRepository,
    ) : ViewModel() {
        private var debounceDuration = 0L
        var searchQuery by mutableStateOf("")
            private set

        @OptIn(FlowPreview::class, ExperimentalCoroutinesApi::class)
        val searchResults =
            snapshotFlow { searchQuery }
                .debounce(debounceDuration)
                .flatMapLatest { query ->

                    (
                        if (query.isEmpty()) {
                            flowOf()
                        } else {

                            val data = repoSearchRepository.searchRepositories(query.trim())

                            data.cachedIn(viewModelScope)
                        }

                    )
                }.cachedIn(viewModelScope)

        fun modifySearchQuery(query: String) {
            viewModelScope.launch {
                searchQuery = query
            }
        }

        fun modifyDebounceTime(duration: Long) {
            debounceDuration = duration
        }
    }
