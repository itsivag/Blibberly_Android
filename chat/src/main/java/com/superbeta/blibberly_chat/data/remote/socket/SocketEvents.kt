package com.itsivag.kotlin_chat_server.socket

sealed class SocketEvents(val eventName: String) {
    data object ConnectionEvent : SocketEvents("connection")
    data object AllConnectedUsersEvent : SocketEvents("users")
    data object NewUserConnectedEvent : SocketEvents("newUserConnected")
    data object MessageEvent : SocketEvents("message")
    data object UsersDisconnectedEvent : SocketEvents("disconnect")
    data object CurrUserDisconnectedEvent : SocketEvents("userDisconnected")
}