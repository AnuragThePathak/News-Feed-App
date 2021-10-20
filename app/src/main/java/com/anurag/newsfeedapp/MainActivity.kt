package com.anurag.newsfeedapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import com.anurag.newsfeedapp.databinding.ActivityMainBinding
import com.anurag.newsfeedapp.utils.TimeEnum
import dagger.hilt.android.AndroidEntryPoint
import java.util.Calendar

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var appBarConfiguration: AppBarConfiguration

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val c = Calendar.getInstance()
        val timeOfDay = c.get(Calendar.HOUR_OF_DAY)
        val navController =
            (supportFragmentManager.findFragmentById(R.id.nav_fragment_container) as NavHostFragment).findNavController()

        navController.addOnDestinationChangedListener { controller, destination, arguments ->
            if (destination.id == R.id.nav_home_fragment) {
                destination.label = when (timeOfDay) {
                    in 0..11 -> {
                        TimeEnum.MORNING.time
                    }
                    in 12..15 -> {
                        TimeEnum.AFTER_NOON.time
                    }
                    else -> TimeEnum.EVENING.time
                }
            }
        }
        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.nav_home_fragment,
                R.id.nav_notification_fragment,
                R.id.nav_setting_fragment
            )
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
    }
}
