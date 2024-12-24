package com.example.runmate2.Logins


import android.util.Log
import android.widget.ProgressBar
import android.widget.Toast
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
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
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.runmate2.AuthState
import com.example.runmate2.AuthViewModel
import com.example.runmate2.R


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SignUp(navController: NavController, viewModel: AuthViewModel){


    var Email by remember { mutableStateOf(" ") }
    var Password by remember { mutableStateOf(" ") }
    var ConfPassword by remember { mutableStateOf(" ") }
    var UserName by remember { mutableStateOf(" ") }
    val state by viewModel.state.observeAsState()
    val context  = LocalContext.current

    LaunchedEffect(state){
        when(state){
            is  AuthState.Authenticated -> {
                navController.popBackStack()
                navController.navigate("Login")
            }
            is AuthState.Error -> {
                Toast.makeText(context, (state as AuthState.Error).message, Toast.LENGTH_SHORT).show()
            }
            else -> {

            }
        }
    }


    val focusRequester1 = remember { FocusRequester() }
    val focusRequester2 = remember { FocusRequester() }
    val focusRequester3 = remember { FocusRequester() }
    val focusRequester4 = remember { FocusRequester() }

    Column(modifier = Modifier
        .fillMaxSize()
        .background(
            brush = Gradientbrush(
                isHorizontalGradient = true,
                colors = listOf(Color.Black, colorResource(R.color.LighterGray)),
                angle = 45f
            )
        )

    ) {


        Spacer(Modifier.padding(50.dp))
        Text("Welcome",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .align(alignment = Alignment.CenterHorizontally))
        Spacer(Modifier.padding(15.dp))
        Card(
            colors = CardDefaults.cardColors(containerColor = Color.Gray),
            modifier = Modifier
                .align(alignment = Alignment.CenterHorizontally)
                .width(330.dp)
                .height(520.dp)
        ) {
            Spacer(Modifier.padding(15.dp))
            TextField(
                value = UserName,
                onValueChange = { newText -> UserName = newText },
                label = {
                    Text(
                        "User Name",
                        color = Color.Black,
                        fontSize = 15.sp
                    )
                },
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.Person,
                        contentDescription = "User Icon",
                        tint = Color.Black,

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
            Spacer(Modifier.padding(8.dp))
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
                    disabledBorderColor = colorResource(R.color.AppLime)
                ),
                modifier = Modifier
                    .align(alignment = Alignment.CenterHorizontally)
                    .focusRequester(focusRequester2)
                    .focusProperties {
                        previous = focusRequester1
                        next = focusRequester3 },
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
                keyboardActions = KeyboardActions(
                    onNext = { focusRequester3.requestFocus() }
                )
            )


            Spacer(Modifier.padding(8.dp))
            TextField(value = Password,
                onValueChange = {newText -> Password = newText.trim()},
                label = { Text("Password",
                    fontSize = 15.sp,
                    color = Color.Black)},
                leadingIcon = { Icon(
                    imageVector = Icons.Default.Lock,
                    contentDescription = "Password icon",
                    tint = Color.Black
                )},
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    focusedBorderColor = colorResource(R.color.AppLime),
                    unfocusedBorderColor = colorResource(R.color.AppLime),
                    errorBorderColor = Color.Red,
                ),
                modifier = Modifier
                    .align(alignment = Alignment.CenterHorizontally)
                    .width(297.dp)
                    .focusRequester(focusRequester3)
                    .focusProperties {
                        previous = focusRequester2
                        next = focusRequester4
                    },
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
                keyboardActions = KeyboardActions(
                    onNext = { focusRequester4.requestFocus() }
                )
            )

            Spacer(Modifier.padding(8.dp))
            TextField(value = ConfPassword,
                onValueChange = {newText -> ConfPassword = newText.trim( )},
                label = { Text("Confirm Password",
                    fontSize = 15.sp,
                    color = Color.Black)},
                leadingIcon = { Icon(
                    imageVector = Icons.Default.Lock,
                    contentDescription = "Password icon",
                    tint = Color.Black
                )},
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    focusedBorderColor = colorResource(R.color.AppLime),
                    unfocusedBorderColor = colorResource(R.color.AppLime),
                    errorBorderColor = Color.Red,
                ),
                modifier = Modifier
                    .align(alignment = Alignment.CenterHorizontally)
                    .width(297.dp)
                    .focusRequester(focusRequester4)
                    .focusProperties {
                        previous = focusRequester3
                    },
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
                keyboardActions = KeyboardActions(
                    onDone = { viewModel.SignUp(Email, ConfPassword)
                        Log.d("Authstate", "$state")
                        if(state == AuthState.Authenticated){
                            Toast.makeText(context, "Signed up successfully", Toast.LENGTH_SHORT).show()
                        }
                    }
                ))
            Spacer(Modifier.padding(10.dp))
            Button(onClick = {
                viewModel.SignUp(Email, ConfPassword)
                Log.d("Authstate", "$state")
                if(state == AuthState.Authenticated){
                    Toast.makeText(context, "Signed up successfully", Toast.LENGTH_SHORT).show()
                }
            },
                colors = ButtonDefaults.buttonColors(containerColor = colorResource(R.color.AppLime)),
                modifier = Modifier
                    .align(alignment = Alignment.CenterHorizontally)
                    .width(297.dp)
                    .height(57.dp)) {
                Text("Create Account",
                    fontSize = 24.sp,
                    modifier = Modifier,
                    color = Color.Black)
            }
            Spacer(Modifier.padding(6.dp))
            Text("Or",
                fontSize = 20.sp,
                modifier = Modifier
                    .align(alignment = Alignment.CenterHorizontally))
            Spacer(Modifier.padding(6.dp))
            Button(onClick = { },
                colors = ButtonDefaults.buttonColors(containerColor = Color.White),
                modifier = Modifier
                    .align(alignment = Alignment.CenterHorizontally)
                    .width(297.dp)
                    .height(57.dp)) {
                Row {
                    Icon(painter = painterResource(R.drawable.googlelogo),
                        contentDescription = "Google Logo",
                        modifier = Modifier
                            .size(35.dp))
                    Text(
                        "Continue with google",
                        fontSize = 17.sp,
                        modifier = Modifier
                            .align(alignment = Alignment.CenterVertically),
                        color = Color.Black)
                }
            }
            Spacer(Modifier.padding(5.dp))
        }
        Spacer(Modifier.padding(15.dp))
        Text("Already have an account ?",
            fontSize = 16.sp,
            color = Color.White,
            modifier = Modifier
                .align(alignment = Alignment.CenterHorizontally))
        Spacer(Modifier.padding(5.dp))
        Text("Log in",
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            textDecoration = androidx.compose.ui.text.style.TextDecoration.Underline,
            color = Color.White,
            modifier = Modifier
                .align(alignment = Alignment.CenterHorizontally)
                .clickable {
                    navController.navigate("Login")
                })
    }
}