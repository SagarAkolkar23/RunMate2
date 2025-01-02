package com.example.runmate2.App

import android.app.Application
import androidx.compose.foundation.background
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
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ModifierLocalBeyondBoundsLayout
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.runmate2.Backend.BackView
import com.example.runmate2.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun During(navController: NavController, view: BackView) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "Runmate",
                    fontSize = 25.sp,
                    color = colorResource(R.color.AppLime),
                    modifier = Modifier
                        .padding(start = 10.dp)) },
                navigationIcon = {
                    IconButton(onClick = { navController.navigate("Home"){
                        popUpTo("Home"){
                            inclusive = true
                        }
                        launchSingleTop = true
                    }},
                        modifier = Modifier.size(55.dp)) {
                        Icon(imageVector = Icons.Default.Home,
                            contentDescription = null,
                            tint = colorResource(R.color.AppLime),
                            modifier = Modifier
                                .size(55.dp))
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color(0xFF01111D)),
                actions = {
                    IconButton(
                        onClick = { },
                        modifier = Modifier
                            .clip(CircleShape)
                            .size(55.dp),
                        colors = IconButtonDefaults.iconButtonColors(colorResource(R.color.AppLime))
                    ) {
                        Icon(
                            imageVector = Icons.Filled.Person,
                            contentDescription = null,
                            tint = Color.Black,
                            modifier = Modifier.size(32.dp)
                        )
                    }
                }
            )
        },
        content = { innerPadding ->
            Column(
                modifier = Modifier
                    .background(color = Color(0xFF01111D))
                    .fillMaxSize()
                    .padding(innerPadding)
            ) {
                Column(
                    modifier = Modifier
                        .padding(10.dp)
                        .fillMaxWidth()
                        .wrapContentHeight()
                ) {

                    Text(
                        text = "Congrats!",
                        fontSize = 62.sp,
                        color = Color.White,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier
                            .padding(top = 5.dp, start = 12.dp)
                    )
                }
                Spacer(modifier = Modifier.height(25.dp))
                Card(
                    shape = RoundedCornerShape(
                        topStart = 48.dp,
                        topEnd = 48.dp,
                        bottomStart = 0.dp,
                        bottomEnd = 0.dp
                    ),
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
                                .padding(top = 5.dp, start = 20.dp, end = 20.dp)
                        )
                        {
                            Cards("Distance", "${view.totalDistance} km")
                            Spacer(modifier = Modifier.width(9.dp))
                            Cards("Speed", "${view.currentSpeed}\nkm/h")
                            Spacer(modifier = Modifier.width(9.dp))
                            Cards("Steps", "0")
                        }
                        Spacer(modifier = Modifier.height(9.dp))
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .wrapContentHeight()
                                .padding(top = 5.dp, start = 20.dp, end = 20.dp),
                        )
                        {
                            Cards("Calories", "0 KCAL")
                            Spacer(modifier = Modifier.width(9.dp))
                            Cards("Time", "00:00")
                            Spacer(modifier = Modifier.width(9.dp))
                            Cards("Avg Speed", "0.00\nkm/h")
                        }
                    }
                }


            }
        }
    )
}


@Composable
fun Cards(text : String, data : String){
    Card(//card color
        shape = RoundedCornerShape(size = 15.dp),
        colors = androidx.compose.material3.CardDefaults.cardColors(containerColor = Color(0xFF1E1E1E)),
        modifier = Modifier
        .width(95.dp)
        .height(115.dp)
        .background(color = Color(0xFF1E1E1E),
            shape = RoundedCornerShape(size = 15.dp))
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

@Composable
@Preview
fun DuringPreview() {
    During(navController = NavController(context = LocalContext.current),  view = BackView(LocalContext.current.applicationContext as Application))
}