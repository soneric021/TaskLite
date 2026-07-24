package com.ericsonmontero.tasklite.presentation.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.dp

@Composable
fun CollapsibleComponent(modifier: Modifier = Modifier, title: String, isExpanded: Boolean = false,onExpand: () -> Unit = {}, content: @Composable () -> Unit) {
    val angle by animateFloatAsState(
        targetValue = if (isExpanded) 180f else 0f,
        animationSpec = tween(durationMillis = 300),
        label = "ArrowRotation"
    )
    Card(modifier.fillMaxWidth().padding(horizontal = 16.dp).clickable { onExpand.invoke() }) {
        Column {
            Row(
                Modifier.fillMaxWidth().padding(16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(title, Modifier, style = MaterialTheme.typography.titleSmall)
                Icon(

                    Icons.Filled.KeyboardArrowDown,
                    contentDescription = null,
                    modifier = Modifier.graphicsLayer {
                        rotationZ = angle
                    },
                )
            }
            if (isExpanded) Divider(Modifier.padding(horizontal = 12.dp), color = MaterialTheme.colorScheme.onSurface)
            AnimatedVisibility(isExpanded, enter = fadeIn(), exit = fadeOut()) {
                Box(Modifier.padding(16.dp)) { content() }
            }
        }
    }
}