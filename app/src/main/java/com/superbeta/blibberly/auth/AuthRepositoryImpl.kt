package com.superbeta.blibberly.auth

import android.content.Context
import android.util.Log
import androidx.credentials.CredentialManager
import androidx.credentials.GetCredentialRequest
import com.google.android.libraries.identity.googleid.GetGoogleIdOption
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential
import com.google.android.libraries.identity.googleid.GoogleIdTokenParsingException
import com.superbeta.blibberly.user.data.model.UserDataModel
import com.superbeta.blibberly.utils.supabase
import io.github.jan.supabase.exceptions.RestException
import io.github.jan.supabase.gotrue.auth
import io.github.jan.supabase.gotrue.providers.Google
import io.github.jan.supabase.gotrue.providers.builtin.Email
import io.github.jan.supabase.gotrue.providers.builtin.IDToken
import io.github.jan.supabase.gotrue.user.UserInfo
import io.github.jan.supabase.postgrest.from
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import java.security.MessageDigest
import java.util.UUID

class AuthRepositoryImpl : AuthRepository {
    override suspend fun createUser(mEmail: String, mPassword: String) {
        val user = supabase.auth.signUpWith(Email) {
            email = mEmail
            password = mPassword
        }
    }

    override suspend fun signInWithEmail(mEmail: String, mPassword: String) {
        TODO("Not yet implemented")
    }

    override suspend fun signInWithGoogle(
        credentialManager: CredentialManager,
        coroutineScope: CoroutineScope,
        context: Context,
        onSignInSuccess: () -> Unit,
        onUserNotRegistered: () -> Unit
    ) {

        // Generate a nonce and hash it with sha-256
        // Providing a nonce is optional but recommended
        val rawNonce = UUID.randomUUID()
            .toString() // Generate a random String. UUID should be sufficient, but can also be any other random string.
        val bytes = rawNonce.toString().toByteArray()
        val md = MessageDigest.getInstance("SHA-256")
        val digest = md.digest(bytes)
        val hashedNonce =
            digest.fold("") { str, it -> str + "%02x".format(it) } // Hashed nonce to be passed to Google sign-in


        val googleIdOption: GetGoogleIdOption =
            GetGoogleIdOption.Builder().setFilterByAuthorizedAccounts(false)
                .setServerClientId("612314404654-m2vafg7gdboioqqhitb8ctt8pqene9tl.apps.googleusercontent.com")
                .setNonce(hashedNonce) // Provide the nonce if you have one
                .build()

        val request: GetCredentialRequest =
            GetCredentialRequest.Builder().addCredentialOption(googleIdOption).build()

        coroutineScope.launch {
            try {
                val result = credentialManager.getCredential(
                    request = request,
                    context = context,
                )

                val googleIdTokenCredential =
                    GoogleIdTokenCredential.createFrom(result.credential.data)

                val googleIdToken = googleIdTokenCredential.idToken

                supabase.auth.signInWith(IDToken) {
                    idToken = googleIdToken
                    provider = Google
                    nonce = rawNonce
                }

                // Handle successful sign-in
                Log.i("Google Sign In", "Sign In Successful")
                val userData = findIfUserRegistered()

                if (userData == null) {
                    Log.i("Google Sign In", "User not registered")
                    onUserNotRegistered()
                } else {
                    onSignInSuccess()
                }


//            } catch (e: GetCredentialException) {
//                e.printStackTrace()
                // Handle GetCredentialException thrown by `credentialManager.getCredential()`
            } catch (e: GoogleIdTokenParsingException) {
                // Handle GoogleIdTokenParsingException thrown by `GoogleIdTokenCredential.createFrom()`
                e.printStackTrace()
            } catch (e: RestException) {
                // Handle RestException thrown by Supabase
                e.printStackTrace()
            } catch (e: Exception) {
                // Handle unknown exceptions
                e.printStackTrace()
            }
        }
    }

    override suspend fun getUserData(): UserInfo {
        return supabase.auth.retrieveUserForCurrentSession(updateSession = true)
    }

    override suspend fun findIfUserRegistered(): UserDataModel? {
        val user = supabase.from("Users").select {
            filter {
                getUserData().email?.let { eq("email", it) }
            }
        }.decodeSingleOrNull<UserDataModel>()
        return user
    }

    override suspend fun forgotPassword() {
        TODO("Not yet implemented")
    }
}