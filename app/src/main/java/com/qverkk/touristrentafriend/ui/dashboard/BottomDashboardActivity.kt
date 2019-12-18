package com.qverkk.touristrentafriend.ui.dashboard

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.qverkk.touristrentafriend.R

class BottomDashboardActivity : AppCompatActivity() {

    private val PREF_NAME = "theme"
    private var PRIVATE_MODE = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        val pref = getSharedPreferences(PREF_NAME, PRIVATE_MODE)
        setTheme(
            if (pref.getBoolean(PREF_NAME, true)) {
                R.style.AppThemeDark
            } else R.style.AppThemeLight
        )
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_bottom_dashboard)
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)

        val navView: BottomNavigationView = findViewById(R.id.nav_view)

        val navController = findNavController(R.id.nav_host_fragment)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_current_user,
                R.id.navigation_home,
                R.id.navigation_dashboard,
                R.id.navigation_message,
                R.id.navigation_settings
            )
        )

        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
    }
}
