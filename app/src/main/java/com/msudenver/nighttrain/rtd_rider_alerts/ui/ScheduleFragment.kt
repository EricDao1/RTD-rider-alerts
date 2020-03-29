package com.msudenver.nighttrain.rtd_rider_alerts.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.msudenver.nighttrain.rtd_rider_alerts.R
import com.msudenver.nighttrain.rtd_rider_alerts.TrainScheduleViewModel
import com.msudenver.nighttrain.rtd_rider_alerts.TrainTimeAdapter

class ScheduleFragment(
    private val viewModel : TrainScheduleViewModel
) : Fragment() {
    private var stationsList : List<String> = ArrayList()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) : View {
        val view = inflater.inflate(R.layout.schedule_fragment, container, false)
        val recyclerView: RecyclerView = view.findViewById(R.id.recycler_view)
        recyclerView.layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)

        val stationsSpinner = view.findViewById<Spinner>(R.id.stations_spinner)
        stationsSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, pos: Int, id: Long) {
                // An item was selected. You can retrieve the selected item using
                // parent.getItemAtPosition(pos)
                if(stationsList.isNotEmpty()) {
                    viewModel.setStationNames(stationsList[pos])
                }

            }
            override fun onNothingSelected(parent: AdapterView<*>) {
                // Another interface callback
            }
        }

        viewModel.stationNames.observe(requireActivity(), Observer { stations ->
            val stationsAdapter =
                ArrayAdapter(context, R.layout.support_simple_spinner_dropdown_item, stations)
            stationsList = stations
            stationsSpinner.adapter = stationsAdapter
        })

        viewModel.scheduledTrains.observe(requireActivity(), Observer { scheduledTrains ->
            val adapter = TrainTimeAdapter(scheduledTrains)
            recyclerView.adapter = adapter
        })
        return view
    }
}