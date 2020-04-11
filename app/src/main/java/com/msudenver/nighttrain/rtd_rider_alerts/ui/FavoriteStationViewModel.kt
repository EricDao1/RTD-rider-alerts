package com.msudenver.nighttrain.rtd_rider_alerts.ui

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.msudenver.nighttrain.rtd_rider_alerts.classes.FavoriteStation

class FavoriteStationViewModel(application: Application) : AndroidViewModel(application) {
    var stationNames : List<FavoriteStation> = ArrayList()
    var filteredStationNames : MutableLiveData<List<FavoriteStation>> = MutableLiveData()

    init {
        // TODO get stations favorite status from db???
    }

    fun filterStations(filterText : String) {
        var filteredStations = ArrayList<FavoriteStation>()
        val upperFilterText = filterText.toUpperCase()
        var toggleFavorite = false
        for(s in stationNames) {
            if(s.stationName.toUpperCase().contains(upperFilterText)) {
                toggleFavorite = !toggleFavorite
                filteredStations.add(s)
            }
        }
        filteredStationNames.postValue(filteredStations)
    }

}