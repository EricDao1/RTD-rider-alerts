package com.msudenver.nighttrain.rtd_rider_alerts

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity(), AdapterView.OnItemSelectedListener {


    private val tag = "alertTag"

    var stationsList : List<String> = ArrayList()
    lateinit var viewModel : TrainScheduleViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val recyclerView: RecyclerView = findViewById(R.id.recycler_view)
        recyclerView.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)

        val stationsSpinner = findViewById<Spinner>(R.id.stations_spinner)

        viewModel = ViewModelProvider(this).get(TrainScheduleViewModel::class.java)
        viewModel.stationNames.observe(this, Observer { stations ->
            val stationsAdapter =
                ArrayAdapter(this, R.layout.support_simple_spinner_dropdown_item, stations)
            stationsList = stations
            stationsSpinner.adapter = stationsAdapter
            stationsSpinner.onItemSelectedListener = this
        })

        viewModel.scheduledTrains.observe(this, Observer { scheduledTrains ->
            val adapter = TrainTimeAdapter(scheduledTrains)
            recyclerView.adapter = adapter
        })

        val downloadButton: Button = findViewById(R.id.download_button)
        downloadButton.setOnClickListener {
            startService(Intent(this,RiderAlertService::class.java))
            Toast.makeText(this, "Downloads started...", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onItemSelected(parent: AdapterView<*>, view: View, pos: Int, id: Long) {
        // An item was selected. You can retrieve the selected item using
        // parent.getItemAtPosition(pos)
        viewModel.setStationNames(stationsList.get(pos))

    }
    override fun onNothingSelected(parent: AdapterView<*>) {
        // Another interface callback
    }
}