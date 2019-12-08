package com.qverkk.touristrentafriend.data

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import com.qverkk.touristrentafriend.database.user.information.UserInformationDb
import java.math.BigDecimal

data class UserInformation(
    @SerializedName("informationId")
    @Expose
    val informationId: Int,
    @SerializedName("userId")
    @Expose
    var userId: Int,
    @SerializedName("description")
    @Expose
    var description: String,
    @SerializedName("price")
    @Expose
    var price: BigDecimal,
    @SerializedName("country")
    @Expose
    var country: String,
    @SerializedName("cityName")
    @Expose
    var cityName: String
) {
    fun toInformationDB(): UserInformationDb =
        UserInformationDb(informationId, userId, description, price, country, cityName)
}
