package com.devmike.issues.screen.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.luminance
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.devmike.domain.models.IssueModel
import com.devmike.domain.models.LabelModel
import com.devmike.issues.GithubGreen
import com.devmike.issues.LUMINANCE_THRESHOLD
import com.devmike.issues.relativeTime
import timber.log.Timber

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun IssueCard(
    issue: IssueModel,
    onClick: () -> Unit,
) {
    val stateBackGround =
        when (issue.state) {
            "open" -> GithubGreen
            "closed" -> Color.Blue
            else -> Color.Gray
        }

    Column(
        modifier = Modifier.padding(8.dp).clickable { onClick() },
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth(),
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.weight(1f),
            ) {
                Icon(
                    imageVector = Icons.Outlined.Person,
                    contentDescription = "Author icon",
                    modifier = Modifier.size(24.dp),
                )

                val annotatedString =
                    buildAnnotatedString {
                        append(issue.author)
                        withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                            append(" • ")
                        }
                        withStyle(style = SpanStyle(fontStyle = FontStyle.Italic)) {
                            append(relativeTime(issue.createdAt))
                        }
                    }

                Text(text = annotatedString, style = MaterialTheme.typography.bodyMedium)
            }

            Text(
                text = issue.state,
                modifier =
                    Modifier
                        .background(
                            stateBackGround,
                            shape = RoundedCornerShape(10.dp),
                        ).padding(vertical = 2.dp, horizontal = 4.dp),
                color = determineTextColor(stateBackGround),
            )
        }
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = issue.title,
            style = MaterialTheme.typography.titleMedium,
        )

        Spacer(modifier = Modifier.height(8.dp))
        FlowRow(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(4.dp),
            verticalArrangement = Arrangement.spacedBy(4.dp),
        ) {
            issue.label.forEach { label ->
                LabelChip(labelModel = label) {}
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        HorizontalDivider(modifier = Modifier.fillMaxWidth())
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
                state = "open",
                author = "User A",
                createdAt = "2023-10-26T10:00:00Z",
                label =
                    listOf(
                        LabelModel("bug", "FF0000"),
                        LabelModel("priority:high", "FFFF00"),
                    ),
                repositoryName = "flutter/flutter",
                assignee = listOf(),
                url = " https://github.com/flutter/flutter",
            ),
    ) {}
}

/**
 * Determines the appropriate text color based on the given background color hex value.
 *
 * @param backgroundColorHex The hexadecimal color code representing the background color.
 * @return The appropriate text color as a [Color] object.
 * The text color will be black if the background color has a luminance greater than 0.5, and white otherwise.
 */
fun determineTextColor(backgroundColor: Color): Color {
    val brightness = backgroundColor.luminance()
    return if (brightness > LUMINANCE_THRESHOLD) Color.Black else Color.White
}

fun getColorFromHex(colorHex: String): Color =
    try {
        Color(android.graphics.Color.parseColor("#$colorHex"))
    } catch (e: IllegalArgumentException) {
        Timber.tag("colorexception").d(e.cause)
        Color.Gray
    }

@Composable
fun LabelChip(
    labelModel: LabelModel,
    onClick: () -> Unit,
) {
    val color = getColorFromHex(labelModel.color)

    Text(
        text = labelModel.name,
        style = MaterialTheme.typography.labelMedium,
        color =
            determineTextColor(
                color,
            ),
        modifier =
            Modifier
                .clip(CircleShape)
                .background(color)
                .padding(
                    4.dp,
                ).clickable { onClick() },
    )
}
