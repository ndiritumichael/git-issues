package com.devmike.issues.screen

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp

@Composable
fun SearchTextField(
    searchText: String,
    onSearchTextChanged: (String) -> Unit,
    modifier: Modifier = Modifier,
    onSearchClicked: () -> Unit,
) {
    var showClearIcon by remember { mutableStateOf(false) }

    OutlinedTextField(
        value = searchText,
        onValueChange = {
            onSearchTextChanged(it)
            showClearIcon = it.isNotEmpty()
        },
        modifier =
            modifier
                .fillMaxWidth()
                .padding(16.dp),
        leadingIcon = {
            Icon(
                imageVector = Icons.Filled.Search,
                contentDescription = "Search Icon",
            )
        },
        trailingIcon = {
            if (showClearIcon) {
                IconButton(onClick = { onSearchTextChanged("") }) {
                    Icon(
                        imageVector = Icons.Filled.Clear,
                        contentDescription = "Clear Icon",
                    )
                }
            }
        },
        placeholder = { Text("Search repositories") },
        keyboardOptions =
            KeyboardOptions(
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Search,
            ),
        keyboardActions =
            KeyboardActions(
                onSearch = {
                    onSearchClicked()
                },
            ),
    )
}
