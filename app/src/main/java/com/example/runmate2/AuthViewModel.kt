package com.example.runmate2

import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth

class AuthViewModel : ViewModel() {
    private val auth : FirebaseAuth = FirebaseAuth.getInstance()
    private val _state = MutableLiveData<AuthState>()
    val state : LiveData<AuthState> = _state

    init{
        checkAuthStatus()
        observeAuthState()
    }
    private fun observeAuthState() {
        _state.value = if (auth.currentUser == null) {
            AuthState.Unauthenticated
        } else {
            AuthState.Authenticated
        }
    }

    fun checkAuthStatus(){
        if(auth.currentUser == null){
            _state.value = AuthState.Unauthenticated
        }
        else{
            _state.value = AuthState.Authenticated
        }
    }

    fun login(Email : String, Password : String){
        if(Email.isBlank() && Password.isBlank()){
            _state.value = AuthState.Error("Email and password cannot be empty")
            return
        }
        _state.value = AuthState.Loading
        auth.signInWithEmailAndPassword(Email, Password)
            .addOnCompleteListener{
                task ->
                if(task.isSuccessful){
                    _state.value = AuthState.Authenticated
                    _state.value = AuthState.Message("Account created successfully")
                }
                else{
                    _state.value = AuthState.Error(task.exception?.message ?: "Unknown error")
                }
            }
    }
    fun SignUp(Email : String, Password : String){
        if(Email.isBlank() && Password.isBlank()){
            _state.value = AuthState.Error("Email and password cannot be empty")
            return
        }
        auth.createUserWithEmailAndPassword(Email, Password)
            .addOnCompleteListener{
                task ->
                if(task.isSuccessful){
                    _state.value = AuthState.Authenticated
                }
                else{
                    _state.value = AuthState.Error(task.exception?.message ?: "Unknown error")
                }
            }
    }
    fun SignOut(){
        auth.signOut()
        _state.value = AuthState.Unauthenticated
    }

    fun resetPassword(Email: String){
        _state.value = AuthState.Loading
        auth.sendPasswordResetEmail(Email)
            .addOnCompleteListener {
                task ->
                if(task.isSuccessful){
                    _state.value = AuthState.Message("Password reset email sent")
                }
                else{
                     _state.value = AuthState.Error(task.exception?.message ?: "Unknown error")
                }
            }
    }

}


sealed class AuthState{
    object Authenticated : AuthState()
    object Unauthenticated : AuthState()
    object Loading : AuthState()
    object AccountCreated : AuthState()
    data class Message(val msg : String) : AuthState()
    data class Error(val message : String) : AuthState()


}