package com.devmike.repository.screen.components

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun LogoutConfirmationDialog(
    showDialog: Boolean,
    onDismiss: () -> Unit,
    onLogout: () -> Unit,
) {
    if (showDialog) {
        AlertDialog(
            onDismissRequest = onDismiss,
            title = { Text("Logout Confirmation") },
            text = { Text("Are you sure you want to logout?") },
            confirmButton = {
                TextButton(onClick = onLogout) {
                    Text("Yes", color = Color.DarkGray)
                }
            },
            dismissButton = {
                TextButton(onClick = onDismiss) {
                    Text("Keep me logged in")
                }
            },
            modifier = Modifier.testTag("Logout_Dialog"),
        )
    }
}

@Composable
@Preview(
    showBackground = true,
    backgroundColor = 0xFFF0EAE2,
    device = "id:pixel_6",
)
fun LogoutConfirmationPreview() {
    var showDialog by remember { mutableStateOf(true) }
    LogoutConfirmationDialog(showDialog = showDialog, onDismiss = {}, onLogout = {})
}
