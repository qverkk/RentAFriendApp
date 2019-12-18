package com.qverkk.touristrentafriend.ui.main

import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.qverkk.touristrentafriend.R


class MainActivity : AppCompatActivity() {

    private val PREF_NAME = "theme"
    private var PRIVATE_MODE = 0

    companion object {
        lateinit var context: Context
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        val pref = getSharedPreferences(PREF_NAME, PRIVATE_MODE)
        setTheme(
            if (pref.getBoolean(PREF_NAME, true)) {
                R.style.AppThemeDark
            } else R.style.AppThemeLight
        )

        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)
        MainActivity.context = applicationContext
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
    }
}
