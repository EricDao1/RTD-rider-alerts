package com.msudenver.nighttrain.rtd_rider_alerts

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*
import androidx.annotation.VisibleForTesting
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.msudenver.nighttrain.rtd_rider_alerts.ui.FavoriteStationFragment
import com.msudenver.nighttrain.rtd_rider_alerts.ui.InterfaceViewModel
import com.msudenver.nighttrain.rtd_rider_alerts.ui.ScheduleFragment

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val downloadButton: Button = findViewById(R.id.download_button)
        downloadButton.setOnClickListener {
            startService(Intent(this, RiderAlertService::class.java))
            Toast.makeText(this, "Downloads started...", Toast.LENGTH_SHORT).show()
        }
        val uiViewModel = ViewModelProvider(this).get(InterfaceViewModel::class.java)
        uiViewModel.showStations.observe(this, Observer { showWhat -> addFragment((if (showWhat) FavoriteStationFragment() else ScheduleFragment())) })
    }

    @VisibleForTesting(otherwise=VisibleForTesting.PRIVATE)
    fun addFragment(fragment : Fragment) {
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.fragment_container_view, fragment)
        fragmentTransaction.commitNow()
    }
}