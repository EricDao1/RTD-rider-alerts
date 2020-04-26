package com.msudenver.nighttrain.rtd_rider_alerts.ui

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.annotation.VisibleForTesting
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.msudenver.nighttrain.rtd_rider_alerts.R
import com.msudenver.nighttrain.rtd_rider_alerts.db.ScheduledTrain
import kotlinx.android.synthetic.main.schedule_fragment.*

class ScheduleFragment : Fragment() {
    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    var stationsList : List<String> = ArrayList()
    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    var viewModel : TrainScheduleViewModel? = null
    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    var stationsSpinner : Spinner? = null
    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    var recyclerView: RecyclerView? = null
    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    var testableContext : Context? = null
    private val logTag = "scheduleFragment"

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) : View {
        val view = inflater.inflate(R.layout.schedule_fragment, container, false)
        recyclerView = view.findViewById<RecyclerView>(R.id.recycler_view)
        initializeRecyclerView()

        stationsSpinner = view.findViewById<Spinner>(R.id.stations_spinner)
        initializeStationSpinner()

        val addFavoriteStationButton : ImageButton = view.findViewById<ImageButton>(R.id.add_station)
        addFavoriteStationButton.setOnClickListener {
            changeToAddFavoriteStations()
        }
        addFavoriteStationButton.setImageResource(R.drawable.baseline_note_add_black_18dp)

        testableContext = context
        viewModel = ViewModelProvider(requireActivity()).get(TrainScheduleViewModel::class.java)
        initializeViewModel()

        val refreshButton = view.findViewById<Button>(R.id.refresh_button)
        refreshButton.setOnClickListener {
            refreshTrains()
        }
        return view
    }

    @VisibleForTesting (otherwise = VisibleForTesting.PRIVATE)
    fun refreshTrains () {
        viewModel?.refreshTrains()

    }
    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    fun updateSpinnerList(stations : List<String>) {
        val stationsAdapter =
            ArrayAdapter(testableContext, R.layout.support_simple_spinner_dropdown_item, stations)
        stationsList = stations
        stationsSpinner?.adapter = stationsAdapter
    }

    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    fun createAdapter(scheduledTrains:List<ScheduledTrain>) {
        val adapter = TrainTimeAdapter(scheduledTrains)
        recyclerView?.adapter = adapter
    }

    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    fun updateSelection(selectedStation: String) {
        if(stationsList.isNotEmpty() && (stationsSpinner?.selectedItem.toString() != (selectedStation))) {
            stationsSpinner?.setSelection(stationsList.indexOf(selectedStation))
        }
    }

    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    fun changeToAddFavoriteStations() {
        val uiViewModel = ViewModelProvider(requireActivity()).get(InterfaceViewModel::class.java)
        uiViewModel.showFavoriteStations()
    }

    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    fun initializeRecyclerView() {
        recyclerView?.layoutManager = LinearLayoutManager(testableContext, RecyclerView.VERTICAL, false)
    }
    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    fun onSpinerItemSelected (pos:Int) {

        if (stationsList.isNotEmpty()) {
            viewModel?.setStationNames(stationsList[pos])

        }
    }


    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    fun initializeStationSpinner() {
        stationsSpinner?.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, pos: Int, id: Long) {
                onSpinerItemSelected (pos)
            }
            override fun onNothingSelected(parent: AdapterView<*>) { /* do nothing */ }
        }
    }

    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    fun initializeViewModel() {
        viewModel?.stationNames!!.observe(requireActivity(), Observer { stations -> updateSpinnerList(stations)})
        viewModel?.stationSelected!!.observe(requireActivity(), Observer {selectedStation -> updateSelection(selectedStation)})
        viewModel?.scheduledTrains!!.observe(requireActivity(), Observer { scheduledTrains -> createAdapter(scheduledTrains) })
    }
}