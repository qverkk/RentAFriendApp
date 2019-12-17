package com.qverkk.touristrentafriend.data

import com.google.gson.annotations.Expose

data class MessageDTO(
    @Expose
    val userFirstName: String,
    @Expose
    val userLastName: String,
    @Expose
    val chatId: String,
    @Expose
    val fromUser: String,
    @Expose
    val textBody: String
)
