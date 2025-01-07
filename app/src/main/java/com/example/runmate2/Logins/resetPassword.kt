package com.example.runmate2.Logins

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.focusProperties
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType.Companion.Text
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.runmate2.AuthViewModel
import com.example.runmate2.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun resetPassword(navController: NavController, authViewModel: AuthViewModel){
    var Email by remember { mutableStateOf("") }
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier.fillMaxSize()
    ) {
        Card(modifier = Modifier,
            colors = CardDefaults.cardColors(containerColor = Color.Gray)
        ) {
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.padding(16.dp)
            ) {
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

                )
                val context = LocalContext.current
                Spacer(modifier = Modifier.height(40.dp))
                Button(
                    onClick = {
                        if(Email.isEmpty()){
                            Toast.makeText(context, "Enter Email", Toast.LENGTH_SHORT).show()
                        }
                        else{
                            authViewModel.resetPassword(Email)
                            Toast.makeText(context, "Password reset link is sent on your email", Toast.LENGTH_SHORT).show()
                            navController.navigate("Login")
                        }

                    },
                    colors = androidx.compose.material3.ButtonDefaults.buttonColors(containerColor = colorResource(R.color.AppLime))
                ) {
                    Text("Reset Password",
                        color = Color.Black)

                }
            }
        }
    }
}
