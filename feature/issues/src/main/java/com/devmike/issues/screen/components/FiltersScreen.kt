package com.devmike.issues.screen.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.FilterChip
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.devmike.domain.helper.IssueState

@Composable
fun FiltersScreen(
    selectedAssignes: List<String>,
    selectedLabels: List<String>,
    selectedIssueState: IssueState,
    showLabelsDialog: () -> Unit,
    showAssigneesDialog: () -> Unit,
    clearFilters: () -> Unit,
    modifyIssueState: (IssueState) -> Unit,
) {
    val showClearChip = selectedAssignes.isNotEmpty() || selectedLabels.isNotEmpty()
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
                    onStateSelected = modifyIssueState,
                )
                FilterChip(
                    selected = selectedLabels.isNotEmpty(),
                    onClick = showLabelsDialog,
                    leadingIcon = {
                        if (selectedLabels.isNotEmpty()) {
                            Box(
                                modifier =
                                    Modifier
                                        .border(
                                            1.dp,
                                            MaterialTheme.colorScheme.onPrimary,
                                            CircleShape,
                                        ).size(15.dp),
                            ) {
                                Text(
                                    text = "${

                                        selectedLabels.size
                                    }",
                                    modifier =
                                        Modifier
                                            .align(Alignment.Center),
                                    style = MaterialTheme.typography.labelSmall,
                                    color = MaterialTheme.colorScheme.onPrimary,
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
                    onClick = showAssigneesDialog,
                    leadingIcon = {
                        if (selectedAssignes.isNotEmpty()) {
                            Box(
                                modifier =
                                    Modifier
                                        .border(
                                            1.dp,
                                            MaterialTheme.colorScheme.onPrimary,
                                            CircleShape,
                                        ).size(15.dp),
                            ) {
                                Text(
                                    text = "${

                                        selectedAssignes.size
                                    }",
                                    modifier =
                                        Modifier
                                            .align(Alignment.Center),
                                    style = MaterialTheme.typography.labelSmall,
                                    color = MaterialTheme.colorScheme.onPrimary,
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
                        onClick = clearFilters,
                        label = { Text(text = "Clear Filters") },
                    )
                }
            }
            HorizontalDivider()
        }
    }
}
