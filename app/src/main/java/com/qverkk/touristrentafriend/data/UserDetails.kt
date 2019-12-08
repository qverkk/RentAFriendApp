package com.qverkk.touristrentafriend.data

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class UserDetails(
    @SerializedName("user")
    @Expose
    val user: User,
    @SerializedName("information")
    @Expose
    val information: UserInformation
)