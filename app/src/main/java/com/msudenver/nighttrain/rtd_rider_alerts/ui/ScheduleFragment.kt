package com.msudenver.nighttrain.rtd_rider_alerts.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.msudenver.nighttrain.rtd_rider_alerts.R
import com.msudenver.nighttrain.rtd_rider_alerts.TrainScheduleViewModel
import com.msudenver.nighttrain.rtd_rider_alerts.TrainTimeAdapter

class ScheduleFragment() : Fragment() {
    private var stationsList : List<String> = ArrayList()
    private lateinit var viewModel : TrainScheduleViewModel
    private lateinit var stationsSpinner : Spinner
    private val logTag = "scheduleFragment"
    private var skipFirstSelction = true

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) : View {
        val view = inflater.inflate(R.layout.schedule_fragment, container, false)
        val recyclerView: RecyclerView = view.findViewById(R.id.recycler_view)
        recyclerView.layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)

        stationsSpinner = view.findViewById<Spinner>(R.id.stations_spinner)
        skipFirstSelction = true

        stationsSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, pos: Int, id: Long) {
                // An item was selected. You can retrieve the selected item using
                // parent.getItemAtPosition(pos)
                Log.v(logTag, "Item selected: " + pos)
                if(skipFirstSelction) {
                    skipFirstSelction = false
                } else {
                    if (stationsList.isNotEmpty()) {
                        viewModel.setStationNames(stationsList[pos])
                    }
                }
            }
            override fun onNothingSelected(parent: AdapterView<*>) {
                // Another interface callback
            }
        }

        viewModel = ViewModelProvider(requireActivity()).get(TrainScheduleViewModel::class.java)
        viewModel.stationNames.observe(requireActivity(), Observer { stations ->
            val stationsAdapter =
                ArrayAdapter(context, R.layout.support_simple_spinner_dropdown_item, stations)
            stationsList = stations
            stationsSpinner.adapter = stationsAdapter
        })

        viewModel.stationSelected.observe(requireActivity(), Observer {selectedStation ->
            Log.v(logTag, selectedStation)
            if(stationsList.isNotEmpty() && (stationsSpinner.selectedItem.toString() != (selectedStation))) {
                stationsSpinner.setSelection(stationsList.indexOf(selectedStation))
            }
        })

        viewModel.scheduledTrains.observe(requireActivity(), Observer { scheduledTrains ->
            val adapter = TrainTimeAdapter(scheduledTrains)
            recyclerView.adapter = adapter
        })
        return view
    }
}