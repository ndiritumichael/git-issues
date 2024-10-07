package com.devmike.repository.screen.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemKey
import com.devmike.domain.models.RepositoryModel
import com.devmike.repository.utils.createTestPagingFlow

@Composable
fun RepositoryListScreen(
    modifier: Modifier,
    repositoriesState: LazyPagingItems<RepositoryModel>,
    onRepositoryClick: (name: String, owner: String) -> Unit,
) {
    LazyColumn(
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
       /* items(
            repositoriesState.itemCount,
            key = repositoriesState.itemKey { it.url },
        ) { count ->
            repositoriesState[count]?.let {
                RepositoryItem(repository = it, onRepositoryClick)
            }
        }*/
        repositoriesState.apply {
            when {
                loadState.refresh is LoadState.NotLoading -> {
                    items(
                        repositoriesState.itemCount,
                        key = repositoriesState.itemKey { it.url },
                    ) { count ->
                        repositoriesState[count]?.let {
                            RepositoryItem(repository = it, onRepositoryClick)
                        }
                    }

                    if (loadState.source.append.endOfPaginationReached &&
                        repositoriesState.itemCount == 0
                    ) {
                        item {
                            NoRepositoriesFoundScreen()
                        }
                    }
                }

                loadState.refresh is LoadState.Loading -> {
                    item {
                        Box(
                            modifier =
                                Modifier
                                    .padding(
                                        top = 50.dp,
                                        bottom = 50.dp,
                                    ),
                            contentAlignment = Alignment.Center,
                        ) {
                            CircularProgressIndicator(
                                modifier = Modifier.height(100.dp),
                            )
                        }
                    }
                }

                loadState.append is LoadState.Loading -> {
                    item {
                        Box(
                            modifier = Modifier.fillMaxWidth(),
                            contentAlignment = Alignment.Center,
                        ) {
                            CircularProgressIndicator(modifier = Modifier.height(50.dp))
                            Text(text = "Search loading")
                        }
                    }
                }

                loadState.refresh is LoadState.Error || loadState.append is LoadState.Error -> {
                    val errorMessage = this.loadState.refresh as LoadState.Error
                    item {
                        Box(
                            modifier =
                                Modifier
                                    .fillMaxWidth()
                                    .padding(56.dp),
                            contentAlignment = Alignment.BottomCenter,
                        ) {
                            Column(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalAlignment = Alignment.CenterHorizontally,
                            ) {
                                val errorText =

                                    errorMessage.error.localizedMessage ?: "Something Went Wrong"

                                Text(errorText, textAlign = TextAlign.Center)
                                Button(onClick = { retry() }) {
                                    Text(text = "Try Again")
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
@Preview
@Preview
fun RepositoryListScreenPreview() {
    val items =

        createTestPagingFlow(
            listOf(
                RepositoryModel(
                    url = "https://github.com/square/retrofit",
                    name = "Retrofit",
                    nameWithOwner = "square/retrofit",
                    owner = "square",
                    description = "A type-safe HTTP client for Android and Java.",
                    stargazers = 40000,
                    forkCount = 8000, issueCount = 1500,
                    avatarUrl = "https://avatars.githubusercontent.com/u/82592",
                ),
                RepositoryModel(
                    url = "https://github.com/bumptech/glide",
                    name = "Glide",
                    nameWithOwner = "bumptech/glide",
                    owner = "bumptech",
                    description =
                        """An image loading and caching library
                                |for Android focused on smooth scrolling.
                        """.trimMargin(),
                    stargazers = 35000,
                    forkCount = 6500,
                    issueCount = 1200,
                    avatarUrl = "https://avatars.githubusercontent.com/u/4255330",
                ),
                RepositoryModel(
                    url = "https://github.com/JetBrains/kotlin",
                    name = "Kotlin",
                    nameWithOwner = "JetBrains/kotlin",
                    owner = "JetBrains",
                    description = "The Kotlin Programming Language.",
                    stargazers = 41000,
                    forkCount = 7800,
                    issueCount = 2300,
                    avatarUrl = "https://avatars.githubusercontent.com/u/878438",
                ),
                RepositoryModel(
                    url = "https://github.com/androidx/androidx",
                    name = "AndroidX",
                    nameWithOwner = "androidx/androidx",
                    owner = "androidx",
                    description =
                        """
                        AndroidX is the open-source project that the Android team uses to
                        develop, test, package, version and release libraries within Jetpack.
                        """.trimIndent(),
                    stargazers = 10000,
                    forkCount = 3500, issueCount = 800,
                    avatarUrl = "https://avatars.githubusercontent.com/u/44576557",
                ),
                RepositoryModel(
                    url = "https://github.com/coil-kt/coil",
                    name = "Coil",
                    nameWithOwner = "coil-kt/coil",
                    owner = "coil-kt",
                    description =
                        """
                        "An image loading library for
                        Android backed by Kotlin Coroutines."
                        """.trimIndent(),
                    stargazers = 8000,
                    forkCount = 1000,
                    issueCount = 300,
                    avatarUrl = "https://avatars.githubusercontent.com/u/70237063",
                ),
            ),
        )

    RepositoryListScreen(
        modifier = Modifier,
        repositoriesState = items.collectAsLazyPagingItems(),
    ) { _, _ ->
    }
}
