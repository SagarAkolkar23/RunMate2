package com.example.runmate2.App

import android.annotation.SuppressLint
import android.app.Application
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.shape.ZeroCornerSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.layout.ModifierLocalBeyondBoundsLayout
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.intl.Locale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.runmate2.Backend.backview
import com.example.runmate2.R
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.text.format


@SuppressLint("DefaultLocale")
@Composable
fun Congrats(navController: NavController, view : backview) : String {

    var seconds by remember { mutableIntStateOf(0) }
    var isRunning by remember { mutableStateOf(false) }
    LaunchedEffect(key1 = true) {
        isRunning = true
    }
    LaunchedEffect(isRunning) {
        if (isRunning) {
            while (true) {
                delay(1000)
                seconds++
            }
        }
    }



    Column(
        modifier = Modifier
            .background(color = Color(0xFF01111D))
            .fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .padding(top = 54.dp, start = 32.dp)
        ) {

            Text("Shinzou Sasageyo",
                modifier = Modifier
                    .padding(start = 30.dp),
                color = Color.White,
                fontSize = 30.sp,
                style = TextStyle(
                    shadow = Shadow(
                        color = colorResource(R.color.AppLime),
                        offset = androidx.compose.ui.geometry.Offset(4f, 8f),
                        blurRadius = 40f
                    )
                ))
        }
        Spacer(modifier = Modifier.height(30.dp))

        Box(modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight(),
            contentAlignment = Alignment.Center){
            Text(
                formatTime(seconds),
                fontSize = 80.sp,
                color = colorResource(R.color.AppLime),
                style = TextStyle(
                    shadow = Shadow(
                        color = colorResource(R.color.AppLime),
                        offset = androidx.compose.ui.geometry.Offset(4f, 8f),
                        blurRadius = 40f
                    )
                )
            )
        }



        Card(
            shape = RoundedCornerShape(topStart = 48.dp, topEnd = 48.dp, bottomStart = 0.dp, bottomEnd = 0.dp),
            colors = androidx.compose.material3.CardDefaults.cardColors(containerColor = Color.Black),
            modifier = Modifier
                .fillMaxWidth()
                .height(300.dp)
                .padding(start = 10.dp, end = 10.dp)
                .shadow(
                    elevation = 80.dp,
                    spotColor = Color.White,
                    ambientColor = Color.White,
                    shape = RoundedCornerShape(48.dp)
                )
                .background(
                    color = Color(0xFF000000),
                    shape = RoundedCornerShape(size = 48.dp)
                ),
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
            ) {
                Spacer(modifier = Modifier.height(30.dp))
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight()
                        .padding(top = 5.dp, start = 20.dp, end = 20.dp),
                    horizontalArrangement = Arrangement.SpaceBetween)
                {
                    Cards2("Distance", " m")
                    Spacer(modifier = Modifier.width(9.dp))
                    Cards2("Speed", "\nm/s")
                    Spacer(modifier = Modifier.width(9.dp))
                    Cards2("Steps", "0")
                }
                Spacer(modifier = Modifier.height(9.dp))
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight()
                        .padding(top = 5.dp, start = 20.dp, end = 20.dp),
                )
                {
                    Cards2("Calories", "0 KCAL")
                    Spacer(modifier = Modifier.width(9.dp))
                }
                Spacer(modifier = Modifier.height(30.dp))
            }
        }
        Spacer(modifier = Modifier.height(40.dp))
        Box(contentAlignment = Alignment.Center,
            modifier = Modifier
                .fillMaxWidth())
        {
            Row {
                Button(onClick = { isRunning = false
                    navController.navigate("During") },
                    colors = androidx.compose.material3.ButtonDefaults.buttonColors(containerColor = colorResource(R.color.AppLime)),
                    modifier = Modifier
                        .size(155.dp)
                        .clip(shape = RoundedCornerShape(95.dp))){
                    Text(
                        "Stop",
                        fontSize = 31.sp,
                        color = Color.Black,
                        textAlign = TextAlign.Center,
                    )
                }
                Spacer(modifier = Modifier.width(30.dp))
                Button(onClick = { seconds = 0 },
                    colors = androidx.compose.material3.ButtonDefaults.buttonColors(containerColor = colorResource(R.color.AppLime)),
                    modifier = Modifier
                        .size(155.dp)
                        .clip(shape = RoundedCornerShape(95.dp))){
                    Text(
                        "Restart",
                        fontSize = 31.sp,
                        color = Color.Black,
                        textAlign = TextAlign.Center,
                    )
                }
            }
        }
    }
    return formatTime(seconds)
}


@Composable
fun Cards2(text : String, data : String){
    Card(//card color
        shape = RoundedCornerShape(size = 15.dp),
        colors = androidx.compose.material3.CardDefaults.cardColors(containerColor = Color(0xFF1E1E1E)),
        modifier = Modifier
            .width(95.dp)
            .height(115.dp)
            .background(
                color = Color(0xFF1E1E1E),
                shape = RoundedCornerShape(size = 15.dp)
            )
    )
    {
        Text(text,
            fontSize = 15.sp,
            color = Color.White,
            modifier = Modifier
                .align(alignment = androidx.compose.ui.Alignment.CenterHorizontally)
                .padding(top = 17.dp))
        Text(data,
            fontSize = 20.sp,
            color = colorResource(R.color.AppLime),
            fontWeight = FontWeight(600),
            modifier = Modifier
                .padding(top = 17.dp)
                .align(alignment = androidx.compose.ui.Alignment.CenterHorizontally))
    }
}

@SuppressLint("DefaultLocale")
fun formatTime(seconds: Int): String {
    val minutes = seconds / 60
    val remainingSeconds = seconds % 60
    return String.format("%02d:%02d", minutes, remainingSeconds)
}

fun getTime() : String {
    return formatTime(seconds = 0)
}

@Composable
@Preview
fun CongratsPreview() {
    Congrats(navController = NavController(context = LocalContext.current), view = backview())
}