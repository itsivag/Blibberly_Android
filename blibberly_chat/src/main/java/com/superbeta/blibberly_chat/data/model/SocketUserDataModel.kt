package com.superbeta.blibberly_chat.data.model

import com.google.gson.annotations.SerializedName

data class SocketUserDataModelItem(
    @SerializedName("userID")
    val userID: String?,
    @SerializedName("username")
    val username: String?
)

class SocketUserDataModel : ArrayList<SocketUserDataModelItem>()