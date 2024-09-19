package com.devmike.repository.screen

import android.widget.Toast
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.devmike.repository.screen.components.RepositoryItem

@Composable
fun RepositorySearchScreen(viewModel: RepositorySearchViewModel = hiltViewModel()) {
    val repositoriesState = viewModel.searchResults.collectAsLazyPagingItems()
    val context = LocalContext.current
    LaunchedEffect(key1 = repositoriesState) {
        if (repositoriesState.itemCount > 0) {
            Toast
                .makeText(context, "Loading ${repositoriesState.peek(2)}", Toast.LENGTH_SHORT)
                .show()
        }
//
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TextField(value = viewModel.searchQuery, onValueChange = viewModel::modifySearchQuery)
        },
    ) { paddingValues ->

        LazyColumn(
            modifier =
                Modifier
                    .fillMaxSize()
                    .padding(paddingValues),
        ) {
            if (repositoriesState.loadState.refresh == LoadState.Loading) {
                item {
                    Text(
                        text = "Waiting for items to load from the backend",
                        modifier =
                            Modifier
                                .fillMaxWidth()
                                .wrapContentWidth(Alignment.CenterHorizontally),
                    )
                }
            }
            items(repositoriesState.itemCount) { count ->
                repositoriesState[count]?.let {
                    RepositoryItem(repository = it)
                }
            }
            if (repositoriesState.loadState.append == LoadState.Loading) {
                item {
                    CircularProgressIndicator(
                        modifier =
                            Modifier
                                .fillMaxWidth()
                                .wrapContentWidth(Alignment.CenterHorizontally),
                    )
                }
            }
        }
    }
}

@Composable
fun <T : Any> LazyPagingItems<T>.rememberLazyListState(): LazyListState {
    // After recreation, LazyPagingItems first return 0 items, then the cached items.
    // This behavior/issue is resetting the LazyListState scroll position.
    // Below is a workaround. More info: https://issuetracker.google.com/issues/177245496.
    return when (itemCount) {
        // Return a different LazyListState instance.
        0 -> remember(this) { LazyListState(0, 0) }
        // Return rememberLazyListState (normal case).
        else ->
            androidx.compose.foundation.lazy
                .rememberLazyListState()
    }
}
