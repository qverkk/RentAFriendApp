package com.qverkk.touristrentafriend.ui.dashboard.ui.messaging.chat

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class UserMessage(
    @SerializedName("fromUser")
    @Expose
    val fromUser: Int,
    @SerializedName("textBody")
    @Expose
    val textBody: String
)
