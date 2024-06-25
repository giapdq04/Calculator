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
import androidx.compose.runtime.LaunchedEffect
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
import net.objecthunter.exp4j.ExpressionBuilder

@Composable
@Preview(showBackground = true)
fun MainScreen() {
    var operation by rememberSaveable { mutableStateOf("") } // phép tính
    var result by rememberSaveable { mutableStateOf("") } // kết quả
    val context = LocalContext.current

    fun khongHopLe(text: String = "Định dạng đã dùng không hợp lệ") {
        Toast.makeText(context, text, Toast.LENGTH_SHORT).show()
    }

    // hàm tính toán
    fun evaluate(expression: String): Double {
        return try {
            // chuyển dấu × và ÷ thành * và /
            var newExpression = expression.replace("×", "*")
            newExpression = newExpression.replace("÷", "/")

            // tính toán
            ExpressionBuilder(newExpression).build().evaluate()
        } catch (e: Exception) {
            throw IllegalArgumentException("Biểu thức không hợp lệ: ${e.message}")
        }
    }

    // sẽ chạy khi phép tính bị thay đổi
    LaunchedEffect(key1 = operation) {
        val operators = listOf('+', '-', '×', '÷')

        if (!operation.matches("^[0-9×÷+-]*[0-9]$".toRegex())) {
            result = ""
            return@LaunchedEffect
        }

        // validate không nhập dấu
        if (!operators.any { it in operation }) {
            result = ""
            return@LaunchedEffect
        }

        val ketQua = evaluate(operation)

        // kết quả
        result = if (ketQua % 1 == 0.0) {
            ketQua.toInt().toString()
        } else {
            ketQua.toString()
        }
    }

    // khi nhấn dấu "="
    fun bang() {
        if (!operation.matches("^[0-9×÷+-]*[0-9]$".toRegex())) {
            khongHopLe()
            return
        }

        val operators = listOf('+', '-', '×', '÷')
        // validate không nhập dấu
        if (!operators.any { it in operation }) {
            return
        }

        operation = result
        result = ""
    }

    fun addPercent(){
        if (operation.isBlank() || operation.last().isDigit().not()) {
            khongHopLe()
            return
        }

        operation += "%"
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
                BtnNumber("%", "#42A610") { addPercent() }
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
                    bang()
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