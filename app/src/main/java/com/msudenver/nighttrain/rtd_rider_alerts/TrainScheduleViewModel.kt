package com.msudenver.nighttrain.rtd_rider_alerts

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.msudenver.nighttrain.rtd_rider_alerts.db.RTDDatabase
import com.msudenver.nighttrain.rtd_rider_alerts.db.ScheduledTrain
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.security.AccessControlContext
import java.util.*

class TrainScheduleViewModel(application: Application) : AndroidViewModel(application) {

    val context : Context = getApplication<Application>().applicationContext
    private val _stationNames : MutableLiveData<List<String>>
    val stationNames : LiveData<List<String>>
        get() = _stationNames

    private val _stationSelected : MutableLiveData<String>
    val stationSelected : LiveData<String>
        get() = _stationSelected

    val scheduledTrains : MutableLiveData<List<ScheduledTrain>> by lazy {
        MutableLiveData<List<ScheduledTrain>>()
    }

    init  {
        _stationSelected = MutableLiveData<String>()
        _stationNames = MutableLiveData<List<String>>()
        GlobalScope.launch {
            val db = RTDDatabase.invoke(context)
            _stationNames.postValue(db.stopDao().getTrainStops())
        }
    }

    fun setStationNames(station : String) {
        GlobalScope.launch {
            val db = RTDDatabase.invoke(context)
            scheduledTrains.postValue(db.stopTimeDao().getNextTrains(
                Date(),
                RiderAlertUtils.getDayOfWeek(Date()),
                station,
                maxResults = 10
            ))
        }
    }

}