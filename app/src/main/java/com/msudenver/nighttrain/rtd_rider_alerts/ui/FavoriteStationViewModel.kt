package com.msudenver.nighttrain.rtd_rider_alerts.ui

import android.app.Application
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.msudenver.nighttrain.rtd_rider_alerts.classes.FavoriteStation

class FavoriteStationViewModel() : ViewModel() {
    var stationNames : List<FavoriteStation> = ArrayList()
    var filteredStationNames : MutableLiveData<List<FavoriteStation>> = MutableLiveData()

    init {
        // TODO get stations favorite status from db???
    }

    fun filterStations(filterText : String) {
        var filteredStations = ArrayList<FavoriteStation>()
        val upperFilterText = filterText.toUpperCase()
        for(s in stationNames) {
            if(s.stationName.toUpperCase().contains(upperFilterText)) {
                filteredStations.add(s)
            }
        }
        filteredStationNames.postValue(filteredStations)
    }

}