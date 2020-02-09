package com.msudenver.nighttrain.rtd_rider_alerts

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.msudenver.nighttrain.rtd_rider_alerts.db.RTDDatabase
import com.msudenver.nighttrain.rtd_rider_alerts.db.ScheduledTrain
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.security.AccessControlContext
import java.util.*

class TrainSchduleViewModel(val context : Context) : ViewModel() {



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
            _stationNames.value = db.stopDao().getTrainStops()
        }


       /* val listscheduletrains = ScheduledTrain()
        listscheduletrains.routeColor = 0xfffff0
        listscheduletrains.routeTextColor = 0xd11d1f
        listscheduletrains.time = Date(1970, 1, 1, 10, 1, 30)
        listscheduletrains.trainName = "C"
        listscheduletrains.tripHeader = "C-Line Union Station"*/


    }
    fun setStationNames(station : String) {
        GlobalScope.launch {
            val db = RTDDatabase.invoke(context)
            scheduledTrains.value = db.stopTimeDao().getNextTrains(
                Date(),
                RiderAlertUtils.getDayOfWeek(Date()),
                station,
                maxResults = 10
            )
        }
    }

}