package com.msudenver.nighttrain.rtd_rider_alerts

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*
import androidx.annotation.VisibleForTesting
import androidx.lifecycle.ViewModelProvider
import com.msudenver.nighttrain.rtd_rider_alerts.ui.ScheduleFragment

class MainActivity : AppCompatActivity() {
    lateinit var theFragment : ScheduleFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val downloadButton: Button = findViewById(R.id.download_button)
        downloadButton.setOnClickListener {
            startService(Intent(this, RiderAlertService::class.java))
            Toast.makeText(this, "Downloads started...", Toast.LENGTH_SHORT).show()
        }
        val fragment = ScheduleFragment()
        addFragment(fragment)
    }

    @VisibleForTesting(otherwise=VisibleForTesting.PRIVATE)
    fun addFragment(fragment : ScheduleFragment) {
        this.theFragment = fragment
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.fragment_container_view, fragment)
        fragmentTransaction.commitNow()
    }
}