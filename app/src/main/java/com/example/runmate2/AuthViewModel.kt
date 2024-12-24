package com.example.runmate2

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class AuthViewModel : ViewModel() {
    private val auth : FirebaseAuth = FirebaseAuth.getInstance()
    private val _state : MutableStateFlow<AuthState> =MutableStateFlow(AuthState.Unauthenticated)
    val state : MutableLiveData<AuthState> = MutableLiveData()

    init{
        observeAuthState()
        checkAuthStatus()
    }
    private fun observeAuthState() {
        viewModelScope.launch {
            _state.collect { authState ->
                state.postValue(authState)
            }
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
                    _state.value = AuthState.Message("Logged in successfully")
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
}


sealed class AuthState{
    object Authenticated : AuthState()
    object Unauthenticated : AuthState()
    object Loading : AuthState()
    object AccountCreated : AuthState()
    data class Message(val msg : String) : AuthState()
    data class Error(val message : String) : AuthState()


}