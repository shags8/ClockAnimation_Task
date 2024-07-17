package com.task.clockanimation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.task.clockanimation.ui.ClockAnimation
import com.task.clockanimation.ui.ColorfulCircle
import com.task.clockanimation.ui.ColorfulCircleWithWaves
import com.task.clockanimation.ui.theme.ClockAnimationTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ClockAnimationTheme {
                Box(modifier = Modifier.fillMaxSize()
                    .background(Color.Black), contentAlignment = Alignment.Center) {
                    Screen()
                }
            }
        }
    }

    @Composable
    fun Screen(){
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            ColorfulCircle()
            ClockAnimation(200.dp)
        }
    }
}