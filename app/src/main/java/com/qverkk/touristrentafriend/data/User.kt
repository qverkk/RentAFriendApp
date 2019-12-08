package com.qverkk.touristrentafriend.data

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import com.qverkk.touristrentafriend.database.user.UserDb
import java.util.*

data class User(
    @SerializedName("userId")
    @Expose
    var userId: Int,
    @SerializedName("firstName")
    @Expose
    var firstName: String,
    @SerializedName("lastName")
    @Expose
    var lastName: String,
    @SerializedName("birthDate")
    @Expose
    var birthDate: Date,
    @SerializedName("username")
    @Expose
    var username: String,
    @SerializedName("password")
    @Expose
    var password: String
) {

    fun toUserDb(): UserDb =
        UserDb(
            userId,
            firstName,
            lastName,
            birthDate,
            username,
            password
        )
}
