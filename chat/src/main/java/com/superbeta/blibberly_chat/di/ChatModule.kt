package com.superbeta.blibberly_chat.di

import com.superbeta.blibberly_auth.utils.userPreferencesDataStore
import com.superbeta.blibberly_chat.data.local.BlibberlyRoomInstanceProvider
import com.superbeta.blibberly_chat.data.local.MessagesDao
import com.superbeta.blibberly_chat.data.remote.socket.SocketHandler
import com.superbeta.blibberly_chat.data.remote.socket.SocketHandlerImpl
import com.superbeta.blibberly_chat.data.remote.supabase.ChatRemoteService
import com.superbeta.blibberly_chat.data.remote.supabase.ChatRemoteServiceImpl
import com.superbeta.blibberly_chat.domain.MessagesRepo
import com.superbeta.blibberly_chat.domain.MessagesRepoImpl
import com.superbeta.blibberly_chat.notification.NotificationRepo
import com.superbeta.blibberly_chat.notification.NotificationRepoImpl
import com.superbeta.blibberly_chat.presentation.viewModels.MessageViewModel
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.createSupabaseClient
import io.github.jan.supabase.gotrue.Auth
import io.github.jan.supabase.postgrest.Postgrest
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val chatModule = module {
    single<SocketHandler> {
        SocketHandlerImpl(androidContext().userPreferencesDataStore)
    }

    single<MessagesDao> {
        BlibberlyRoomInstanceProvider.getDatabase(androidContext()).MessagesDao()
    }

    single<SupabaseClient> {
        createSupabaseClient(
            supabaseUrl = "https://dxyahfscoumjwjuwlgje.supabase.co",
            supabaseKey = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJzdXBhYmFzZSIsInJlZiI6ImR4eWFoZnNjb3VtandqdXdsZ2plIiwicm9sZSI6ImFub24iLCJpYXQiOjE3MTY5MTUzOTMsImV4cCI6MjAzMjQ5MTM5M30.DqthAS5M1CSeBFQf87TAxv57eMCalxxiPAbRp_XQ8AE"
        ) {
            install(Postgrest)
            install(Auth)
//            install(Storage)
        }

    }
    single<ChatRemoteService> {
        ChatRemoteServiceImpl(supabase = get())
    }

    single<NotificationRepo> {
        NotificationRepoImpl()
    }

    single<MessagesRepo> {
        MessagesRepoImpl(
            db = get<MessagesDao>(),
            socketHandler = get<SocketHandler>(),
            chatRemoteService = get(),
            notificationService = get()
        )
    }

    viewModel {
        MessageViewModel(messagesRepo = get<MessagesRepo>())
    }

}