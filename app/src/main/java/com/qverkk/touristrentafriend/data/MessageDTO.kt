package com.qverkk.touristrentafriend.data

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class MessageDTO(
    @SerializedName("messageId")
    @Expose
    val messageId: Int,
    @SerializedName("userFirstName")
    @Expose
    val userFirstName: String,
    @SerializedName("userLastName")
    @Expose
    val userLastName: String,
    @SerializedName("chatId")
    @Expose
    val chatId: String,
    @SerializedName("fromUser")
    @Expose
    val fromUser: Int,
    @SerializedName("toUser")
    @Expose
    val toUser: Int,
    @SerializedName("textBody")
    @Expose
    val textBody: String
)
