package com.msudenver.nighttrain.rtd_rider_alerts.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageButton
import androidx.annotation.VisibleForTesting
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.msudenver.nighttrain.rtd_rider_alerts.R
import com.msudenver.nighttrain.rtd_rider_alerts.classes.FavoriteStation

class FavoriteStationFragment : Fragment() {
    //just display
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) : View {
        val view = inflater.inflate(R.layout.fragment_favorite_station, container, false)
        val stationRecyclerView = view.findViewById<RecyclerView>(R.id.fav_station_recycler)

        stationRecyclerView.layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)

        val fav1 = FavoriteStation("10th & Osage", true)
        val fav2 = FavoriteStation("Auraria West & Colfax", false)
        val favList = listOf(fav1,fav2)

        val adapter = StationPickerAdapter(favList)
        stationRecyclerView.adapter = adapter

        val searchText = view.findViewById<EditText>(R.id.search_text)
        //searchText.keyListener

        val backButton : ImageButton = view.findViewById(R.id.back_schedule_button)
        backButton.setImageResource(R.drawable.baseline_keyboard_backspace_black_18dp)
        backButton.setOnClickListener {
            leaveView()
        }

        return view
    }

    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    fun leaveView() {
        val viewModel = ViewModelProvider(requireActivity()).get(InterfaceViewModel::class.java)
        viewModel.showSchedule()
    }
}