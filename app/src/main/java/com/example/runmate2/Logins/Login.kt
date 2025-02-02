package com.example.runmate2.Logins

import android.annotation.SuppressLint
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusProperties
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.os.registerForAllProfilingResults
import androidx.navigation.NavController
import com.example.runmate2.AuthState
import com.example.runmate2.AuthViewModel
import com.example.runmate2.R
import com.example.runmate2.googleViewModel
import com.google.firebase.auth.FirebaseAuth


@SuppressLint("StateFlowValueCalledInComposition")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Login(navController: NavController, viewModel: AuthViewModel, googleViewModel: googleViewModel) {
    var Email by remember { mutableStateOf("") }
    var Password by remember { mutableStateOf("") }
    val context = LocalContext.current
    val state by viewModel.state.observeAsState()
    val focusRequester1 = remember { FocusRequester() }
    val focusRequester2 = remember { FocusRequester() }
    var reset by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier
            .background(
                brush = Gradientbrush(
                    isHorizontalGradient = true,
                    colors = listOf(Color.Black, colorResource(R.color.LighterGray)),
                    angle = 45f
                )
            )
            .fillMaxSize()
    ) {

        Column(
            modifier = Modifier
                .align(alignment = Alignment.Center)
        ) {
            Text(
            "Welcome!",
            fontWeight = FontWeight.Bold,
            fontSize = 32.sp,
            modifier = Modifier
                .align(alignment = Alignment.CenterHorizontally)
            )
            Spacer(Modifier.padding(20.dp))
            Card(
                colors = CardDefaults.cardColors(containerColor = Color.Gray),
                modifier = Modifier
                    .align(alignment = Alignment.CenterHorizontally)
                    .width(330.dp)
                    .height(350.dp)
            ) {
                Spacer(Modifier.padding(25.dp))
                TextField(
                    value = Email,
                    onValueChange = { newText -> Email = newText.trim() },
                    label = {
                        Text(
                            "Email ID",
                            color = Color.Black,
                            fontSize = 15.sp
                        )
                    },
                    leadingIcon = {
                        Text(
                            "@",
                            fontSize = 20.sp,
                            color = Color.Black
                        )
                    },
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        focusedBorderColor = colorResource(R.color.AppLime),
                        unfocusedBorderColor = colorResource(R.color.AppLime),     // Border color when unfocused
                        errorBorderColor = Color.Red,
                    ),
                    modifier = Modifier
                        .align(alignment = Alignment.CenterHorizontally)
                        .focusRequester(focusRequester1)
                        .focusProperties {
                            next = focusRequester2
                        },
                    keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
                    keyboardActions = KeyboardActions(
                        onNext = { focusRequester2.requestFocus() }
                    )
                )
                Spacer(Modifier.padding(15.dp))
                TextField(
                    value = Password,
                    visualTransformation = PasswordVisualTransformation(),
                    onValueChange = { newText -> Password = newText.trim() },
                    label = {
                        Text(
                            "Password",
                            fontSize = 15.sp,
                            color = Color.Black
                        )
                    },
                    leadingIcon = {
                        Icon(
                            painter = painterResource(R.drawable.password),
                            contentDescription = null,
                            tint = Color.Black
                        )
                    },
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        focusedBorderColor = colorResource(R.color.AppLime),
                        unfocusedBorderColor = colorResource(R.color.AppLime),
                        errorBorderColor = Color.Red,
                    ),
                    modifier = Modifier
                        .align(alignment = Alignment.CenterHorizontally)
                        .width(297.dp)
                        .focusRequester(focusRequester2)
                        .focusProperties {
                            previous = focusRequester1
                        },
                    keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
                    keyboardActions = KeyboardActions(
                        onDone = {
                            viewModel.login(Email, Password)
                            focusRequester2.freeFocus()
                            if (state == AuthState.Authenticated){
                                Toast.makeText(context, "Logged in Successfully", Toast.LENGTH_SHORT).show()
                            }
                        }
                    )
                )
                Spacer(Modifier.padding(5.dp))
                Text(
                    "Forget Password ?",
                    modifier = Modifier
                        .align(alignment = Alignment.End)
                        .padding(end = 25.dp)
                        .clickable {
                            navController.navigate("resetPassword")
                        },
                    fontSize = 15.sp,
                    color = colorResource(R.color.DarkGreen)
                )
                Spacer(Modifier.padding(10.dp))
                Button(
                    onClick = {
                        viewModel.login(Email, Password)
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = colorResource(R.color.AppLime)),
                    modifier = Modifier
                        .align(alignment = Alignment.CenterHorizontally)
                        .width(297.dp)
                        .height(57.dp)
                ) {
                    Text(
                        "Log in",
                        fontSize = 24.sp,
                        modifier = Modifier,
                        color = Color.Black
                    )
                }
            }

            Spacer(Modifier.padding(15.dp))
            Text(
                "Don't have an account ?",
                fontSize = 16.sp,
                color = Color.White,
                modifier = Modifier
                    .align(alignment = Alignment.CenterHorizontally)
            )
            Spacer(Modifier.padding(5.dp))
            Text("Sign UP",
                fontSize = 16.sp,
                color = Color.White,
                textDecoration = TextDecoration.Underline,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .align(alignment = Alignment.CenterHorizontally)
                    .clickable {
                        navController.navigate("SignUp")
                    }
            )
        }

    }
    LaunchedEffect(state) {
        Log.d("Authstate", "$state")
        when(state){
            is AuthState.Authenticated -> {
                navController.navigate("Home") {
                    popUpTo(navController.graph.startDestinationId)
                }
            }
            is AuthState.Loading -> {
                Toast.makeText(context, "Loading", Toast.LENGTH_SHORT).show()
            }
            is AuthState.Error -> {
                Toast.makeText(context, (state as AuthState.Error).message, Toast.LENGTH_SHORT).show()
            }
            else -> {}
        }
    }
}
@Composable
fun Gradientbrush(isHorizontalGradient: Boolean, colors: List<androidx.compose.ui.graphics.Color>, angle: Float): Brush {
    return Brush.linearGradient(
        colors = colors,
        start = Offset(0f, Float.POSITIVE_INFINITY),
        end = Offset(Float.POSITIVE_INFINITY, 0f)
    )
}
