package com.example.assignment3

import android.os.Bundle
import android.util.Log
import android.view.WindowManager
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Magenta
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextIndent
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.updatePadding
import com.example.assignment3.ui.theme.Assignment3Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            Assignment3Theme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = colorScheme.background
                ) {
                    MainScreen()
                }
            }


        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen() {
    var billAmount by remember { mutableStateOf("") }
    var tipAmount by remember { mutableStateOf("") }
    var totalAmount by remember { mutableStateOf("") }
    var tipPercentage by remember { mutableStateOf(PERCS.TEN) }
    val unselected: ButtonColors = ButtonDefaults.buttonColors(
        containerColor = colorScheme.secondary,
        contentColor = colorScheme.onSecondary
    )
    val selected: ButtonColors = ButtonDefaults.buttonColors(
        containerColor = colorScheme.primary,
        contentColor = colorScheme.onPrimary
    )

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 30.dp)
    ) {
        Text(
            text = stringResource(R.string.header),
            fontSize = 36.sp,
            modifier = Modifier.padding(20.dp)

        )
        //Description Text
        Text(
            text = stringResource(id = R.string.description),
            fontSize = 14.sp
        )
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(vertical = 16.dp)
        ) {
            Text(
                text = stringResource(id = R.string.dollarsign),
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(vertical = 6.dp, horizontal = 4.dp),
                fontSize = 24.sp
            )
            TextField(
                value = billAmount,
                onValueChange = { billAmount = it },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
            )

        }
        Text(text = stringResource(id = R.string.select))
        Row(
            modifier = Modifier.padding(vertical = 14.dp),
            horizontalArrangement = Arrangement.spacedBy(6.dp),

            ) {
            Button(
                onClick = { tipPercentage = PERCS.TEN },
                shape = RoundedCornerShape(8.dp),
                colors = if (tipPercentage == PERCS.TEN) selected else unselected
            )
            {
                Text(text = "10%")
            }
            Button(
                onClick = { tipPercentage = PERCS.TWE },
                shape = RoundedCornerShape(8.dp),
                colors = if (tipPercentage == PERCS.TWE) selected else unselected
            ) {
                Text(text = "20%")
            }
            Button(
                onClick = { tipPercentage = PERCS.THR; },
                shape = RoundedCornerShape(8.dp),
                colors = if (tipPercentage == PERCS.THR) selected else unselected
            ) {
                Text(text = "30%")
            }
        }

        Button(onClick = {

            if (billAmount != null && billAmount != "") {
                val (tip, total) = Calculate(tipPercentage, billAmount)
                tipAmount = tip
                totalAmount = total
            } else {
                tipAmount = "Error"
            }
        }, shape = RoundedCornerShape(8.dp)) {
            Text(stringResource(R.string.calc))
        }
        Row(modifier = Modifier.padding(top = 40.dp).imePadding()) {
            Text(
                text = if(tipAmount == "") "" else stringResource(R.string.tip),
                fontSize = 28.sp,
                modifier = Modifier.imePadding()
            )
            Text(
                text = tipAmount,
                fontSize = 28.sp,
                modifier = Modifier.imePadding()

            )
        }
        Row(
            modifier = Modifier.padding(vertical = 10.dp).imePadding()
        ) {
            Text(
                text = stringResource(R.string.tot),
                fontSize = 28.sp
            )
            Text(
                text = totalAmount,
                fontSize = 28.sp
            )
        }
    }
}

enum class PERCS(val multiplier: Double) {
    TEN(0.10), TWE(0.20), THR(0.30)
}

fun Calculate(perc: PERCS, check: String): Pair<String, String> {
    val tip = (check.toDouble() * perc.multiplier)
    val total = tip + check.toDouble()
    return tip.toString() to total.toString()
}

@Preview(showBackground = true)
@Composable
fun ScreenPreview() {
    Assignment3Theme {
        MainScreen()
    }
}