package com.qverkk.touristrentafriend.ui.login.helpers

import android.os.AsyncTask
import com.qverkk.touristrentafriend.database.AppDatabase
import com.qverkk.touristrentafriend.database.user.UserDb
import com.qverkk.touristrentafriend.database.user.information.UserInformationDb
import com.qverkk.touristrentafriend.ui.main.MainActivity

class UserAsyncTask :
    AsyncTask<Void, Void, UserDb>() {
    override fun doInBackground(vararg p0: Void?): UserDb {
        val database = AppDatabase.getDatabase(MainActivity.context)
        return database.userDao().getUser()!!
    }
}

class InformationAsyncTask :
    AsyncTask<Void, Void, UserInformationDb>() {
    override fun doInBackground(vararg p0: Void?): UserInformationDb {
        val database = AppDatabase.getDatabase(MainActivity.context)
        return database.userInformationDao().getUserInformation()!!
    }
}
