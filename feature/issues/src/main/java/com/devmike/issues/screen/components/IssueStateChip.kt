package com.devmike.issues.screen.components

import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Check
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MenuAnchorType
import androidx.compose.material3.SuggestionChip
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.devmike.domain.helper.IssueState

@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterial3Api::class)
@Composable
fun IssueStateChip(
    selectedState: IssueState,
    states: List<IssueState>,
    onStateSelected: (IssueState) -> Unit,
    modifier: Modifier = Modifier,
) {
    var expanded by remember { mutableStateOf(false) }

    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = { expanded = !expanded },
        modifier = modifier,
    ) {
        SuggestionChip(
            onClick = { },
            label = {
                Row(verticalAlignment = Alignment.CenterVertically) {
//

                    Text(
                        text = selectedState.state,
                        style = TextStyle.Default.copy(fontSize = 14.sp),
                        modifier = Modifier,
                        fontWeight = FontWeight.Black,
                    )
                    ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded)
                }
            },
            modifier =
                Modifier
                    .menuAnchor(
                        MenuAnchorType.PrimaryNotEditable,
                    ).exposedDropdownSize(false)
                    .widthIn(50.dp),
        )

        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            modifier = Modifier.width(IntrinsicSize.Min),
        ) {
            states.forEach { state ->
                DropdownMenuItem(
                    leadingIcon = {
                        if (state == selectedState) {
                            Icon(imageVector = Icons.Outlined.Check, contentDescription = null)
                        }
                    },
                    text = { Text(state.state) },
                    onClick = {
                        onStateSelected(state)
                        expanded = false
                    },
                )
            }
        }
    }
}
