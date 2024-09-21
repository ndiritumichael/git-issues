package com.devmike.issues.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Error
import androidx.compose.material.icons.filled.RadioButtonUnchecked
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.devmike.domain.models.IssueModel

@Composable
fun IssueCard(
    issue: IssueModel,
    onClick: () -> Unit,
) {
    Card(
        modifier =
            Modifier
                .fillMaxWidth()
                .padding(4.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
        ) {
            Text(
                text = issue.title,
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.primary,
            )
            Spacer(modifier = Modifier.height(8.dp))
            Row(
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Icon(
                    imageVector =
                        when (issue.state) {
                            "OPEN" -> Icons.Default.RadioButtonUnchecked
                            "CLOSED" -> Icons.Default.CheckCircle
                            else -> Icons.Default.Error
                        },
                    contentDescription = "Issue State",
                    tint =
                        when (issue.state) {
                            "OPEN" -> MaterialTheme.colorScheme.error
                            "CLOSED" -> MaterialTheme.colorScheme.primary
                            else -> MaterialTheme.colorScheme.onSurface
                        },
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = issue.state,
                    style = MaterialTheme.typography.bodyMedium,
                )
            }
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "Created by ${issue.author} on ${issue.createdAt}",
                style = MaterialTheme.typography.bodySmall,
            )
            Spacer(modifier = Modifier.height(8.dp))
            if (issue.bodyText != null) {
                Text(
                    text = issue.bodyText!!,
                    style = MaterialTheme.typography.bodyMedium,
                    maxLines = 3,
                    overflow = TextOverflow.Ellipsis,
                )
                Spacer(modifier = Modifier.height(8.dp))
            }

            Spacer(modifier = Modifier.height(8.dp))
            if (issue.assignee.isNotEmpty()) {
                Text(
                    text = "Assignees: ${issue.assignee.joinToString(", ")}",
                    style = MaterialTheme.typography.bodySmall,
                )
            }
        }
    }
}

@Composable
fun FlowRow(
    modifier: Modifier = Modifier,
    mainAxisSpacing: Int = 0,
    crossAxisSpacing: Int = 0,
    content: @Composable () -> Unit,
) {
    Layout(
        content = content,
        modifier = modifier,
    ) { measurables, constraints ->
        val rows = mutableListOf<MutableList<Pair<Int, Int>>>()
        var rowConstraints = constraints
        var rowWidth = 0

        measurables.forEach { measurable ->
            val placeable = measurable.measure(rowConstraints)
            if (rowWidth + placeable.width + mainAxisSpacing > constraints.maxWidth) {
                rows.add(mutableListOf())
                rowConstraints = constraints
                rowWidth = 0
            }
            rows.lastOrNull()?.add(Pair(placeable.width, placeable.height))
            rowWidth += placeable.width + mainAxisSpacing
            rowConstraints =
                rowConstraints.copy(
                    maxWidth = constraints.maxWidth - rowWidth,
                )
        }

        val rowHeights = rows.map { row -> row.maxOfOrNull { it.second } ?: 0 }
        val layoutHeight = rowHeights.sumOf { it } + (rows.size - 1) * crossAxisSpacing
        layout(constraints.maxWidth, layoutHeight) {
            var yPosition = 0
            rows.forEachIndexed { rowIndex, row ->
                var xPosition = 0
                row.forEach { (width, height) ->
                    measurables[yPosition].measure(constraints).place(xPosition, yPosition)
                    xPosition += width + mainAxisSpacing
                }
                yPosition += rowHeights[rowIndex] + crossAxisSpacing
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun IssueCardPreview() {
    IssueCard(
        issue =
            IssueModel(
                id = "2",
                title = "Issue Title",
                state = "Open",
                author = "User A",
                createdAt = "2023-10-26T10:00:00Z",
                label = listOf("Bug", "High Priority"),
                repositoryName = "flutter/flutter",
                assignee = listOf(),
                url = " https://github.com/flutter/flutter",
            ),
    ) {
    }
}
