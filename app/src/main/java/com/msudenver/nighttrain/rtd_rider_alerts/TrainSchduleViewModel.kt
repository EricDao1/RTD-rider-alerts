package com.msudenver.nighttrain.rtd_rider_alerts

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.msudenver.nighttrain.rtd_rider_alerts.db.RTDDatabase
import com.msudenver.nighttrain.rtd_rider_alerts.db.ScheduledTrain
import java.util.*

class TrainSchduleViewModel(val db : RTDDatabase) : ViewModel() {

    val scheduledTrains : MutableLiveData<List<ScheduledTrain>> by lazy {
        MutableLiveData<List<ScheduledTrain>>()

    }

    init {
        val listscheduletrains = ScheduledTrain()
        listscheduletrains.routeColor = 0xfffff0
        listscheduletrains.routeTextColor = 0xd11d1f
        listscheduletrains.time = Date(1970, 1, 1, 10, 1, 30)
        listscheduletrains.trainName = "C"
        listscheduletrains.tripHeader = "C-Line Union Station"
        scheduledTrains.value = listscheduletrains//db.stopTimeDao().getNextTrains()
    }


}