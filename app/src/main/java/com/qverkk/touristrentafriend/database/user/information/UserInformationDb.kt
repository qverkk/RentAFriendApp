package com.qverkk.touristrentafriend.database.user.information

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.math.BigDecimal

@Entity(tableName = "user_information")
data class UserInformationDb(
    @PrimaryKey
    @ColumnInfo(name = "informationId")
    val informationId: Int,
    @ColumnInfo(name = "userId")
    var userId: Int,
    @ColumnInfo(name = "description")
    var description: String,
    @ColumnInfo(name = "price")
    var price: BigDecimal,
    @ColumnInfo(name = "country")
    var country: String,
    @ColumnInfo(name = "cityName")
    var cityName: String
)