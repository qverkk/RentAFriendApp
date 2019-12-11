package com.qverkk.touristrentafriend.database.user

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.qverkk.touristrentafriend.data.User

@Entity(tableName = "current_users")
data class UserDb(
    @PrimaryKey
    @ColumnInfo(name = "userId")
    var userId: Int,
    @ColumnInfo(name = "firstName")
    var firstName: String,
    @ColumnInfo(name = "lastName")
    var lastName: String,
    @ColumnInfo(name = "birthDate")
    var birthDate: String,
    @ColumnInfo(name = "username")
    var username: String,
    @ColumnInfo(name = "password")
    var password: String
) {
    fun toUser(): User = User(
        userId, firstName, lastName, birthDate, username, password
    )
}


