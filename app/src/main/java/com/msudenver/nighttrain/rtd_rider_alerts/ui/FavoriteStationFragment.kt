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
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.msudenver.nighttrain.rtd_rider_alerts.R
import com.msudenver.nighttrain.rtd_rider_alerts.classes.FavoriteStation

class FavoriteStationFragment : Fragment() {
    //just display
    private var stationRecyclerView : RecyclerView? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) : View {
        val view = inflater.inflate(R.layout.fragment_favorite_station, container, false)
        stationRecyclerView = view.findViewById<RecyclerView>(R.id.fav_station_recycler)

        stationRecyclerView?.layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)

        val fav1 = FavoriteStation(0,"10th & Osage", true)
        val fav2 = FavoriteStation(1,"Auraria West & Colfax", false)
        val favList = listOf(fav1,fav2)

        val stationViewModel = ViewModelProvider(requireActivity()).get(FavoriteStationViewModel::class.java)
        stationViewModel.stationNames = favList
        stationViewModel.filteredStationNames.observe(requireActivity(), Observer {s -> updateAdapter(s) } )
        stationViewModel.filterStations("")

        val searchText = view.findViewById<EditText>(R.id.search_text)
        searchText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(text: CharSequence, i: Int, i1: Int, after: Int) {
                if (after > 2) stationViewModel.filterStations(text.toString()) else stationViewModel.filterStations(
                    ""
                )
            }
            override fun onTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {} //do nothing}
            override fun afterTextChanged(editable: Editable) {}//do nothing}
        })


        val backButton : ImageButton = view.findViewById(R.id.back_schedule_button)
        backButton.setImageResource(R.drawable.baseline_keyboard_backspace_black_18dp)
        backButton.setOnClickListener { leaveView() }

        return view
    }

    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    fun leaveView() {
        val viewModel = ViewModelProvider(requireActivity()).get(InterfaceViewModel::class.java)
        viewModel.showSchedule()
    }

    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    fun updateAdapter(stations: List<FavoriteStation>) {
        val adapter = StationPickerAdapter(stations)
        stationRecyclerView?.adapter = adapter
    }
}