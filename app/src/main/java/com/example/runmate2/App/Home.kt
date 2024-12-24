package com.example.runmate2.App

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.runmate2.AuthState
import com.example.runmate2.AuthViewModel
import com.example.runmate2.R

@Composable
fun Home(navController: NavController, viewModel: AuthViewModel){

    val state by viewModel.state.observeAsState()
    val context = LocalContext.current
    LaunchedEffect(state) {
        when(state){
            is AuthState.Unauthenticated -> {
                navController.navigate("Login") {
                    popUpTo(navController.graph.startDestinationId)
                }
            }
            is AuthState.Loading -> {
                Toast.makeText(context, "Loading", Toast.LENGTH_SHORT).show()
            }
            else -> {  }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally) {
        Text("RunMate",
            fontSize = 36.sp,
            color = Color.White)
        Spacer(modifier = Modifier.height(16.dp))
        Text("Ready, Set, Run!",
            fontSize = 24.sp,
            color = Color.White)
        Spacer(modifier = Modifier.height(103.dp))
        Button(onClick = {  },
            modifier = Modifier
                .clip(CircleShape)
                .size(250.dp)
                .background(brush = GradientBrush(
                    isHorizontalGradient = true,
                    colors = listOf(colorResource(R.color.AppLime),
                        colorResource(R.color.AppLime).copy(alpha = 0.7f),
                        Color.White),
                    angle = 175f
                )),
            colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent)
                ) {
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Image(painter = painterResource(id = R.drawable.run_icon),
                    contentDescription = null,
                    modifier = Modifier
                        .size(100.dp))
                Text("Start",
                    fontSize = 48.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black,
                    )
            }
        }

        Button(onClick = {
            viewModel.SignOut()
            Log.d("Authstate", "${viewModel.state.value}")
        }) {
            Text("Sign Out")
        }
    }

}

@Composable
fun GradientBrush(isHorizontalGradient: Boolean, colors: List<Color>, angle: Float, modifier: Modifier = Modifier, ) : Brush{
    return Brush.linearGradient(
        colors = colors,
        start = androidx.compose.ui.geometry.Offset(0f, Float.POSITIVE_INFINITY),
        end = androidx.compose.ui.geometry.Offset(Float.POSITIVE_INFINITY, 0f)
    )
}


@Preview
@Composable
fun HomePreview(){
    Home(navController = NavController(context = LocalContext.current), viewModel = AuthViewModel())
}
