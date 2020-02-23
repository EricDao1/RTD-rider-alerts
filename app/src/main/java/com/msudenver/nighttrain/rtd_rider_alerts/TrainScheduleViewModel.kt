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
            val rightnow = Date()
            val tomorrow = Date(rightnow.year, rightnow.month, rightnow.date + 1)//Calendar.getInstance()
            val today = Date(rightnow.year, rightnow.month, rightnow.date) //Calendar.getInstance()
            //today.set(today.get(Calendar.YEAR)-1900, today.get(Calendar.MONTH), today.get(Calendar.DAY_OF_MONTH))
            //tomorrow.set(tomorrow.get(Calendar.YEAR)-1900, tomorrow.get(Calendar.MONTH), tomorrow.get(Calendar.DAY_OF_MONTH)+1)
            //val nowtime = Date(70,0,0,rightnow.hours,rightnow.minutes, rightnow.seconds)
            var nextTrains = db.stopTimeDao().getNextTrains(
                Date(70,0,1,rightnow.hours-7,rightnow.minutes, rightnow.seconds) ,
                RiderAlertUtils.getDayOfWeek(Date()),
                station,
                maxResults = 20
            )
            val cancelledTrains = db.cancelledTripDao().getTrainsForToday(today, tomorrow)
            for(trip in nextTrains) {
                var isCancelled = 0
                for(cancelledTrain in cancelledTrains) {
                    if(cancelledTrain.tripId == trip.tripID) {
                        isCancelled = 1
                        break
                    }
                }
                trip.cancelledAlert = isCancelled
            }
            scheduledTrains.postValue(nextTrains)
        }
    }

}