package com.superbeta.blibberly_chat.data.remote.socket

sealed class SocketAcknowledgement(val name: String) {
    data object MessageDelivered : SocketAcknowledgement("messageDelivered")
    data object UserOffline : SocketAcknowledgement("userOffline")
}