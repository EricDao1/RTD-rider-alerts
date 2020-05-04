package com.msudenver.nighttrain.rtd_rider_alerts.ui

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.msudenver.nighttrain.rtd_rider_alerts.classes.FavoriteStation
import com.msudenver.nighttrain.rtd_rider_alerts.db.RTDDatabase
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class FavoriteStationViewModel(application: Application) : AndroidViewModel(application) {
    private val context : Context = getApplication<Application>().applicationContext
    var stationNames : List<FavoriteStation> = ArrayList()
    var filteredStationNames : MutableLiveData<List<FavoriteStation>> = MutableLiveData()

    init {
        GlobalScope.launch {
            val db = RTDDatabase.invoke(context)
            stationNames = db.favoriteStationDao().getAllStations()
            filterStations("")
        }
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

    fun updateValue(id : Int, value: Boolean) {
        GlobalScope.launch {
            val db = RTDDatabase.invoke(context)
            db.favoriteStationDao().updateStationFavorite(id, value)
        }
    }

}