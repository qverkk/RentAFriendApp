package com.qverkk.touristrentafriend.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.qverkk.touristrentafriend.data.Converters
import com.qverkk.touristrentafriend.database.user.UserDao
import com.qverkk.touristrentafriend.database.user.UserDb
import com.qverkk.touristrentafriend.database.user.information.UserInformationDao
import com.qverkk.touristrentafriend.database.user.information.UserInformationDb
import com.qverkk.touristrentafriend.database.user.picture.UserPictureDb
import com.qverkk.touristrentafriend.database.user.picture.UserProfilePictureDao

@Database(
    entities = [UserDb::class, UserInformationDb::class, UserPictureDb::class],
    version = 9,
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {

    abstract fun userDao(): UserDao
    abstract fun userInformationDao(): UserInformationDao
    abstract fun userProfilePicture(): UserProfilePictureDao

    companion object {
        @Volatile
        private var _instance: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            val tempInstance =
                _instance
            if (tempInstance != null) {
                return tempInstance
            }
            synchronized(this) {
                val instance =
                    Room.databaseBuilder(
                        context.applicationContext,
                        AppDatabase::class.java,
                        "rent-a-friend"
                    ).fallbackToDestructiveMigration().build()
                _instance = instance
                return instance
            }
        }
    }
}