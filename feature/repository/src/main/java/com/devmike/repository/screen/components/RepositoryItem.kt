package com.devmike.repository.screen.components

import android.content.res.Configuration
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.BugReport
import androidx.compose.material.icons.filled.ForkRight
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.devmike.domain.models.RepositoryModel

@Composable
fun RepositoryItem(
    repository: RepositoryModel,
    onRepositoryClick: (name: String, owner: String) -> Unit,
) {
    val context = LocalContext.current

    Card(
        modifier = Modifier.fillMaxWidth().padding(4.dp).testTag("repository_item"),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        onClick = {
            if (repository.issueCount >
                0
            ) {
                onRepositoryClick(repository.name, repository.owner)
            } else {
                Toast
                    .makeText(
                        context,
                        "No issues found",
                        Toast.LENGTH_SHORT,
                    ).show()
            }
        },
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp),
            ) {
                AsyncImage(
                    model = repository.avatarUrl,
                    contentDescription = null,
                    modifier = Modifier.size(30.dp).clip(CircleShape),
                )

                Text(
                    text = repository.nameWithOwner,
                    style = MaterialTheme.typography.titleMedium,
                )
            }
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = repository.description ?: "No description",
                style = MaterialTheme.typography.bodyMedium,
                color = Color.Gray,
            )
            Spacer(modifier = Modifier.height(8.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Start,
            ) {
                RepositoryStat(Icons.Default.Star, repository.stargazers.toString())
                RepositoryStat(Icons.Default.ForkRight, repository.forkCount.toString())
                RepositoryStat(Icons.Default.BugReport, repository.issueCount.toString())
            }
        }
    }
}

@Composable
fun RepositoryStat(
    icon: ImageVector,
    count: String,
) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            modifier = Modifier.size(16.dp),
        )
        Spacer(modifier = Modifier.width(4.dp))
        Text(text = count, style = MaterialTheme.typography.bodySmall)
    }
}

@Composable
@Preview(
    showBackground = true,
    backgroundColor = 0xFFF0EAE2,
    device = "id:pixel_6_pro",
    uiMode = Configuration.UI_MODE_NIGHT_NO or Configuration.UI_MODE_TYPE_NORMAL,
)
fun RepositoryItemPreview() {
    RepositoryItem(
        repository =
            RepositoryModel(
                name = "Room Database",
                owner = "mike",
                avatarUrl = "https://placehold.co/400",
                nameWithOwner = "mike/Room Database",
                description = "Room: SQLite database object-mapping library",
                stargazers = 1000,
                forkCount = 100,
                issueCount = 10,
                url = "https://github.com/mike/RoomDatabase",
            ),
        onRepositoryClick = { _, _ -> },
    )
}

@Preview(
    showBackground = true,
    backgroundColor = 0xFFF0EAE2,
    device = "spec:id=reference_phone,shape=Normal,width=411,height=891,unit=dp,dpi=420",
)
@Composable
fun RepositoryStatPreview() {
    RepositoryStat(icon = Icons.Default.Star, count = "1000")
}
