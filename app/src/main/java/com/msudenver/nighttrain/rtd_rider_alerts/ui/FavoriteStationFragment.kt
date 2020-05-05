package com.msudenver.nighttrain.rtd_rider_alerts.ui

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageButton
import androidx.annotation.VisibleForTesting
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.msudenver.nighttrain.rtd_rider_alerts.R
import com.msudenver.nighttrain.rtd_rider_alerts.classes.FavoriteStation

class FavoriteStationFragment : Fragment() {
    //just display
    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    var stationRecyclerView : RecyclerView? = null
    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    var searchText : EditText? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) : View {
        val view = inflater.inflate(R.layout.fragment_favorite_station, container, false)
        stationRecyclerView = view.findViewById<RecyclerView>(R.id.fav_station_recycler)
        searchText = view.findViewById<EditText>(R.id.search_text)

        val backButton : ImageButton = view.findViewById<ImageButton>(R.id.back_schedule_button)
        backButton.setImageResource(R.drawable.baseline_keyboard_backspace_black_18dp)
        backButton.setOnClickListener { leaveView() }

        return view
    }

    override fun onResume() {
        super.onResume()

        val stationViewModel = ViewModelProvider(requireActivity()).get(TrainScheduleViewModel::class.java)
        initializeRecyclerView()
        initializeSearchText(stationViewModel)
        stationViewModel.filteredStationNames.observe(requireActivity(), Observer {s -> updateAdapter(s, stationViewModel) } )
        stationViewModel.filterStations("")
    }

    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    fun leaveView() {
        val viewModel = ViewModelProvider(requireActivity()).get(InterfaceViewModel::class.java)
        viewModel.showSchedule()
    }

    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    fun updateAdapter(stations: List<FavoriteStation>, viewModel: TrainScheduleViewModel) {
        fun update(id:Int, value:Boolean) {
            viewModel.updateValue(id,value)
        }
        val adapter = StationPickerAdapter(stations, ::update)
        stationRecyclerView?.adapter = adapter
    }

    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    fun initializeRecyclerView() {
        stationRecyclerView?.layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
    }

    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    fun initializeSearchText(stationViewModel : TrainScheduleViewModel) {
        searchText?.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(text: CharSequence, i: Int, i1: Int, after: Int) {
                stationViewModel.filterStations(getFilterText(text.toString(),after))
            }
            override fun onTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {} //do nothing}
            override fun afterTextChanged(editable: Editable) {}//do nothing}
        })
    }

    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    fun getFilterText(possibleString : String, after : Int) : String {
        return if (after > 2) possibleString else ""
    }
}