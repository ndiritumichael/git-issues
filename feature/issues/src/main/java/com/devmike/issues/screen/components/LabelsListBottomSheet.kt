package com.devmike.issues.screen.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
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
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.itemKey
import com.devmike.domain.models.LabelModel

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun LabelsScreen(
    pagedLabels: LazyPagingItems<LabelModel>,
    selectedLabels: Set<LabelModel>,
    showDialog: Boolean,
    onSelectedLabel: (LabelModel) -> Unit,
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
                        modifier = Modifier.fillMaxWidth(),
                        shadowElevation = 4.dp,
                        tonalElevation = 4.dp,
                    ) {
                        Row(
                            modifier =
                                Modifier.fillMaxWidth().padding(horizontal = 8.dp),
                            verticalAlignment = Alignment.CenterVertically,
                        ) {
                            IconButton(onClick = onDismiss) {
                                Icon(
                                    imageVector = Icons.Default.Close,
                                    contentDescription = "close labels dialog",
                                )
                            }
                            Spacer(modifier = Modifier.size(16.dp))
                            Text(
                                text = "Filter by Labels",
                                style = MaterialTheme.typography.titleLarge,
                            )
                        }
                    }
                }
                items(
                    pagedLabels.itemCount,
                    key = pagedLabels.itemKey { it.name },
                ) { index ->

                    pagedLabels[index]?.let { label ->
                        val color = getColorFromHex(label.color)
                        Row(
                            modifier =
                                Modifier.fillMaxWidth().clickable {
                                    onSelectedLabel(label)
                                },
                        ) {
                            FilterChip(
                                selected = false,
                                onClick = { onSelectedLabel(label) },
                                label = {
                                    Text(
                                        text = label.name,
                                        style = MaterialTheme.typography.labelSmall,
                                    )
                                },
                                colors =
                                    FilterChipDefaults.filterChipColors(
                                        containerColor = color,
                                        labelColor =
                                            determineTextColor(
                                                color,
                                            ),
                                    ),
                                shape = CircleShape,
                            )

                            Spacer(modifier = Modifier.weight(1f))

                            AnimatedVisibility(selectedLabels.contains(label)) {
                                Icon(
                                    imageVector = Icons.Default.Check,
                                    contentDescription = "selected label",
                                    tint = MaterialTheme.colorScheme.primary,
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}
