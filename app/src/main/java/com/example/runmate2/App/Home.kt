package com.example.runmate2.App

import android.Manifest
import android.app.Application
import android.location.Location
import android.util.Log
import android.widget.ImageButton
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.LinkAnnotation
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.runmate2.AuthState
import com.example.runmate2.AuthViewModel
import com.example.runmate2.R
import com.example.runmate2.App.Congrats
import com.example.runmate2.Backend.backview
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberMultiplePermissionsState

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun Home(navController: NavController, viewModel: AuthViewModel, view : backview){

    val state by viewModel.state.observeAsState()
    val context = LocalContext.current
    val permissionState = rememberMultiplePermissionsState(
        permissions = listOf(
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACTIVITY_RECOGNITION
        )
    )


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

    Box(modifier = Modifier,
        contentAlignment = Alignment.TopEnd) {
        Image(painter = painterResource(R.drawable.nar),
            contentDescription = null,
            contentScale = ContentScale.Fit,
            modifier = Modifier.fillMaxSize())
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0x80000000)),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        )
        {
            permissionState.permissions.forEach { perm ->
                when(perm.permission){
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACTIVITY_RECOGNITION -> {
                        when {
                            perm.hasPermission -> {}
                            perm.shouldShowRationale -> {}
                            !perm.hasPermission && !perm.shouldShowRationale -> {}
                        }
                    }
                }

            }
            Spacer(modifier = Modifier.height(550.dp))
            Text(
                "RunMate",
                fontSize = 36.sp,
                color = Color.White
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                "Ready, Set, Run!",
                fontSize = 24.sp,
                color = Color.White
            )
            Spacer(modifier = Modifier.height(20.dp))

                Button(
                    onClick = {
                        permissionState.launchMultiplePermissionRequest()
                        if (permissionState.allPermissionsGranted) {
                            navController.navigate("Congrats")
                        }
                        else{
                            Toast.makeText(context, "Location Permission Denied", Toast.LENGTH_SHORT).show()
                            permissionState.launchMultiplePermissionRequest()
                        }
                    },
                    modifier = Modifier
                        .clip(RoundedCornerShape(50))
                        .width(300.dp)
                        .height(90.dp)
                        .background(
                            brush = GradientBrush(
                                isHorizontalGradient = true,
                                colors = listOf(
                                    colorResource(R.color.AppLime),
                                    colorResource(R.color.AppLime).copy(alpha = 0.7f),
                                    Color.White
                                ),
                                angle = 175f
                            )
                        ),
                    colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent)
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center) {
                        Image(
                            painter = painterResource(id = R.drawable.run_icon),
                            contentDescription = null,
                            modifier = Modifier
                                .size(60.dp)
                        )
                        Spacer(modifier = Modifier.width(20.dp))
                        Text(
                            "Start",
                            fontSize = 48.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.Black,
                        )
                    }
                }
        }
    }
}

@Composable
fun GradientBrush(isHorizontalGradient: Boolean, colors: List<Color>, angle: Float, modifier: Modifier = Modifier, ) : Brush{
    return Brush.linearGradient(
        colors = colors,
        start = Offset(0f, Float.POSITIVE_INFINITY),
        end = Offset(Float.POSITIVE_INFINITY, 0f)
    )
}


@Preview
@Composable
fun HomePreview(){
    Home(navController = NavController(context = LocalContext.current), viewModel = AuthViewModel(), view = backview())
}
