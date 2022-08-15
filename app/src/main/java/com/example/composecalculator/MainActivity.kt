package com.example.composecalculator

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.*
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.composecalculator.ui.theme.*

data class CalBtnBean(val text: String, val textColor: Color, val backgroundColors: List<Color>, val onClick: () -> Unit = {})

data class CalState(val num1: Int = 0, val num2: Int = 0, val opt: String? = null)

class MainActivity : ComponentActivity() {
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ComposeCalculatorTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    Calculator()
                }
            }
        }
    }
    
}

val buttonList = arrayOf(
    arrayOf(
        CalBtnBean("C", Color.White, GrayColorList),
        CalBtnBean("+/-", Color.White, GrayColorList),
        CalBtnBean("%", Color.White, GrayColorList),
        CalBtnBean("/", Color.White, GrayColorList)
    ),
    arrayOf(
        CalBtnBean("7", BlackFont, WhiteColorList),
        CalBtnBean("8", BlackFont, WhiteColorList),
        CalBtnBean("9", BlackFont, WhiteColorList),
        CalBtnBean("x", Color.White, GrayColorList)
    ),
    arrayOf(
        CalBtnBean("4", BlackFont, WhiteColorList),
        CalBtnBean("5", BlackFont, WhiteColorList),
        CalBtnBean("6", BlackFont, WhiteColorList),
        CalBtnBean("-", Color.White, GrayColorList)
    ),
    arrayOf(
        CalBtnBean("1", BlackFont, WhiteColorList),
        CalBtnBean("2", BlackFont, WhiteColorList),
        CalBtnBean("3", BlackFont, WhiteColorList),
        CalBtnBean("+", Color.White, GrayColorList)
    ),
    arrayOf(
        CalBtnBean("0", BlackFont, WhiteColorList),
        CalBtnBean(".", BlackFont, WhiteColorList),
        CalBtnBean("=", Color.White, OrangeColorList)
    )
)

@Composable
fun Calculator() {
    var state by remember {
        mutableStateOf(CalState())
    }

    Column(
        Modifier
            .background(BackgroundGray)
            .padding(horizontal = 10.dp)
    ) {
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 10.dp),
            text = "CALCULATOR",
            color = Color(206, 207, 205),
            textAlign = TextAlign.Center,
            fontFamily = FontFamily.SansSerif,
            fontWeight = FontWeight.Black
        )
        Screen(
            Modifier
                .wrapContentHeight()
                .fillMaxWidth()
                .padding(bottom = 40.dp),
            state.num2
        )
        Column(Modifier.fillMaxSize()) {
            buttonList.forEach {
                Row(
                    Modifier.weight(1f),
                    Arrangement.spacedBy(10.dp)
                ) {
                    it.forEach {
                        CalBtn(
                            Modifier
                                .weight(if (it.text == "0") 2f else 1f)
                                .aspectRatio(if (it.text == "0") 2f else 1f),
                            it
                        ) {
                            state = calculate(state, it.text)
                        }
                    }
                }
            }
        }
    }
}

fun calculate(curState: CalState, input: String): CalState {
    return when (input) {
        in "0".."9" -> curState.copy(num2 = input.toInt(), num1 = curState.num2)
        in arrayOf("+", "-", "x", "/") -> curState.copy(opt = input)
        in "=" -> {
            val calResult = when (curState.opt) {
                "+" -> curState.num1 + curState.num2
                "-" -> curState.num1 - curState.num2
                "x" -> curState.num1 * curState.num2
                "/" -> curState.num1 / curState.num2
                else -> 0
            }
            if (calResult > -10000000 && calResult < 10000000) {
                curState.copy(num2 = calResult)
            } else {
                curState.copy(num2 = 0)
            }

        }
        in "C" -> {
            curState.copy(num1 = 0, num2 = 0, opt = null)
        }
        else -> curState
    }
}

@Composable
fun Screen(modifier: Modifier, result: Int) {
    val resList = result.toString().toCharArray()
    Box(
        modifier
            .background(
                Color(204, 206, 179),
                shape = RoundedCornerShape(10.dp)
            )
            .border(10.dp, Color(92, 90, 84), RoundedCornerShape(10.dp))
            .padding(vertical = 30.dp, horizontal = 20.dp),
        contentAlignment = Alignment.CenterEnd
    ) {
        Row(
        ) {
            resList.map {
                DigitNum(it, 0.2f)
            }
        }
    }
}

@Composable
fun CalBtn(modifier: Modifier, symbol: CalBtnBean, onClick: () -> Unit = {}) {
    Box(
        modifier
            .background(
                brush = Brush.verticalGradient(
                    symbol.backgroundColors
                ),
                shape = RoundedCornerShape(15)
            )
            .border(2.dp, Color(175, 176, 173), RoundedCornerShape(10.dp))
            .clickable { onClick.invoke() },
        contentAlignment = Alignment.Center
    ) {
        Text(
            symbol.text,
            fontSize = 40.sp,
            color = symbol.textColor,
            fontFamily = FontFamily.SansSerif,
            fontWeight = FontWeight.Black
        )
    }
}

@Preview(
    showBackground = true,
    showSystemUi = true
)
@Composable
fun DefaultPreview() {
    ComposeCalculatorTheme {
        Calculator()
    }
}