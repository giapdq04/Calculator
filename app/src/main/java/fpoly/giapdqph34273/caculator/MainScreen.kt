package fpoly.giapdqph34273.caculator

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
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.graphics.toColorInt

@Composable
@Preview(showBackground = true)
fun MainScreen() {
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
                text = "123+123",
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
                text = "123+123",
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
            IconButton(onClick = { }) {
                Icon(
                    painter = painterResource(id = R.drawable.green_backspace),
                    contentDescription = null,
                    tint = Color("#42A610".toColorInt())
                )
            }
        }

        Divider()

        Column(
            modifier = Modifier
                .weight(5f)
        ) {
            Spacer(modifier = Modifier.height(30.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                BtnNumber("C","#d93d34")
                BtnNumber("( )","#42A610")
                BtnNumber("%","#42A610")
                BtnNumber("÷","#42A610", fontSize = 50f)
            }

            Spacer(modifier = Modifier.height(10.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                BtnNumber("7")
                BtnNumber("8")
                BtnNumber("9")
                BtnNumber("×","#42A610", fontSize = 50f)
            }

            Spacer(modifier = Modifier.height(10.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                BtnNumber("4")
                BtnNumber("5")
                BtnNumber("6")
                BtnNumber("-","#42A610", fontSize = 50f)
            }

            Spacer(modifier = Modifier.height(10.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                BtnNumber("1")
                BtnNumber("2")
                BtnNumber("3")
                BtnNumber("+","#42A610", fontSize = 50f)
            }

            Spacer(modifier = Modifier.height(10.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                BtnNumber("+/-", fontSize = 25f)
                BtnNumber("0")
                BtnNumber(",")
                BtnNumber("=","#fafafa","#42A610", fontSize = 50f)
            }
        }
    }
}

@Composable
fun BtnNumber(
    number: String,
    contentColor: String = "#4e4e51",
    containerColor: String = "#eeeef0",
    fontSize: Float = 30f
) {
    Button(
        onClick = { },
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