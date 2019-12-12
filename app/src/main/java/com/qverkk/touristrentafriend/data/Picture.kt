package com.qverkk.touristrentafriend.data

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class Picture(
    @SerializedName("pictureId")
    @Expose
    val pictureId: Int,
    @SerializedName("userId")
    @Expose
    val userId: Int,
    @SerializedName("imageBase64")
    @Expose
    val imageBase64: String,
    @SerializedName("profilePicture")
    @Expose
    val profilePicture: Boolean
)