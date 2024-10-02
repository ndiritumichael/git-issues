package com.devmike.issues.screen

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.paging.compose.collectAsLazyPagingItems
import com.devmike.domain.helper.IssueState
import com.devmike.domain.models.IssueModel
import com.devmike.domain.models.LabelModel
import com.devmike.issues.screen.components.AssigneesScreen
import com.devmike.issues.screen.components.IssueCard
import com.devmike.issues.screen.components.IssueStateChip
import com.devmike.issues.screen.components.LabelsScreen
import com.devmike.issues.screen.components.NoIssuesFoundScreen
import com.devmike.issues.screen.components.SearchTextField
import kotlinx.coroutines.flow.flowOf

@OptIn(ExperimentalFoundationApi::class, ExperimentalMaterial3Api::class)
@Composable
fun IssuesScreen(
    viewModel: IssuesViewModel = hiltViewModel(),
    onBackClicked: () -> Unit,
) {
    val issues = viewModel.issuesResults.collectAsLazyPagingItems()
    var showAssigneesDialog by remember { mutableStateOf(false) }
    var showLabelsDialog by remember { mutableStateOf(false) }
    var showSearch by remember {
        mutableStateOf(false)
    }

    val selectedAssignes by viewModel.selectedAssignees.collectAsStateWithLifecycle()
    val selectedLabels by viewModel.selectedLabels.collectAsStateWithLifecycle()

    val showClearChip =
        remember(selectedAssignes, selectedLabels) {
            derivedStateOf {
                selectedAssignes.isNotEmpty() || selectedLabels.isNotEmpty()
            }
        }.value

    val selectedIssueState by viewModel.selectedIssueState.collectAsStateWithLifecycle()

    AssigneesScreen(
        showDialog = showAssigneesDialog,
        pagedAssignees = viewModel.repositoryAssignees.collectAsLazyPagingItems(),
        selectedAssignees = selectedAssignes,
        onAssigneeSelected = { viewModel.modifySelectedAssignees(it) },
        onDismiss = { showAssigneesDialog = false },
    )
    LabelsScreen(
        showDialog = showLabelsDialog,
        pagedLabels = viewModel.repositoryLabels.collectAsLazyPagingItems(),
        selectedLabels = selectedLabels,
        onSelectedLabel = { viewModel.modifySelectedLabels(it) },
        onDismiss = { showLabelsDialog = false },
    )

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            AnimatedContent(targetState = showSearch, label = "showsearch") { show ->
                if (show) {
                    SearchTextField(
                        searchText = viewModel.searchQuery,
                        onSearchTextChanged = viewModel::modifySearchQuery,
                        label = viewModel.repoDetails.repository + " Issues",
                    ) {
                        showSearch = false
                    }
                } else {
                    TopAppBar(
                        modifier = Modifier,
                        windowInsets = WindowInsets(top = 0.dp, bottom = 0.dp),
                        title = {
                            Column {
                                Text(
                                    text =
                                        buildString {
                                            append(viewModel.repoDetails.owner)
                                            append("/")
                                            append(viewModel.repoDetails.repository)
                                        },
                                    style = MaterialTheme.typography.bodySmall,
                                )
                                Text(
                                    text = "Issues",
                                    style = MaterialTheme.typography.bodyLarge,
                                    fontWeight = FontWeight.Bold,
                                )
                            }
//
                        },
                        navigationIcon = {
                            IconButton(onClick = onBackClicked) {
                                Icon(
                                    Icons.AutoMirrored.Filled.ArrowBack,
                                    contentDescription = "Back",
                                )
                            }
                        },
                        actions = {
                            IconButton(onClick = { showSearch = true }) {
                                Icon(Icons.Default.Search, contentDescription = "Search")
                            }
                        },
                    )
                }
            }
        },
    ) { paddingValues ->
        LazyColumn(
            modifier =
                Modifier.fillMaxSize().padding(
                    bottom = paddingValues.calculateBottomPadding(),
                    top = paddingValues.calculateTopPadding(),
                ),
        ) {
            stickyHeader {
                Surface {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier.fillMaxWidth(),
                    ) {
                        Row(
                            modifier = Modifier.padding(horizontal = 8.dp),
                            horizontalArrangement = Arrangement.spacedBy(8.dp),
                            verticalAlignment = Alignment.CenterVertically,
                        ) {
                            IssueStateChip(
                                selectedState = selectedIssueState,
                                states = IssueState.entries,
                                onStateSelected = {
                                    viewModel.modifyIssueState(it)
                                },
                            )
                            FilterChip(
                                selected = selectedLabels.isNotEmpty(),
                                onClick = { showLabelsDialog = true },
                                leadingIcon = {
                                    if (selectedLabels.isNotEmpty()) {
                                        Box(
                                            modifier =
                                                Modifier
                                                    .background(
                                                        MaterialTheme.colorScheme.onPrimary,
                                                        CircleShape,
                                                    ).clip(CircleShape)
                                                    .size(15.dp),
                                        ) {
                                            Text(
                                                text = "${

                                                    selectedLabels.size
                                                }",
                                                modifier =
                                                    Modifier
                                                        .align(Alignment.Center),
                                                style = MaterialTheme.typography.labelSmall,
                                            )
                                        }
                                    }
                                },
                                label = {
                                    Text(
                                        text = "Labels",
                                    )
                                },
                            )

                            FilterChip(
                                selected = selectedAssignes.isNotEmpty(),
                                onClick = { showAssigneesDialog = true },
                                leadingIcon = {
                                    if (selectedAssignes.isNotEmpty()) {
                                        Box(
                                            modifier =
                                                Modifier
                                                    .background(
                                                        MaterialTheme.colorScheme.onPrimary,
                                                        CircleShape,
                                                    ).clip(CircleShape)
                                                    .size(15.dp),
                                        ) {
                                            Text(
                                                text = "${

                                                    selectedAssignes.size
                                                }",
                                                modifier =
                                                    Modifier
                                                        .align(Alignment.Center),
                                                style = MaterialTheme.typography.labelSmall,
                                            )
                                        }
                                    }
                                },
                                label = {
                                    Text(
                                        text =

                                            "Assignees",
                                    )
                                },
                            )

                            AnimatedVisibility(showClearChip) {
                                FilterChip(
                                    selected = showClearChip,
                                    onClick = { viewModel.clearFilters() },
                                    label = { Text(text = "Clear Filters") },
                                )
                            }
                        }
                        HorizontalDivider()
                    }
                }
            }
            items(issues.itemCount) { index ->
                val issue = issues[index]
                issue?.let {
                    IssueCard(issue = it, onClick = { })
                }
            }
            issues.apply {
                when {
                    loadState.refresh is LoadState.NotLoading -> {
                        if (loadState.source.append.endOfPaginationReached &&
                            issues.itemCount == 0
                        ) {
                            item {
                                NoIssuesFoundScreen()
                            }
                        }
                    }

                    loadState.refresh is LoadState.Loading -> {
                        item {
                            Box(
                                modifier =
                                    Modifier
                                        .padding(),
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

                                        errorMessage.error.localizedMessage
                                            ?: "Something Went Wrong"

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
}

@Composable
fun LoadingIndicator() {
    CircularProgressIndicator(
        modifier =
            Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .wrapContentWidth(Alignment.CenterHorizontally),
    )
}

@Composable
fun ErrorItem(message: String) {
    Text(
        text = message,
        color = MaterialTheme.colorScheme.error,
        modifier =
            Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .wrapContentWidth(Alignment.CenterHorizontally),
    )
}

@Preview(showBackground = true)
@Composable
fun IssuesScreenPreview() {
    val issues =
        flowOf(
            PagingData.from(
                listOf(
                    issue1,
                    issue2,
                    issue3,
                ),
            ),
        ).collectAsLazyPagingItems()

    // IssuesScreenItems("", issues = issues, onBackClicked = {})
}

val issue1 =
    IssueModel(
        id = "1",
        bodyText = "This is a bug report for a crash onthe login screen.",
        state = "OPEN",
        url = "https://github.com/owner/repo/issues/1",
        title = "Crash on login screen",
        createdAt = "2023-11-01T10:00:00Z",
        label =
            listOf(
                LabelModel("bug", "FF0000"),
                LabelModel("priority:high", "FFFF00"),
            ),
        author = "userA",
        repositoryName = "MyAwesomeApp",
        assignee = listOf("dev1"),
    )

val issue2 =
    IssueModel(
        id = "2",
        bodyText = "Implement a new feature for dark mode support.",
        state = "CLOSED",
        url = "https://github.com/owner/repo/issues/2",
        title = "Dark mode feature",
        createdAt = "2023-10-25T14:30:00Z",
        label = listOf(LabelModel("enhancement", "00FF00")),
        author = "userB",
        repositoryName = "MyAwesomeApp",
        assignee = listOf("dev2", "designer1"),
    )

val issue3 =
    IssueModel(
        id = "3",
        bodyText = null,
        state = "OPEN",
        url = "https://github.com/owner/repo/issues/3",
        title = "Improve documentation",
        createdAt = "2023-11-03T09:15:00Z",
        label = listOf(LabelModel("documentation", "00FF00")),
        author = "userC",
        repositoryName = "MyAwesomeApp",
        assignee = listOf(),
    )
