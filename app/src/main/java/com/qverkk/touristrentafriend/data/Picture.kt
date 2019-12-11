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
    val imageBase64: ByteArray,
    @SerializedName("profilePicture")
    @Expose
    val profilePicture: Boolean
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Picture

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