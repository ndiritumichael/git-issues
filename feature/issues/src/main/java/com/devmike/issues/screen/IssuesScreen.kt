package com.devmike.issues.screen

import android.widget.Toast
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.devmike.domain.models.IssueModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flowOf

@Composable
fun IssuesScreen(viewModel: IssuesViewModel = hiltViewModel()) {
    val pagedissues = viewModel.issuesResults.collectAsLazyPagingItems()
    IssuesScreenItems(issues = pagedissues, onIssueClick = {})
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun IssuesScreenItems(
    issues: LazyPagingItems<IssueModel>,
    onIssueClick: (IssueModel) -> Unit,
) {
    val context = LocalContext.current

    LaunchedEffect(key1 = true) {
        while (true) {
            Toast.makeText(context, "size is ${issues.itemCount}", Toast.LENGTH_SHORT).show()

            delay(1000)
        }
//
    }
    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Issues") })
        },
    ) { paddingValues ->
        LazyColumn(
            contentPadding = paddingValues,
            modifier = Modifier.fillMaxSize(),
        ) {
            items(issues.itemCount) { index ->
                val issue = issues[index]
                issue?.let {
                    IssueCard(issue = it, onClick = { onIssueClick(it) })
                }
            }
            issues.apply {
                when {
                    loadState.refresh is LoadState.Loading -> {
                        item { CircularProgressIndicator(modifier = Modifier.fillMaxSize()) }
                    }

                    loadState.append is LoadState.Loading -> {
                        item { CircularProgressIndicator(modifier = Modifier.padding(16.dp)) }
                    }

                    loadState.refresh is LoadState.Error -> {
                        item {
                            ErrorItem(
                                message =
                                    (loadState.refresh as LoadState.Error).error.message
                                        ?: "Error refreshing data",
                            )
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

    IssuesScreenItems(issues = issues, onIssueClick = {})
}

val issue1 =
    IssueModel(
        id = "1",
        bodyText = "This is a bug report for a crash onthe login screen.",
        state = "OPEN",
        url = "https://github.com/owner/repo/issues/1",
        title = "Crash on login screen",
        createdAt = "2023-11-01T10:00:00Z",
        label = listOf("bug", "priority:high"),
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
        label = listOf("enhancement"),
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
        label = listOf("documentation"),
        author = "userC",
        repositoryName = "MyAwesomeApp",
        assignee = listOf(),
    )
