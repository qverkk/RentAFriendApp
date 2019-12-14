package com.qverkk.touristrentafriend.data

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class UserOrder(
    @SerializedName("orderId")
    @Expose
    var orderId: String,
    @SerializedName("userRentingId")
    @Expose
    var userRentingId: Int,
    @SerializedName("userRentedId")
    @Expose
    var userRentedId: Int,
    @SerializedName("chatName")
    @Expose
    var chatName: String
)