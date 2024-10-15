package com.superbeta.blibberly_chat.di

import com.superbeta.blibberly_auth.utils.userPreferencesDataStore
import com.superbeta.blibberly_chat.data.local.BlibberlyRoomInstanceProvider
import com.superbeta.blibberly_chat.data.local.MessagesDao
import com.superbeta.blibberly_chat.data.remote.SocketHandler
import com.superbeta.blibberly_chat.data.remote.SocketHandlerImpl
import com.superbeta.blibberly_chat.domain.MessagesRepoImpl
import com.superbeta.blibberly_chat.presentation.viewModels.MessageViewModel
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

    single {
        MessagesRepoImpl(db = get<MessagesDao>(), socketHandler = get<SocketHandler>())
    }

    viewModel {
        MessageViewModel(messagesRepo = get<MessagesRepoImpl>())
    }

}