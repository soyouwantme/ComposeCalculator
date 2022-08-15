package com.example.composecalculator

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

data class Coordinate(
    val x: Float,
    val y: Float
)

data class DigitalLine (
    val startPos: Coordinate,
    val movePos: MutableList<Coordinate>
)

val lineList: MutableList<DigitalLine> = mutableListOf(
    DigitalLine(
        Coordinate(70f, 0f), mutableListOf(
            Coordinate(500f, 0f),
            Coordinate(420f, 80f),
            Coordinate(148f, 80f)
        )
    ),
    DigitalLine(
        Coordinate(60f, 18f), mutableListOf(
            Coordinate(130f, 90f),
            Coordinate(110f, 320f),
            Coordinate(60f, 370f),
            Coordinate(30f, 340f)
        )
    ),
    DigitalLine(
        Coordinate(50f, 390f), mutableListOf(
            Coordinate(100f, 440f),
            Coordinate(80f, 670f),
            Coordinate(0f, 750f),
            Coordinate(20f, 440f)
        )
    ),
    DigitalLine(
        Coordinate(70f, 380f), mutableListOf(
            Coordinate(110f, 340f),
            Coordinate(400f, 340f),
            Coordinate(440f, 380f),
            Coordinate(400f, 420f),
            Coordinate(110f, 420f)
        )
    ),
    DigitalLine(
        Coordinate(510f, 20f), mutableListOf(
            Coordinate(490f, 330f),
            Coordinate(460f, 370f),
            Coordinate(410f, 320f),
            Coordinate(430f, 95f)
        )
    ),
    DigitalLine(
        Coordinate(450f, 390f), mutableListOf(
            Coordinate(480f, 420f),
            Coordinate(450f, 750f),
            Coordinate(380f, 670f),
            Coordinate(400f, 440f)
        )
    ),
    DigitalLine(
        Coordinate(90f, 680f), mutableListOf(
            Coordinate(360f, 680f),
            Coordinate(430f, 760f),
            Coordinate(10f, 760f)
        )
    )
)

fun getColorArr(num: Char): MutableList<Int> {
    return when (num) {
        '0' -> mutableListOf(1, 1, 1, 0, 1, 1, 1)
        '1' -> mutableListOf(0, 0, 0, 0, 1, 1, 0)
        '2' -> mutableListOf(1, 0, 1, 1, 1, 0, 1)
        '3' -> mutableListOf(1, 0, 0, 1, 1, 1, 1)
        '4' -> mutableListOf(0, 1, 0, 1, 1, 1, 0)
        '5' -> mutableListOf(1, 1, 0, 1, 0, 1, 1)
        '6' -> mutableListOf(1, 1, 1, 1, 0, 1, 1)
        '7' -> mutableListOf(1, 0, 0, 0, 1, 1, 0)
        '8' -> mutableListOf(1, 1, 1, 1, 1, 1, 1)
        '9' -> mutableListOf(1, 1, 0, 1, 1, 1, 1)
        '-' -> mutableListOf(0, 0, 0, 1, 0, 0, 0)
        else -> mutableListOf(0, 0, 0, 0, 0, 0, 0)
    }
}

@Composable
fun DigitNum(num: Char, baseSize: Float = 0.1f) {
    Canvas(
        Modifier
            .width(200.dp * baseSize)
            .height(300.dp * baseSize)
    ) {
        val pathList: MutableList<Path> = mutableListOf()
        lineList.map {
            pathList.add(Path().apply {
                this.moveTo(
                    it.startPos.x * baseSize,
                    it.startPos.y * baseSize
                )
                it.movePos.map {
                    this.lineTo(it.x * baseSize, it.y * baseSize)
                }
                this.close()
            })
        }
        pathList.mapIndexed { index, it ->
            drawPath(it, if (getColorArr(num)[index] == 1) Color.Black else Color(200, 200, 174))
        }
    }

}

@Preview(
    showBackground = true,
    showSystemUi = true
)
@Composable
fun DigitNumPreview() {
    DigitNum('8')
}