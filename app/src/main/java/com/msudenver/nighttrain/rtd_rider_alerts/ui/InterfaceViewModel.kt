package com.msudenver.nighttrain.rtd_rider_alerts.ui

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData

class InterfaceViewModel(application: Application) : AndroidViewModel(application) {
    var showStations : MutableLiveData<Boolean> = MutableLiveData<Boolean>()

    init {
        showStations.value = false
    }
    fun showFavoriteStations() {
        showStations.value = true
    }

    fun showSchedule() {
        showStations.value = false
    }

}