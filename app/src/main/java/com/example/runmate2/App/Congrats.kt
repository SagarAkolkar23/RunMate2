package com.example.runmate2.App

import android.annotation.SuppressLint
import android.app.Application
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableFloatStateOf
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.ModifierLocalBeyondBoundsLayout
import androidx.compose.ui.modifier.modifierLocalMapOf
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
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
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.runmate2.AuthViewModel
import com.example.runmate2.Backend.DistView
import com.example.runmate2.Backend.StepsView
import com.example.runmate2.Backend.backview
import com.example.runmate2.Backend.distViewFact
import com.example.runmate2.Backend.stepsViewFact
import com.example.runmate2.R
import com.google.accompanist.permissions.rememberMultiplePermissionsState
import kotlinx.coroutines.Job
import android.Manifest
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.PermissionState
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.text.format


@OptIn(ExperimentalPermissionsApi::class)
@SuppressLint("DefaultLocale")

@Composable
fun Congrats(navController: NavController, view : backview,application: Application = LocalContext.current.applicationContext as Application, authViewModel: AuthViewModel) {

    val formattedTime by remember { derivedStateOf { view.formatTime() } }
    val context = LocalContext.current
    val viewModel : StepsView = viewModel(factory = stepsViewFact(context.applicationContext as Application))
    val stepCount by viewModel.stepCount.collectAsState()
    var calories by remember { mutableFloatStateOf(0.0F) }
    val distView: DistView = viewModel(factory = distViewFact(application))
    val distance by distView.distance.observeAsState(0.0)
    val speed by distView.speed.observeAsState(0f)



    LaunchedEffect(stepCount) {
        calories = (stepCount * 0.04).toFloat()
    }

    LaunchedEffect(Unit) {
        view.startTimer()
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
                .padding(top = 35.dp, start = 32.dp)
        ) {
            Spacer(modifier = Modifier.height(10.dp))
            Text(
                "Hello Senshi",
                modifier = Modifier,
                color = Color.White,
                fontSize = 32.sp,
            )
            Spacer(modifier = Modifier.height(5.dp))
            Text(
                "Shinzou Sasageyo",
                modifier = Modifier,
                fontSize = 16.sp,
                color = Color.White
            )
        }
        Spacer(modifier = Modifier.height(10.dp))





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
                .wrapContentHeight()
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

            Box(modifier = Modifier.fillMaxSize()) {
                Image(painter = painterResource(R.drawable.goku2),
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .padding(top = 400.dp))
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                ) {
                    Spacer(modifier = Modifier.height(30.dp))
                    Row(
                        horizontalArrangement = Arrangement.End,
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(
                            "Tatakae!!",
                            modifier = Modifier.padding(end = 140.dp)
                        )
                        Button(
                            onClick = {
                                view.resetTimer()
                                viewModel.resetSteps()
                                distView.resetDistance()
                            },
                            colors = androidx.compose.material3.ButtonDefaults.buttonColors(
                                containerColor = colorResource(R.color.AppLime)
                            ),
                            modifier = Modifier
                                .width(132.dp)
                                .height(32.dp)
                                .padding(end = 30.dp)
                        ) {
                            Text(
                                "Restart",
                                fontSize = 15.sp,
                                color = Color.Black,
                                textAlign = TextAlign.Center,
                            )
                        }
                    }
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .wrapContentHeight(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            formattedTime,
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
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .wrapContentHeight()
                            .padding(top = 5.dp, start = 20.dp, end = 20.dp),
                        horizontalArrangement = Arrangement.SpaceBetween
                    )
                    {
                        Cards2("Distance", "$distance m")
                        Spacer(modifier = Modifier.width(9.dp))
                        Cards2("Speed", "$speed m/s")
                        Spacer(modifier = Modifier.width(9.dp))
                        Cards2("Steps", "$stepCount")
                    }
                    Spacer(modifier = Modifier.height(9.dp))
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .wrapContentHeight()
                            .padding(top = 5.dp, start = 20.dp, end = 20.dp),
                    )
                    {
                        Cards2("Calories", "$calories CAL")
                        Spacer(modifier = Modifier.width(9.dp))
                        Button(
                            onClick = {
                                view.stopTimer()
                                navController.navigate("During/$formattedTime/$stepCount/$calories/$speed/$distance")
                            },
                            colors = androidx.compose.material3.ButtonDefaults.buttonColors(
                                containerColor = Color(0xFFCCFF00)
                            ),
                            modifier = Modifier
                                .height(115.dp)
                        ) {
                            Text(
                                "Stop",
                                fontSize = 31.sp,
                                color = Color.Black,
                                textAlign = TextAlign.Center,
                            )
                        }
                    }
                    Spacer(modifier = Modifier.height(30.dp))


                }
            }
        }
    }
}


@Composable
fun Cards2(text : String, data : String){
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
        Text(text,
            fontSize = 15.sp,
            color = Color.White,
            modifier = Modifier
                .align(alignment = androidx.compose.ui.Alignment.CenterHorizontally)
                .padding(top = 17.dp))
        Text(data,
            fontSize = 20.sp,
            color = Color(0xFFCCFF00),
            fontWeight = FontWeight(600),
            modifier = Modifier
                .padding(top = 17.dp)
                .align(alignment = androidx.compose.ui.Alignment.CenterHorizontally))
    }
}





@Composable
@Preview
fun CongratsPreview() {
    Congrats(navController = NavController(context = LocalContext.current), view = backview(), authViewModel = AuthViewModel())
}

