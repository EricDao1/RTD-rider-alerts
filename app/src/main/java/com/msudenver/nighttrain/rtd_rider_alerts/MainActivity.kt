package com.msudenver.nighttrain.rtd_rider_alerts

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {

    private val tag = "alertTag"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val recyclerView : RecyclerView = findViewById(R.id.recycler_view)
        recyclerView.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)

        val adapter = TrainTimeAdapter(arrayOf<String>("home", "work", "school"))
        recyclerView.adapter = adapter

        val stationsSpinner = findViewById<Spinner>(R.id.stations_spinner)

        val viewModel = ViewModelProvider(this).get(TrainScheduleViewModel::class.java)
        viewModel.stationNames.observe(this, Observer { stations ->
            val stationsAdapter = ArrayAdapter(this, R.layout.support_simple_spinner_dropdown_item, stations)
            stationsSpinner.adapter = stationsAdapter
        })

        //startService(Intent(this,RiderAlertService::class.java))
    }
}
