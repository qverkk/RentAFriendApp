package com.qverkk.touristrentafriend.database.user.picture

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user_picture")
data class UserPictureDb(
    @PrimaryKey
    @ColumnInfo(name = "pictureId")
    val pictureId: Int,
    @ColumnInfo(name = "userId")
    val userId: Int,
    @ColumnInfo(name = "imageBase64")
    var imageBase64: ByteArray,
    @ColumnInfo(name = "profilePicture")
    var profilePicture: Boolean
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as UserPictureDb

        if (pictureId != other.pictureId) return false
        if (userId != other.userId) return false
        if (!imageBase64.contentEquals(other.imageBase64)) return false
        if (profilePicture != other.profilePicture) return false

        return true
    }

    override fun hashCode(): Int {
        var result = pictureId
        result = 31 * result + userId
        result = 31 * result + imageBase64.contentHashCode()
        result = 31 * result + profilePicture.hashCode()
        return result
    }
}