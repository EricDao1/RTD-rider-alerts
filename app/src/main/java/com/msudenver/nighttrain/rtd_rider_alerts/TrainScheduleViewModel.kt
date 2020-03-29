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
    val stationNames : MutableLiveData<List<String>> = MutableLiveData<List<String>>()
    val stationSelected : MutableLiveData<String> = MutableLiveData<String>()
    val scheduledTrains : MutableLiveData<List<ScheduledTrain>> = MutableLiveData<List<ScheduledTrain>>()

    init {
        GlobalScope.launch {
            val db = RTDDatabase.invoke(context)
            val stops = db.stopDao().getTrainStops()
            stationNames.postValue(stops)
        }
    }

    fun setStationNames(station : String) {
        GlobalScope.launch {
            val db = RTDDatabase.invoke(context)
            val rightnow = Calendar.getInstance()
            val timerightnow = Calendar.getInstance()
            timerightnow.set(1970, 0, 1, (rightnow.get(Calendar.HOUR_OF_DAY)-7), (rightnow.get(Calendar.MINUTE)) )
            rightnow.set(rightnow.get(Calendar.YEAR) , (rightnow.get(Calendar.MONTH)), (rightnow.get(Calendar.DAY_OF_MONTH)), 0, 0)
            rightnow.set(rightnow.get(Calendar.YEAR), (rightnow.get(Calendar.MONTH)), (rightnow.get(Calendar.DAY_OF_MONTH ) +1), 0,0)
            val tomorrow = rightnow.time
            rightnow.set(rightnow.get(Calendar.YEAR), (rightnow.get(Calendar.MONTH)), (rightnow.get(Calendar.DAY_OF_MONTH) -1), 0, 0)
            val today = rightnow.time

            var nextTrains = db.stopTimeDao().getNextTrains(
                timerightnow.time, RiderAlertUtils.getDayOfWeek(Date()),
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
            stationSelected.postValue(station)
            scheduledTrains.postValue(nextTrains)
        }
    }

}