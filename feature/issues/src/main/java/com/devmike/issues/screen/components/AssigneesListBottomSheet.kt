package com.devmike.issues.screen.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.itemKey
import coil.compose.AsyncImage
import com.devmike.domain.models.AssigneeModel

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun AssigneesScreen(
    pagedAssignees: LazyPagingItems<AssigneeModel>,
    selectedAssignees: Set<AssigneeModel>,
    onAssigneeSelected: (AssigneeModel) -> Unit,
    showDialog: Boolean,
    onDismiss: () -> Unit,
) {
    if (showDialog) {
        ModalBottomSheet(
            onDismissRequest = onDismiss,
            modifier = Modifier.statusBarsPadding(),
        ) {
            LazyColumn(
                modifier = Modifier.padding(16.dp),
            ) {
                stickyHeader {
                    Surface(
                        shadowElevation = 4.dp,
                        tonalElevation = 4.dp,
                    ) {
//

                        Row(
                            modifier =
                                Modifier
                                    .fillMaxWidth()
                                    .padding(horizontal = 8.dp),
                            verticalAlignment = Alignment.CenterVertically,
                        ) {
                            IconButton(onClick = onDismiss) {
                                Icon(
                                    imageVector = Icons.Default.Close,
                                    contentDescription = "close assignees dialog",
                                )
                            }
                            Spacer(modifier = Modifier.size(16.dp))
                            Text(
                                text = "Filter by Assignees",
                                style = MaterialTheme.typography.titleLarge,
                            )
                        }
                    }
                }
                items(
                    pagedAssignees.itemCount,
                    key = pagedAssignees.itemKey { it.username },
                ) { index ->

                    pagedAssignees[index]?.let {
                        AssigneesItem(assignee = it, isSelected = selectedAssignees.contains(it)) {
                            onAssigneeSelected(it)
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun AssigneesItem(
    assignee: AssigneeModel,
    isSelected: Boolean = false,
    onItemClick: () -> Unit,
) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        verticalAlignment = Alignment.CenterVertically,
        modifier =
            Modifier
                .fillMaxWidth()
                .clickable { onItemClick() }
                .padding(4.dp),
    ) {
        AsyncImage(
            model = assignee.avatarUrl,
            contentDescription = "UserImage",
            modifier =
                Modifier
                    .size(50.dp)
                    .clip(
                        CircleShape,
                    ),
        )

        Column {
            Text(
                text = assignee.name ?: "",
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.Bold,
            )
            Text(
                text = assignee.username,
                style = MaterialTheme.typography.bodySmall,
            )
        }
        Spacer(modifier = Modifier.weight(1f))

        AnimatedVisibility(isSelected) {
            Icon(
                imageVector = Icons.Default.Check,
                contentDescription = "selected assignee",
                tint = MaterialTheme.colorScheme.primary,
            )
        }
    }
}
