package com.qverkk.touristrentafriend.database.user.picture

import androidx.room.*

@Dao
interface UserProfilePictureDao {

    @Query("SELECT * FROM user_picture LIMIT 1")
    fun getUserProfileImage(): UserPictureDb?

    @Insert
    fun insert(picture: UserPictureDb)

    @Delete
    fun delete(picture: UserPictureDb)

    @Update
    fun update(picture: UserPictureDb)

    @Query("DELETE FROM user_information WHERE 1 = 1")
    fun deleteAll()
}