package com.example.runmate2.App

import android.app.Application
import android.util.Log
import androidx.compose.foundation.Image
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
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.shape.ZeroCornerSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MenuItemColors
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.focus.focusModifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.ModifierLocalBeyondBoundsLayout
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.runmate2.AuthViewModel
import com.example.runmate2.Backend.backview
import com.example.runmate2.R


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun During(navController: NavController, view : backview, time: String, steps : String, calories : String, authViewModel: AuthViewModel, speed : String, distance : String) {
    var expanded by remember { mutableStateOf(false) }
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
                    } },
                        modifier = Modifier.size(55.dp)) {
                        Icon(imageVector = Icons.Default.Home,
                            contentDescription = null,
                            tint = colorResource(R.color.AppLime),
                            modifier = Modifier
                                .size(40.dp))
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = colorResource(R.color.DarkGray)),
                actions = {
                    IconButton(
                        onClick = { expanded = !expanded },
                        modifier = Modifier
                            .clip(CircleShape)
                            .size(40.dp),
                        colors = IconButtonDefaults.iconButtonColors(colorResource(R.color.AppLime))
                    ) {
                        Icon(
                            imageVector = Icons.Filled.MoreVert,
                            contentDescription = null,
                            tint = Color.Black,
                            modifier = Modifier
                                .size(32.dp)
                                .background(color = colorResource(R.color.AppLime))
                        )
                    }
                    DropdownMenu(
                        expanded = expanded,
                        onDismissRequest = { expanded = false },
                        modifier = Modifier.wrapContentWidth(),

                    ){
                        DropdownMenuItem(
                            text = { Text("Hello , Senshi",
                                fontSize = 20.sp,
                                color = colorResource(R.color.warrior)) },
                            onClick = {  })
                        DropdownMenuItem(
                            {
                                Row (modifier = Modifier
                                    .fillMaxWidth()){
                                    Icon(painter = painterResource(R.drawable.baseline_logout_24),
                                        contentDescription = null,)
                                    Spacer(modifier = Modifier.width(10.dp))
                                    Text("Logout",
                                        fontSize = 20.sp,
                                        color = colorResource(R.color.redlight))
                                }
                            },
                            onClick = {
                                authViewModel.SignOut()
                                navController.navigate("Login"){
                                    popUpTo(navController.graph.startDestinationId){
                                        inclusive = true
                                    }
                                    launchSingleTop = true
                                }
                            }
                        )
                    }
                }
            )
        },
        content = { innerPadding ->
            Box(modifier = Modifier.fillMaxSize()) {
                Image(painter = painterResource(R.drawable.luffy),
                    contentDescription = null,
                    modifier = Modifier.fillMaxSize())
                Column(
                    modifier = Modifier
                        .background(color = Color(0x80000000))
                        .fillMaxSize()
                        .padding(innerPadding)
                ) {
                    Column(
                        modifier = Modifier
                            .padding(10.dp)
                            .fillMaxWidth()
                            .wrapContentHeight(),
                    ) {
                        Row {
                            Text(
                                text = "WELL DONE!",
                                fontSize = 32.sp,
                                color = Color.White,
                                fontWeight = FontWeight.Bold,
                                style = TextStyle(
                                    shadow = Shadow(
                                        color = colorResource(R.color.AppLime),
                                        offset = androidx.compose.ui.geometry.Offset(4f, 8f),
                                        blurRadius = 40f
                                    )
                                ),
                                modifier = Modifier
                            )
                        }
                        Spacer(modifier = Modifier.height(15.dp))

                        Spacer(modifier = Modifier.height(5.dp))
                        Text(
                            "Summary:",
                            fontSize = 20.sp,
                            color = Color.White
                        )
                        Spacer(modifier = Modifier.height(20.dp))
                        Row(modifier = Modifier.padding(start = 9.dp)) {
                            Cards("Time", time)
                            Spacer(modifier = Modifier.width(9.dp))
                            Cards("Distance", "$distance m")
                            Spacer(modifier = Modifier.width(9.dp))
                            Cards("Top Speed", "$speed m/s")

                        }
                        Spacer(modifier = Modifier.height(9.dp))
                        Row(modifier = Modifier.padding(start = 59.dp)) {
                            Cards("Calories", "$calories CAL")
                            Spacer(modifier = Modifier.width(9.dp))
                            Cards("Total Steps", steps)

                        }
                        Spacer(modifier = Modifier.height(15.dp))
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
            .width(100.dp)
            .height(115.dp)
            .background(
                color = Color(0xFF1E1E1E),
                shape = RoundedCornerShape(size = 15.dp)
            )
    )
    {
        Column (
            modifier = Modifier
                .align(alignment = Alignment.CenterHorizontally),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text,
                fontSize = 15.sp,
                color = Color.White,
                modifier = Modifier
                    .padding(top = 17.dp)
            )
            Text(
                data,
                fontSize = 20.sp,
                color =  Color(0xFFCCFF00),
                fontWeight = FontWeight(600),
                modifier = Modifier
                    .padding(top = 17.dp)
            )
        }
    }
}

@Composable
@Preview
fun DuringPreview() {
    During(navController = NavController(context = LocalContext.current), view = backview(), "", "", "", authViewModel = AuthViewModel(), "", "")
}