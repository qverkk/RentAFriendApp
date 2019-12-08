package com.qverkk.touristrentafriend.database.user

import androidx.room.*

@Dao
interface UserDao {

    @Query("SELECT * FROM current_users LIMIT 1")
    fun getUser(): UserDb?

    @Insert
    fun insertUser(user: UserDb)

    @Delete
    fun delete(user: UserDb)

    @Query("DELETE FROM current_users WHERE 1 = 1")
    fun deleteAll()

    @Update
    fun update(user: UserDb)
}