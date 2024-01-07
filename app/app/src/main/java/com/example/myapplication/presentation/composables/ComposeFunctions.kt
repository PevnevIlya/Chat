package com.example.myapplication.presentation.composables

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
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
        isError = isError,
        modifier = Modifier
            .clip(RoundedCornerShape(24.dp))
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

@Composable
fun CardWithImageAndText(imageUrl: String, text1: String, text2: String, action: () -> Unit) {
    Card(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth()
            .clickable { action() }
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            AsyncImage(
                model = imageUrl,
                contentDescription = "Photo",
                modifier = Modifier
                    .size(150.dp)
                    .clip(CircleShape),
                contentScale = ContentScale.Crop
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(text = text1, style = TextStyle(fontSize = 18.sp))
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = text2, style = TextStyle(fontSize = 14.sp, color = Color.Gray))
        }
    }
}

