package com.example.assignment3

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
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
import com.example.assignment3.ui.theme.Assignment3Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Assignment3Theme {
                
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
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
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 30.dp)
    ){
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
        ){
           Text(
               text= stringResource(id = R.string.dollarsign),
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
            horizontalArrangement = Arrangement.spacedBy(6.dp)

        ){
            Button(onClick = {}, shape = RoundedCornerShape(8.dp)){
                Text(text="10%")
            }
            Button(onClick = {}, shape = RoundedCornerShape(8.dp) ){
                Text(text="20%")
            }
            Button(onClick = {}, shape = RoundedCornerShape(8.dp) ){
                Text(text="30%")
            }
        }
        Button(onClick = {}, shape = RoundedCornerShape(8.dp)){
            Text(stringResource(R.string.calc))
        }

    }
    Column(
        verticalArrangement = Arrangement.Bottom,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = Modifier.padding(vertical = 10.dp)
        ) {
            Text(
                text = stringResource(R.string.tip),
                fontSize = 28.sp
            )
            Text(
                text = "3.32",
                fontSize = 28.sp
            )
        }
        Row(
            modifier = Modifier.padding(vertical = 10.dp)
        ) {
            Text(
                text = stringResource(R.string.tot),
                fontSize = 28.sp
            )
            Text(
                text = "3.32",
                fontSize = 28.sp
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ScreenPreview() {
    Assignment3Theme {
        MainScreen()
    }
}