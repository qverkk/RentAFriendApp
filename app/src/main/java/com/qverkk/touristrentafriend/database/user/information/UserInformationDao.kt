package com.qverkk.touristrentafriend.database.user.information

import androidx.room.*

@Dao
interface UserInformationDao {

    @Query("SELECT * FROM user_information LIMIT 1")
    fun getUserInformation(): UserInformationDb?

    @Insert
    fun insert(userInformationDb: UserInformationDb)

    @Delete
    fun delete(userInformationDb: UserInformationDb)

    @Update
    fun update(userInformationDb: UserInformationDb)

    @Query("DELETE FROM user_information WHERE 1 = 1")
    fun deleteAll()
}