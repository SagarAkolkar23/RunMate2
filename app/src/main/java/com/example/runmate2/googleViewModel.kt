package com.example.runmate2

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.credentials.CredentialManager
import androidx.credentials.CustomCredential
import androidx.credentials.GetCredentialRequest
import androidx.credentials.exceptions.GetCredentialCancellationException
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import com.google.android.libraries.identity.googleid.GetGoogleIdOption
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.firestore.auth.User
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import java.security.MessageDigest
import java.util.UUID

class googleViewModel(application: Application) : AndroidViewModel(application) {

    val user = MutableLiveData<userData>( null)
    private lateinit var firebaseAuth: FirebaseAuth
    lateinit var googleSignInClient: GoogleSignInClient
    private lateinit var googleSignInLauncher: ActivityResultLauncher<Intent>

    fun initGoogleSignIn(context: Context, launcher: ActivityResultLauncher<Intent>){
        firebaseAuth = FirebaseAuth.getInstance()
        googleSignInLauncher = launcher
        val googleSignInOptions = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(context.getString(R.string.web_client_id))
            .requestEmail()
            .build()
        googleSignInClient = GoogleSignIn.getClient(context, googleSignInOptions)
    }

    fun signWithGoogle() : Intent{
        return googleSignInClient.signInIntent
    }

    fun handleSignInResult(task: com.google.android.gms.tasks.Task<GoogleSignInAccount>, context: Context, navController: androidx.navigation.NavController) {
        try {
            val account = task.getResult(ApiException::class.java)
            account?.let {
                firebaseAuthWithGoogle(it.idToken!!, context, navController)
            }
        } catch (e: ApiException) {
            Log.w("GoogleSignIn", "Sign in failed", e)
        }
    }

    private fun firebaseAuthWithGoogle(idToken:String, context: Context, navController: NavController){
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        FirebaseAuth.getInstance().signInWithCredential(credential)
            .addOnCompleteListener { task ->
                if(task.isSuccessful) {
                    Toast.makeText(context, "Sign in successful", Toast.LENGTH_SHORT).show()
                    navController.navigate("Home")
                }
                else{
                    Toast.makeText(context, "Authentication Failed.", Toast.LENGTH_SHORT).show()
                }
            }
    }

    suspend fun googleSignIn(context: Context) : Flow<Result<AuthResult>> {
        val firebaseAuth = FirebaseAuth.getInstance()


        return callbackFlow {
            try{
                val credentialManager : CredentialManager = CredentialManager.create(context)
                val ranNonce : String = UUID.randomUUID().toString()
                val bytes : ByteArray = ranNonce.toByteArray()
                val md : MessageDigest = MessageDigest.getInstance("SHA-256")
                val digest : ByteArray = md.digest(bytes)
                val encodedHash : String = digest.fold("") { str, it -> str + "%02x".format(it) }
                val googleIdOption : GetGoogleIdOption = GetGoogleIdOption.Builder()
                    .setFilterByAuthorizedAccounts(false)
                    .setNonce(encodedHash)
                    .setServerClientId(context.getString(R.string.web_client_id))
                    .setAutoSelectEnabled(true)
                    .build()
                val request : GetCredentialRequest = GetCredentialRequest.Builder()
                    .addCredentialOption(googleIdOption)
                    .build()

                val result = credentialManager.getCredential(context, request)
                val credential = result.credential

                if(credential is CustomCredential && credential.type == GoogleIdTokenCredential.TYPE_GOOGLE_ID_TOKEN_CREDENTIAL){
                    val googleIdTokenCredential = GoogleIdTokenCredential.createFrom(credential.data)
                    val authCredential = GoogleAuthProvider.getCredential(googleIdTokenCredential.idToken, null)
                    val authResult = firebaseAuth.signInWithCredential(authCredential).await()

                    trySend(Result.success(authResult))
                }
                else{
                    throw RuntimeException("Invalid Credential")
                }
            }
            catch (e : GetCredentialCancellationException){
                trySend(Result.failure(Exception("Sign-in was cancelled")))
            }
            catch (e : Exception){
                trySend(Result.failure(e))
            }
            awaitClose {  }
        }
    }
    fun handleGoogleSignIn(context: Context, navController: NavController){
        viewModelScope.launch {
            googleSignIn(context).collect(){ result ->
                result.fold(
                    onSuccess = { authResult ->

                        val currentUser = authResult.user
                        if(currentUser != null){
                            user.value = currentUser.displayName?.let {
                                currentUser.email?.let { it1 ->
                                    userData(currentUser.uid,
                                        it, currentUser.photoUrl.toString(), it1
                                    )
                                }
                            }
                            Toast.makeText(context, "Account created", Toast.LENGTH_SHORT).show()
                            navController.navigate("Home")
                        }
                        else{
                            Toast.makeText(context, "Sign in failed", Toast.LENGTH_SHORT).show()
                        }
                    },
                    onFailure = { e ->
                        Toast.makeText(context, e.message, Toast.LENGTH_SHORT).show()
                    }
                )
            }
        }
    }
}