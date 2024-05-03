package com.superbeta.blibberly

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.googlefonts.GoogleFont
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.google.android.libraries.identity.googleid.GetGoogleIdOption
import com.superbeta.blibberly.navigation.BlibberlyNavHost
import com.superbeta.blibberly.ui.theme.BlibberlyTheme
import com.superbeta.blibberly.utils.SupabaseInstance
import io.getstream.chat.android.client.ChatClient
import io.getstream.chat.android.client.logger.ChatLogLevel
import io.getstream.chat.android.models.UploadAttachmentsNetworkType
import io.getstream.chat.android.models.User
import io.getstream.chat.android.offline.plugin.factory.StreamOfflinePluginFactory
import io.getstream.chat.android.state.plugin.config.StatePluginConfig
import io.getstream.chat.android.state.plugin.factory.StreamStatePluginFactory
import io.github.jan.supabase.gotrue.auth

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //supabase
//        val supabase = SupabaseInstance.supabase
//        val auth = supabase.auth

        //google fonts


        //creds manager
//        val googleIdOption: GetGoogleIdOption = GetGoogleIdOption.Builder()
//            .setFilterByAuthorizedAccounts(true)
//            .setServerClientId("612314404654-n01da2n4jg19ett4eu7k3pgvvvb337va.apps.googleusercontent.com")
//            .build()

        //stream
        val apiKey = "y2st43tkq85k"
        val token =
            "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VyX2lkIjoiMSJ9.NACLyT7Yejq55wB-54JXh61efspeZ__jDVL2lUSDW2M"

        val offlinePluginFactory = StreamOfflinePluginFactory(applicationContext)
        val statePluginFactory = StreamStatePluginFactory(
            config = StatePluginConfig(
                backgroundSyncEnabled = true,
                userPresence = true
            ),
            appContext = applicationContext
        )

        val client = ChatClient.Builder(apiKey, applicationContext)
            .logLevel(ChatLogLevel.ALL)
            .withPlugins(offlinePluginFactory, statePluginFactory)
            .build()

        val user = User(
            id = "1",
            name = "Paranoid Android",
            image = "https://bit.ly/2TIt8NR",
        )

        client.connectUser(
            user = user,
            token = token, // or client.devToken(userId); if auth is disabled for your app
        ).enqueue { result ->
            if (result.isSuccess) {
                // Handle success
                Log.i("itsivag", "logged in")
            } else {
                // Handler error
                Log.i("itsivag", "error signing in")
            }
        }

        var isTopBarVisible by mutableStateOf(false)
        setContent {
            BlibberlyTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val navController = rememberNavController()
                    val navBackStackEntry by navController.currentBackStackEntryAsState()
                    when (navBackStackEntry?.destination?.route) {
                        "home" -> {
                            isTopBarVisible = true
                        }

                        else -> {
                            isTopBarVisible = false
                        }

                    }

                    Scaffold(topBar = {
                        if (isTopBarVisible)
                            CenterAlignedTopAppBar(
                                title = { Text(text = "Paging Test") },
                                navigationIcon = {
                                    IconButton(
                                        onClick = { /*TODO*/ }) {
                                        Icon(
                                            imageVector = Icons.Default.Person,
                                            contentDescription = "Profile"
                                        )
                                    }
                                }, actions = {
                                    IconButton(
                                        onClick = { /*TODO*/ }) {
                                        Icon(
                                            imageVector = Icons.Default.Settings,
                                            contentDescription = "Filter"
                                        )
                                    }
                                })
                    }) {
                        BlibberlyNavHost(
                            navController = navController,
                            modifier = Modifier.padding(it)
                        )
                    }
                }
            }
        }

    }
}