package com.krypton.flashbeam.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.krypton.flashbeam.models.AbstractOptionItem


@Composable
fun <T : AbstractOptionItem> SelectOption(
    expanded: MutableState<Boolean>,
    selections: List<T>,
    default: T,
    onChange: (T) -> Unit,
    modifier: Modifier = Modifier,
    color: Color,
) {
    var selected by remember {
        mutableStateOf(default)
    }
    Box(
        modifier = Modifier
            .border(2.dp, color, RoundedCornerShape(10.dp))
            .clickable {
                expanded.value = true
            }
            .then(modifier)) {
        Text(
            selected.name,
            fontSize = 16.sp,
            color = color,
            modifier = Modifier
                .padding(horizontal = 20.dp, vertical = 10.dp)
        )
        DropdownMenu(
            expanded.value,
            onDismissRequest = {
                expanded.value = false
            },
            modifier = modifier
                .then(modifier)
        ) {
            selections.forEachIndexed { index, it ->
                DropdownMenuItem(onClick = {
                    selected = it
                    expanded.value = false
                    onChange(it)
                }, text = {
                    Text(
                        it.name,
                        fontSize = 16.sp,
                        fontWeight = if (it == selected) FontWeight.Bold else null,
                        color = color,
                    )
                })
            }
        }
    }
}