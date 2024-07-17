package com.task.clockanimation.ui

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import kotlinx.coroutines.delay
import java.util.Calendar
import kotlin.math.cos
import kotlin.math.sin

@Composable
fun ClockHands(hourAngle: Float, minuteAngle: Float, circleSize: Dp) {
    Canvas(modifier = Modifier.size(circleSize)) {
        val radius = size.minDimension / 2



        // Draw hour hand
        drawLine(
            color = Color.Red,
            strokeWidth = 8f,
            cap = StrokeCap.Round,
            start = center,
            end = getPoint(center.x, center.y, hourAngle-90, radius*0.5f)
        )

        // Draw minute hand
        drawLine(
            color = Color.Blue,
            strokeWidth = 4f,
            cap = StrokeCap.Round,
            start = center,
            end = getPoint(center.x, center.y, minuteAngle-90, radius * 0.8f)
        )
    }
}

private fun getPoint(centerX: Float, centerY: Float, angleDegrees: Float, length: Float): Offset {
    val angleRadians = Math.toRadians(angleDegrees.toDouble())
    val x = centerX + length * cos(angleRadians).toFloat()
    val y = centerY + length * sin(angleRadians).toFloat()
    return Offset(x, y)
}

@Composable
fun ClockAnimation(circleSize: Dp) {
    var currentTime by remember { mutableStateOf(Calendar.getInstance()) }

    LaunchedEffect(key1 = Unit) {
        while (true) {
            delay(1000) // Update every second
//            currentTime.timeInMillis = System.currentTimeMillis()
            currentTime = Calendar.getInstance()
        }
    }

    val hourAngle = (360 / 12) * (currentTime.get(Calendar.HOUR)) + (currentTime.get(Calendar.MINUTE) * 0.5f)
    val minuteAngle = (360 / 60) * currentTime.get(Calendar.MINUTE)

    ClockHands(hourAngle, minuteAngle.toFloat(),circleSize)
}
