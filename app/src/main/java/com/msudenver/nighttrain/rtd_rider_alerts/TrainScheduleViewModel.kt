package com.msudenver.nighttrain.rtd_rider_alerts

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.msudenver.nighttrain.rtd_rider_alerts.db.RTDDatabase
import com.msudenver.nighttrain.rtd_rider_alerts.db.ScheduledTrain
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
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
            val rightnow = Calendar.getInstance()
            rightnow.set(rightnow.get(Calendar.YEAR) , (rightnow.get(Calendar.MONTH)), (rightnow.get(Calendar.DAY_OF_MONTH)), 0, 0)
            val rightnowUTC = Date(rightnow.time.time % 24*3600*1000 - 7*3600000)

            rightnow.set(rightnow.get(Calendar.YEAR), (rightnow.get(Calendar.MONTH)), (rightnow.get(Calendar.DAY_OF_MONTH ) +1), 0,0)
            val tomorrow = rightnow.time

           // val tomorrow = Date(rightnow.year, rightnow.month, rightnow.date + 1)//Calendar.getInstance()

            rightnow.set(rightnow.get(Calendar.YEAR), (rightnow.get(Calendar.MONTH)), (rightnow.get(Calendar.DAY_OF_MONTH) -1), 0, 0)
            val today = rightnow.time


            var nextTrains = db.stopTimeDao().getNextTrains(
                rightnowUTC, RiderAlertUtils.getDayOfWeek(Date()),
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