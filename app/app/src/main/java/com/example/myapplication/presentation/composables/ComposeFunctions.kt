package com.example.myapplication.presentation.composables

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myapplication.R

@Composable
fun DefaultText(
    modifier: Modifier,
    text: String,
    color: Color,
    fontSize: TextUnit = 14.sp,
    textAlign: TextAlign = TextAlign.Center,
    fontFamily: FontFamily = FontFamily.Default,
    fontWeight: FontWeight = FontWeight.Medium
) {
    Text(
        modifier = modifier,
        text = text,
        color = color,
        fontSize = fontSize,
        textAlign = textAlign,
        fontFamily = fontFamily,
        fontWeight = fontWeight
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DefaultTextField(
    modifier: Modifier,
    text: String,
    color: Color,
    value: String,
    onValueChange: (String) -> Unit,
    isError: Boolean = false
) {
    TextField (
        value = value,
        onValueChange = onValueChange,
        placeholder = { DefaultText(modifier = modifier, text = text, color = color, textAlign = TextAlign.Left) },
        singleLine = true,
        isError = isError
    )
}

@Composable
fun DefaultButton(
    action: () -> Unit,
    modifier: Modifier,
    buttonText: String,
    textColor: Color,
    buttonColor: Color,
    fontSize: TextUnit = 18.sp,
) {
    Button(
        onClick = { action() },
        modifier = modifier,
        colors = ButtonDefaults.buttonColors(buttonColor),
    ) {
        DefaultText(modifier = Modifier.fillMaxSize(), text = buttonText, color = textColor, fontSize = fontSize)
    }
}

@Composable
fun LoadingScreen(){
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator(
            modifier = Modifier.width(100.dp),
            color = Color.White
        )
    }
}

