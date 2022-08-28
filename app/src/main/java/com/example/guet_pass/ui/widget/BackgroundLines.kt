package com.example.guet_pass.ui.widget

import androidx.compose.foundation.Canvas
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.guet_pass.ui.theme.BackGround
import com.example.guet_pass.ui.theme.Grey600

@Composable
fun BackgroundLines(
    modifier: Modifier
){
    Canvas(modifier = modifier){
        val canvasWidth = size.width
        val canvasHeight = size.height

        var startX = 0f
        val startY = 0f
        val endX = 0f
        var endY = 0f

        for (coordinate in 0..((canvasHeight + canvasWidth).toInt()) step 100){
            startX = coordinate.toFloat()
            endY = coordinate.toFloat()
            drawLine(
                color = BackGround,
                start = Offset(startX,startY),
                end = Offset(endX,endY),
                strokeWidth = 10.dp.toPx()
            )
        }
    }
}