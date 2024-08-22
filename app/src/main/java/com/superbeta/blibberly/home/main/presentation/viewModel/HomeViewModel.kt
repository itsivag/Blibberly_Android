package com.superbeta.blibberly.home.main.presentation.viewModel

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.CreationExtras
import com.superbeta.blibberly_auth.utils.userPreferencesDataStore
import com.superbeta.blibberly_chat.data.remote.SocketHandler
import com.superbeta.blibberly_chat.data.remote.SocketHandlerImpl

@Suppress("UNCHECKED_CAST")
class HomeViewModel(val socket: SocketHandler) : ViewModel() {

    init {
        //socket
//        viewModelScope.launch {
//            socket.getSocket()
//        }
    }

    companion object {
        val Factory: ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>, extras: CreationExtras): T {

                val application =
                    extras[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as Application
//                val homeRepo = HomeRepositoryImpl()
                val socket: SocketHandler =
                    SocketHandlerImpl(application.applicationContext.userPreferencesDataStore)

                return HomeViewModel(socket = socket) as T
            }
        }
    }

}