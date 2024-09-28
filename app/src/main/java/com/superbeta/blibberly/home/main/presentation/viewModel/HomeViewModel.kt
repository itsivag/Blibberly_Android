package com.superbeta.blibberly.home.main.presentation.viewModel
//
//import android.app.Application
//import android.util.Log
//import androidx.lifecycle.ViewModel
//import androidx.lifecycle.ViewModelProvider
//import androidx.lifecycle.viewModelScope
//import androidx.lifecycle.viewmodel.CreationExtras
//import com.superbeta.blibberly_auth.utils.userPreferencesDataStore
//import com.superbeta.blibberly_chat.data.local.MessageRoomInstanceProvider
//import com.superbeta.blibberly_chat.data.remote.SocketHandlerImpl
//import com.superbeta.blibberly_chat.domain.MessagesRepoImpl
//import kotlinx.coroutines.launch
//
//@Suppress("UNCHECKED_CAST")
//class HomeViewModel(private val messagesRepoImpl: MessagesRepoImpl) : ViewModel() {
//
//    init {
//        viewModelScope.launch {
//            messagesRepoImpl.connectSocketToBackend()
//            Log.i("Connected to socket", "SUCCESS")
//        }
//    }
//
//    companion object {
//        val Factory: ViewModelProvider.Factory = object : ViewModelProvider.Factory {
//            override fun <T : ViewModel> create(modelClass: Class<T>, extras: CreationExtras): T {
//
//                val application =
//                    extras[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as Application
//                val db = MessageRoomInstanceProvider.getDb(application.applicationContext)
//                val socketHandlerImpl =
//                    SocketHandlerImpl(application.applicationContext.userPreferencesDataStore)
//
//                val messagesRepo = MessagesRepoImpl(db.MessagesDao(), socketHandlerImpl)
//
//                return HomeViewModel(messagesRepo) as T
//            }
//        }
//    }
//
//}