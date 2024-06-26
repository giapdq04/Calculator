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
    val operators = listOf('+', '-', '×', '÷')

    fun khongHopLe(text: String = "Định dạng đã dùng không hợp lệ") {
        Toast.makeText(context, text, Toast.LENGTH_SHORT).show()
    }

    // hàm tính toán
    fun evaluate(expression: String): Double {
        return try {
            // chuyển dấu × và ÷ thành * và /
            var newExpression = expression.replace("×", "*")
                // chuyển dấu % thành /100
                .replace("%", "/100")
                .replace("÷", "/")
                .replace(",", ".")

            // xóa dấu cuối cùng nếu là dấu toán tử
            if (operators.any { newExpression.endsWith(it) }) {
                newExpression = newExpression.dropLast(1)
            }

            // tự động thêm ngoặc đóng nếu ngoặc mở chưa được đóng
            val openBrackets = newExpression.count { it == '(' }
            val closeBrackets = newExpression.count { it == ')' }
            for (i in 0 until (openBrackets - closeBrackets)) {
                newExpression += ")"
            }

            // tính toán
            ExpressionBuilder(newExpression).build().evaluate()
        } catch (e: Exception) {
            throw IllegalArgumentException("Biểu thức không hợp lệ: ${e.message}")
        }
    }

    // sẽ chạy lần đầu khi vào màn hình và khi phép tính bị thay đổi
    LaunchedEffect(key1 = operation) {
        if (!operation.matches("^[0-9+\\-%.×÷\\s()]+$".toRegex()) && !operation.matches("^[0-9×÷+,-]*[0-9%×÷+-]$".toRegex())) {
            result = ""
            return@LaunchedEffect
        }

        // validate không nhập dấu
        if (operators.any { operation.endsWith(it) }) {
            result = ""
            return@LaunchedEffect
        }

        // validate không nhập dấu và %
        if (!operation.contains('%') && !operators.any { it in operation }) {
            return@LaunchedEffect
        }

        // Kiểm tra xem có phép chia cho không hay không
        if (operation.contains("÷0")) {
            return@LaunchedEffect
        }

        // kiểm tra nếu ở cuối phép tính là dấu + "(" thì thông báo lỗi
        if (operators.any { operation.endsWith("$it(") }) {
            return@LaunchedEffect
        }

        val ketQua = evaluate(operation)

        // kết quả
        result = if (ketQua % 1 == 0.0) {
            ketQua.toInt().toString().replace(".", ",")
        } else {
            ketQua.toString().replace(".", ",")
        }
    }

    // khi nhấn dấu "="
    fun bang() {
        if (!operation.matches("^[0-9+\\-%.×÷\\s()]+$".toRegex()) && !operation.matches("^[0-9×÷+,-]*[0-9%×÷+-]$".toRegex())) {
            result = ""
            return
        }

        // validate không nhập dấu
        if (operators.any { operation.endsWith(it) }) {
            result = ""
            return
        }

        // validate không nhập dấu và %
        if (!operation.contains('%') && !operators.any { it in operation }) {
            return
        }

        // Kiểm tra xem có phép chia cho không hay không
        if (operation.contains("÷0")) {
            Toast.makeText(context, "Không thể chia cho 0", Toast.LENGTH_SHORT).show()
            return
        }

        // kiểm tra nếu ở cuối phép tính là dấu + "(" thì thông báo lỗi
        if (operators.any { operation.endsWith("$it(") }) {
            khongHopLe()
            return
        }

        operation = result
        result = ""
    }

    // thêm %
    fun addPercent() {
        if (operation.isBlank() || operation.last().isDigit().not()) {
            khongHopLe()
            return
        }

        operation += "%"
    }

    // thêm số
    fun addNumber(number: String) {
        when {
            operation == "0" -> operation = number
            operation.endsWith("0") && operation.length > 1 && operators.contains(operation[operation.length - 2]) -> operation =
                operation.dropLast(1) + number

            operation.endsWith("%") -> operation += "×$number"
            else -> operation += number
        }
    }

    //thêm dấu
    fun addOperator(operator: String) {
        if (operation.endsWith("(") && (operator == "×" || operator == "÷")) {
            khongHopLe()
            return
        }
        if (operation.isNotBlank() && operators.any { operation.endsWith(it) }) {
            operation = operation.dropLast(1)
        }
        if (operation.isNotBlank()) operation += operator
        else khongHopLe()
    }

    // thêm ngoặc
    fun addBracket() {
        if (operation.count { it == '(' } > operation.count { it == ')' }) {
            operation += ")"
        } else {
            if (operation.isBlank() || operation.last().isDigit().not()) {
                if (operation.endsWith("%")) {
                    operation += "×("
                } else {
                    operation += "("
                }
            } else {
                if (operation.last().isDigit()) {
                    operation += "×("
                } else {
                    operation += "×("
                }
            }
        }
    }

    // xóa phép tính và kết quả
    fun clear() {
        operation = ""
        result = ""
    }

    // thêm dấu phẩy
    fun addComma() {
        if (operation.isBlank()) {
            operation += "0,"
        }
        if (operators.any { operation.endsWith(it) }) {
            operation += "0,"
        }
        if (operation.endsWith(",")) {
            return
        }
        operation += ","
    }

    // thêm dấu âm
    fun addNegative() {
        when {
            operation.isBlank() -> operation += "(-"
            operation == "(-" -> operation = ""
            operation.endsWith("%") -> operation += "×(-"
            operation.contains("-(-") -> operation = operation.replace("-(-", "-")
            operation.contains("+(-") -> operation = operation.replace("+(-", "+")
            operation.contains("×(-") -> operation = operation.replace("×(-", "×")
            operation.contains("÷(-") -> operation = operation.replace("÷(-", "÷")
            operation.last().isDigit() -> {
                operation = if (operation.startsWith("(-")) {
                    operation.drop(2)
                } else {
                    val lastOperatorIndex = operation.indexOfLast { it in operators }
                    if (lastOperatorIndex != -1 && lastOperatorIndex < operation.length - 1) {
                        operation.substring(
                            0,
                            lastOperatorIndex + 1
                        ) + "(-" + operation.substring(lastOperatorIndex + 1)
                    } else {
                        "(-$operation"
                    }
                }
            }

            else -> operation += "(-"
        }
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
            IconButton(
                onClick = {
                    operation = operation.dropLast(1)
                },
                enabled = operation.isNotBlank()
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.green_backspace),
                    contentDescription = null,
                    tint = Color((if (operation.isBlank()) "#848487" else "#42A610").toColorInt())
                )
            }
        }

        HorizontalDivider()

        Column(
            modifier = Modifier.weight(5f)
        ) {
            Spacer(modifier = Modifier.height(30.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                BtnNumber("C", "#d93d34") { clear() }
                BtnNumber("( )", "#42A610") { addBracket() }
                BtnNumber("%", "#42A610") { addPercent() }
                BtnNumber("÷", "#42A610", fontSize = 50f) { addOperator("÷") }
            }

            Spacer(modifier = Modifier.height(10.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                BtnNumber("7") { addNumber("7") }
                BtnNumber("8") { addNumber("8") }
                BtnNumber("9") { addNumber("9") }
                BtnNumber("×", "#42A610", fontSize = 50f) { addOperator("×") }
            }

            Spacer(modifier = Modifier.height(10.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                BtnNumber("4") { addNumber("4") }
                BtnNumber("5") { addNumber("5") }
                BtnNumber("6") { addNumber("6") }
                BtnNumber("-", "#42A610", fontSize = 50f) { addOperator("-") }
            }

            Spacer(modifier = Modifier.height(10.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                BtnNumber("1") { addNumber("1") }
                BtnNumber("2") { addNumber("2") }
                BtnNumber("3") { addNumber("3") }
                BtnNumber("+", "#42A610", fontSize = 50f) { addOperator("+") }
            }

            Spacer(modifier = Modifier.height(10.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                BtnNumber("+/-", fontSize = 25f) { addNegative() }
                BtnNumber("0") { addNumber("0") }
                BtnNumber(",") { addComma() }
                BtnNumber("=", "#fafafa", "#42A610", fontSize = 50f) { bang() }
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