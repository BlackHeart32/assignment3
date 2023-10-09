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
    //Setting each of my mutable state variables to hold the mutable information of my application
    var billAmount by remember { mutableStateOf("") }
    var tipAmount by remember { mutableStateOf("") }
    var totalAmount by remember { mutableStateOf("") }
    //A mutable state variable to hold the current tip percentage to be applied
    var tipPercentage by remember { mutableStateOf(PERCS.TEN) }
    //The following variables are color schemes to apply to the selected and unselected buttons.
    val unselected: ButtonColors = ButtonDefaults.buttonColors(
        containerColor = colorScheme.secondary,
        contentColor = colorScheme.onSecondary
    )
    val selected: ButtonColors = ButtonDefaults.buttonColors(
        containerColor = colorScheme.primary,
        contentColor = colorScheme.onPrimary
    )
    //Column to host most (if not all) of our application components.
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
        //Avoided using hardcoded text and setup proper alignment and padding
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
        //A row for the tip percentage options, each button will set the tip percentage to their represented options.
        //The button that holds the current selected percentage option will be highlighted to stand out from the others.
        //The text in the buttons will tell the user what percentages such button will apply.
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
                //Destructuring the values calculated and assigning the to the mutable states holding tip and total
                val (tip, total) = Calculate(tipPercentage, billAmount)
                tipAmount = tip
                totalAmount = total

        }, shape = RoundedCornerShape(8.dp)) {
            Text(stringResource(R.string.calc))
        }
        Row(modifier = Modifier.padding(top = 40.dp)) {
            Text(
                //If there is no tip calculated yet, Display nothing in the place holder and tip amount
                text = if(tipAmount == "" || tipAmount == "Error") "" else stringResource(R.string.tip),
                fontSize = 28.sp
            )
            Text(

                text = tipAmount,
                fontSize = 28.sp,
            )
        }
        Row(
            modifier = Modifier.padding(vertical = 10.dp)
        ) {
            Text(
                //Checking if the returned value from calculate is error, if so, display nothing for total
                if(totalAmount == "" || totalAmount == "Error") "" else stringResource(R.string.tot),
                fontSize = 28.sp
            )
            Text(
                //Since only one error message is needed, nothing will be displayed in total when an error happens
                if(totalAmount == "" || totalAmount == "Error") "" else totalAmount,
                fontSize = 28.sp
            )
        }
    }
}

enum class PERCS(val multiplier: Double) {
    TEN(0.10), TWE(0.20), THR(0.30)
}
//My calculate function will calculate the tip and total and return a pair of strings holding their values
fun Calculate(perc: PERCS, check: String): Pair<String, String> {

    try {
        //Ensuring a max of 2 decimal places
        val tip = Math.round((check.toDouble() * perc.multiplier) * 100.0) /100.0
        val total = Math.round((tip + check.toDouble()) * 100.0)/100.0
        return ("$$tip").toString() to ("$$total").toString()
    }
    //This exception handler will catch if the user didn't enter an amount yet, and if the entered input is mixed with letters
    catch(e: Exception){
        return "Error" to "Error"
    }
}

@Preview(showBackground = true)
@Composable
fun ScreenPreview() {
    Assignment3Theme {
        MainScreen()
    }
}