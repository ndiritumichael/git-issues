package com.devmike.repository.screen

import android.widget.Toast
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemKey
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
            TextField(
                value = viewModel.searchQuery,
                onValueChange = viewModel::modifySearchQuery,
                modifier = Modifier.fillMaxWidth(),
            )
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
            items(
                repositoriesState.itemCount,
                key = repositoriesState.itemKey { it.url },
            ) { count ->
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
