package com.msudenver.nighttrain.rtd_rider_alerts

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*
import androidx.lifecycle.ViewModelProvider
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

        val scheduleFragment = ScheduleFragment()
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.fragment_container_view, scheduleFragment)
        fragmentTransaction.commitNow()
    }
}