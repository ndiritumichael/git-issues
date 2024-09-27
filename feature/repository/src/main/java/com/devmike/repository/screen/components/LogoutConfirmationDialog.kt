package com.devmike.repository.screen.components

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

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
        )
    }
}
