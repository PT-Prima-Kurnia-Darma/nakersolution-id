package com.nakersolutionid.nakersolutionid.ui.components

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.SubcomposeLayout
import androidx.compose.ui.text.TextLayoutResult
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


/**
 * A composable that displays text and applies a "running" marquee animation
 * if the text's width exceeds the available space. The animation scrolls back and forth.
 *
 * @param text The text to display.
 * @param modifier The modifier to be applied to the layout.
 * @param color The color of the text.
 * @param fontSize The size of the font.
 * @param fontWeight The weight of the font.
 */
@Composable
fun RunningText(
    text: String,
    modifier: Modifier = Modifier,
    color: Color = Color.Unspecified,
    fontSize: TextUnit = TextUnit.Unspecified,
    fontWeight: FontWeight? = null
) {
    // A key that changes when the text or font size changes, to restart the animation
    val key = text to fontSize

    // State to hold the animation transition
    val transition = rememberInfiniteTransition(label = "runningTextTransition")

    // State to track if the text is overflowing
    var isOverflowing by remember(key) { mutableStateOf(false) }

    // The animation value for the text's horizontal offset
    val xOffset by transition.animateFloat(
        initialValue = 0f,
        targetValue = if (isOverflowing) -1f else 0f,
        animationSpec = infiniteRepeatable(
            animation = tween(
                durationMillis = 5000, // Duration for one scroll direction
                delayMillis = 1000,   // Delay before starting/reversing
                easing = LinearEasing
            ),
            repeatMode = RepeatMode.Reverse
        ),
        label = "xOffsetAnimation"
    )

    // A SubcomposeLayout is used to measure the text and the container separately.
    // This allows us to determine if the text overflows the container's width.
    SubcomposeLayout(
        modifier = modifier.clipToBounds() // Clip the content to the bounds of the container
    ) { constraints ->
        // 1. Measure the text with its given style but without width constraints
        val textPlaceable = subcompose("text") {
            Text(
                text = text,
                color = color,
                fontSize = fontSize,
                fontWeight = fontWeight,
                maxLines = 1,
                overflow = TextOverflow.Visible, // Important to measure the full width
                onTextLayout = { textLayoutResult: TextLayoutResult ->
                    // Check if the measured text width is greater than the available width
                    isOverflowing = textLayoutResult.size.width > constraints.maxWidth
                }
            )
        }.first().measure(constraints.copy(maxWidth = Int.MAX_VALUE))

        // 2. Calculate the layout for the text
        val textWidth = textPlaceable.width
        val containerWidth = constraints.maxWidth

        // The offset is the difference between the text and container width.
        // The animation value (xOffset) interpolates between 0 and this difference.
        val offsetX = ((textWidth - containerWidth).coerceAtLeast(0) * xOffset).toInt()

        // 3. Place the text composable in the layout
        layout(containerWidth, textPlaceable.height) {
            textPlaceable.placeRelative(x = offsetX, y = 0)
        }
    }
}

// --- Preview and Example Usage ---

@Composable
fun RunningTextExample() {
    Surface(
        color = Color(0xFF111827), // bg-gray-900
        modifier = Modifier.fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(32.dp),
            verticalArrangement = Arrangement.Center
        ) {
            // Example 1: Text that fits and does not scroll
            Text(
                "Short Text",
                color = Color.White,
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = 16.dp)
            )
            Surface(
                color = Color(0xFF374151), // bg-gray-700
                shape = MaterialTheme.shapes.medium,
                modifier = Modifier.fillMaxWidth()
            ) {
                RunningText(
                    text = "This text is short and fits perfectly.",
                    fontSize = 20.sp,
                    color = Color.White,
                    modifier = Modifier.padding(16.dp)
                )
            }

            Spacer(Modifier.height(40.dp))

            // Example 2: Text that overflows and scrolls back and forth
            Text(
                "Overflowing Text",
                color = Color.White,
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = 16.dp)
            )
            Surface(
                color = Color(0xFF374151), // bg-gray-700
                shape = MaterialTheme.shapes.medium,
                modifier = Modifier.fillMaxWidth()
            ) {
                RunningText(
                    text = "This is a much longer piece of text that will definitely overflow the container and trigger the running animation.",
                    fontSize = 20.sp,
                    color = Color.White,
                    modifier = Modifier.padding(16.dp)
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun RunningTextPreview() {
    RunningTextExample()
}