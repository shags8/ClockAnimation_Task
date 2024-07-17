package com.task.clockanimation.ui

import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Fill
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.clipPath
import androidx.compose.ui.text.style.TextOverflow.Companion.Clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.sin
import kotlin.properties.Delegates

@Composable
fun ColorfulCircleWithWaves() {

    val colors = listOf(
        Color.Red, Color.Green, Color.Blue, Color.Yellow,
        Color.Magenta, Color.Cyan, Color.Gray)

    // Remember the InfiniteTransition instance
    val transition = rememberInfiniteTransition(label = "")

    // Animate a Float value from 0 to 1 infinitely
    val animatedProgress by transition.animateFloat(
        initialValue = 0f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(
                durationMillis = 4000,
                easing = LinearEasing
            ),
            repeatMode = RepeatMode.Restart
        ), label = ""
    )
    // Generate unique random phase shifts for each color segment
    val randomPhaseShifts = remember {
        List(colors.size) { Math.random().toFloat() * 2 * PI.toFloat() }
    }

    Canvas(
        modifier = Modifier.size(200.dp)
    ) {
        val radius = size.minDimension / 2
        val strokeWidth = 1f // Adjust as needed
        val centerX = size.width / 2
        val centerY = size.height / 2


        // Calculate the start angle for each color segment
        var startAngle = 0f
        val sweepAngle = 360f / colors.size
        val smallAmplitude = 15f
        val waveFrequency = 1

        // Draw the circle with colored segments
        colors.forEachIndexed { index, color ->
            drawArc(
                color = color,
                startAngle = startAngle,
                sweepAngle = sweepAngle,
                useCenter = false,
                topLeft = Offset(center.x - radius, center.y - radius),
                size = Size(radius * 2, radius * 2),
                style = Stroke(width = strokeWidth.dp.toPx(), cap = StrokeCap.Round)
            )
                val path = Path().apply {
                    for (i in 0..100) {
                        val randomAmplitude = (Math.random().toFloat() * 100 )
                        val randomPhaseShift = randomPhaseShifts[index]
                        val segmentProgress = i / 100f
                        val angle = (startAngle+segmentProgress * sweepAngle) * PI.toFloat() / 180f
                        val modulatedAmplitude = randomAmplitude * cos(segmentProgress * PI.toFloat() + PI.toFloat() / 2)
                        val waveOffset = modulatedAmplitude * sin(waveFrequency * angle + animatedProgress * 2 * PI.toFloat() + randomPhaseShift)
                        val x = centerX + (radius + waveOffset) * cos(angle)
                        val y = centerY + (radius + waveOffset) * sin(angle)
                        if (i == 0) {
                            moveTo(x, y)
                        } else {
                            lineTo(x, y)
                        }

                    }
                }
                val clipPath = Path().apply {
                    addOval(Rect(center = Offset(centerX, centerY), radius = radius))
                }

                // Clip the wave path to only show the part outside the circle
                clipPath(clipPath, clipOp = androidx.compose.ui.graphics.ClipOp.Difference) {
                    drawPath(path, color = color, style = Fill)
                }
//Extra Circle
            val randomPhaseShift2 = Math.random().toFloat() * 2 * PI.toFloat()
            val path2 = Path().apply {
                for (i in 0..100) {
                    val segmentProgress = i / 100f
                    val angle = (startAngle+segmentProgress * sweepAngle) * PI.toFloat() / 180f
                    val modulatedAmplitude = smallAmplitude * cos(segmentProgress * PI.toFloat() + PI.toFloat() / 2)
                    val waveOffset = modulatedAmplitude * sin(waveFrequency * angle + animatedProgress * 2 * PI.toFloat() + randomPhaseShift2)
                    val x = centerX + (radius + waveOffset) * cos(angle)
                    val y = centerY + (radius + waveOffset) * sin(angle)
                    if (i == 0) {
                        moveTo(x, y)
                    } else {
                        lineTo(x, y)
                    }

                }
            }
            val clipPath2 = Path().apply {
                addOval(Rect(center = Offset(centerX, centerY), radius = radius))
            }

            // Clip the wave path to only show the part outside the circle
            clipPath(clipPath2, clipOp = androidx.compose.ui.graphics.ClipOp.Difference) {
                drawPath(path2, color = color, style = Fill)
            }
            startAngle += sweepAngle
        }
    }

}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun ColorfulCircle() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        ColorfulCircleWithWaves()
    }
}