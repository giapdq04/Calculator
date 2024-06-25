package fpoly.giapdqph34273.caculator

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.graphics.toColorInt

@Composable
@Preview(showBackground = true)
fun MainScreen() {
    var operation by rememberSaveable { mutableStateOf("") }
    var result by rememberSaveable { mutableStateOf("") }
    val context = LocalContext.current

    fun khongHopLe(text:String = "Định dạng đã dùng không hợp lệ") {
        Toast.makeText(context, text, Toast.LENGTH_SHORT).show()
        return
    }

    fun evaluate(expression: String): Double {
        val operators = listOf('+', '-', '×', '÷')
        var operator: Char? = null
        var index = -1

        // Tìm toán tử trong biểu thức
        for (i in expression.indices) {
            if (expression[i] in operators) {
                operator = expression[i]
                index = i
                break
            }
        }

        // Tách biểu thức thành hai phần
        val num1 = expression.substring(0, index).trim().toDouble()
        val num2 = expression.substring(index + 1).trim().toDouble()

        return when (operator) {
            '+' -> num1 + num2
            '-' -> num1 - num2
            '×' -> num1 * num2
            '÷' -> num1 / num2
            else -> throw IllegalArgumentException("Toán tử không hợp lệ")
        }
    }

    fun tinhToan() {
        val operators = listOf('+', '-', '×', '÷')

        // validate không nhập số và toán tử (+, -, ×, ÷)
        if (!operation.matches("^[0-9×÷+-]*[0-9]$".toRegex())) {
            khongHopLe()
            return
        }

        // validate không nhập dấu
        if (!operators.any { it in operation }) {
            khongHopLe("Chưa nhập dấu")
            return
        }

        // kết quả
        result = evaluate(operation).toString()
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .safeDrawingPadding()
            .padding(30.dp)
    ) {
        Row(
            modifier = Modifier
                .weight(1.4f)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.End,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = operation,
                fontSize = 30.sp
            )
        }

        Row(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.End,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = result,
                fontSize = 20.sp,
                color = Color("#8b8b8e".toColorInt())
            )
        }

        Row(
            modifier = Modifier
                .weight(0.9f)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.End,
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = {
                operation = operation.dropLast(1)
            }) {
                Icon(
                    painter = painterResource(id = R.drawable.green_backspace),
                    contentDescription = null,
                    tint = Color((if (operation.isBlank()) "#848487" else "#42A610").toColorInt())
                )
            }
        }

        HorizontalDivider()

        Column(
            modifier = Modifier
                .weight(5f)
        ) {
            Spacer(modifier = Modifier.height(30.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                BtnNumber("C", "#d93d34") {
                    operation = ""
                    result = ""
                }
                BtnNumber("( )", "#42A610")
                BtnNumber("%", "#42A610") {
                    if (operation.isBlank()) {
                        khongHopLe()
                    } else {
                        operation += "%"
                    }
                }
                BtnNumber("÷", "#42A610", fontSize = 50f) {
                    if (operation.isBlank()) {
                        khongHopLe()
                    } else {
                        operation += "÷"
                    }
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                BtnNumber("7") {
                    operation += "7"
                }
                BtnNumber("8") {
                    operation += "8"
                }
                BtnNumber("9") {
                    operation += "9"
                }
                BtnNumber("×", "#42A610", fontSize = 50f) {
                    if (operation.isBlank()) {
                        khongHopLe()
                    } else {
                        operation += "×"
                    }
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                BtnNumber("4") {
                    operation += "4"
                }
                BtnNumber("5") {
                    operation += "5"
                }
                BtnNumber("6") {
                    operation += "6"
                }
                BtnNumber("-", "#42A610", fontSize = 50f) {
                    if (operation.isBlank()) {
                        khongHopLe()
                    } else {
                        operation += "-"
                    }
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                BtnNumber("1") {
                    operation += "1"
                }
                BtnNumber("2") {
                    operation += "2"
                }
                BtnNumber("3") {
                    operation += "3"
                }
                BtnNumber("+", "#42A610", fontSize = 50f) {
                    if (operation.isBlank()) {
                        khongHopLe()
                    } else {
                        operation += "+"
                    }
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                BtnNumber("+/-", fontSize = 25f)
                BtnNumber("0") {
                    operation += "0"
                }
                BtnNumber(",") {
                    operation += ","
                }
                BtnNumber("=", "#fafafa", "#42A610", fontSize = 50f) {
                    tinhToan()
                }
            }
        }
    }
}

@Composable
fun BtnNumber(
    number: String,
    contentColor: String = "#4e4e51",
    containerColor: String = "#eeeef0",
    fontSize: Float = 30f,
    onClick: () -> Unit = { }
) {
    Button(
        onClick = onClick,
        modifier = Modifier
            .size(80.dp)
            .clip(RoundedCornerShape(50.dp)),
        colors = ButtonDefaults.buttonColors(
            containerColor = Color(containerColor.toColorInt()),
            contentColor = Color(contentColor.toColorInt()),
        )
    ) {
        Text(
            text = number,
            fontSize = fontSize.sp
        )
    }
}