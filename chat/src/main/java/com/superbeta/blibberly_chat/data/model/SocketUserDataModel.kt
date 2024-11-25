package com.superbeta.blibberly_chat.data.model

import com.google.gson.annotations.SerializedName

data class SocketUserDataModelItem(
    @SerializedName("userName")
    val userName: String?,
    @SerializedName("email")
    val email: String?,
)

//class SocketUserDataModel : ArrayList<SocketUserDataModelItem>()