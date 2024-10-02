package com.devmike.gitissuesmobile

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp

@Composable
fun GitHubLoginScreen(
    modifier: Modifier,
    onLoginClick: () -> Unit,
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(text = "Welcome to GitIssues", style = MaterialTheme.typography.headlineMedium)
        Spacer(modifier = Modifier.size(16.dp))
        Text(text = "Please login to continue", style = MaterialTheme.typography.bodyMedium)
        Spacer(modifier = Modifier.size(16.dp))
        Image(
            painter = painterResource(id = R.drawable.github_logo),
            contentDescription = "githublogo",
            modifier = Modifier.size(200.dp),
            colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.primary),
        )
        Spacer(modifier = Modifier.size(16.dp))

        Button(onClick = onLoginClick) {
            Text(text = "Login with GitHub")
        }
    }
}
