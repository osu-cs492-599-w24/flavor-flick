package edu.oregonstate.cs492.assignment4.ui

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.MenuProvider
import androidx.lifecycle.Lifecycle
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import com.google.android.material.appbar.MaterialToolbar
import edu.oregonstate.cs492.assignment4.R

/*
 * Often, we'll have sensitive values associated with our code, like API keys, that we'll want to
 * keep out of our git repo, so random GitHub users with permission to view our repo can't see them.
 * The OpenWeather API key is like this.  We can keep our API key out of source control using the
 * technique described below.  Note that values configured in this way can still be seen in the
 * app bundle installed on the user's device, so this isn't a safe way to store values that need
 * to be kept secret at all costs.  This will only keep them off of GitHub.
 *
 * The Gradle scripts for this app are set up to read your API key from a special Gradle file
 * that lives *outside* your project directory.  This file called `gradle.properties`, and it
 * should live in your GRADLE_USER_HOME directory (this will usually be `$HOME/.gradle/` in
 * MacOS/Linux and `$USER_HOME/.gradle/` in Windows).  To store your API key in `gradle.properties`,
 * make sure that file exists in the correct location, and then add the following line:
 *
 *   OPENWEATHER_API_KEY="<put_your_own_OpenWeather_API_key_here>"
 *
 * If your API key is stored in that way, the Gradle build for this app will grab it and write it
 * into the string resources for the app with the resource name "openweather_api_key".  You'll be
 * able to access your key in the app's Kotlin code the same way you'd access any other string
 * resource, e.g. `getString(R.string.openweather_api_key)`.  This is what's done in the code below
 * when the OpenWeather API key is needed.
 *
 * If you don't mind putting your OpenWeather API key on GitHub, then feel free to just hard-code
 * it in the app. 🤷‍
 */

class MainActivity : AppCompatActivity() {
    private lateinit var appBarConfig: AppBarConfiguration

    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val navHostFragment = supportFragmentManager.findFragmentById(
            R.id.nav_host_fragment
        ) as NavHostFragment
        val navController = navHostFragment.navController
        appBarConfig = AppBarConfiguration(navController.graph)

        val appBar: MaterialToolbar = findViewById(R.id.top_app_bar)
        setSupportActionBar(appBar)
        setupActionBarWithNavController(navController, appBarConfig)

        /*
         * Set up a MenuProvider to provide and handle app bar actions for all screens under this
         * activity.
         */
        addMenuProvider(
            object : MenuProvider {
                override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                    menuInflater.inflate(R.menu.main_menu, menu)
                }

                override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                    return when (menuItem.itemId) {
                        R.id.action_settings -> {
                            findNavController(R.id.nav_host_fragment).navigate(R.id.settings)
                            true
                        }
                        else -> false
                    }
                }

            },
            this,
            Lifecycle.State.STARTED
        )
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment)
        return navController.navigateUp(appBarConfig) || super.onSupportNavigateUp()
    }
}