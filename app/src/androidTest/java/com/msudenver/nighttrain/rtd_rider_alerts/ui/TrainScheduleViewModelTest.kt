package com.msudenver.nighttrain.rtd_rider_alerts.ui

import androidx.lifecycle.ViewModelProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.rule.ActivityTestRule
import com.google.common.truth.Truth
import com.msudenver.nighttrain.rtd_rider_alerts.MainActivity
import com.msudenver.nighttrain.rtd_rider_alerts.db.CancelledTripEntity
import com.msudenver.nighttrain.rtd_rider_alerts.db.ScheduledTrain
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import java.util.*

@RunWith(AndroidJUnit4::class)
class TrainScheduleViewModelTest {

    @Rule
    @JvmField val activityRule = ActivityTestRule(MainActivity::class.java)

    @Test
    fun testCancelledTrainsFilter() {
        val cTrip2 = CancelledTripEntity(0,2, Date())
        val cTrip4 = CancelledTripEntity(1,4, Date())
        val cTrip6 = CancelledTripEntity(1,6, Date())

        val sTrip1 = ScheduledTrain()
        sTrip1.tripID = 1
        val sTrip2 = ScheduledTrain()
        sTrip2.tripID = 2
        val sTrip3 = ScheduledTrain()
        sTrip3.tripID = 3
        val sTrip4 = ScheduledTrain()
        sTrip4.tripID = 4
        val trainScheduleViewModel = ViewModelProvider(activityRule.activity).get(TrainScheduleViewModel::class.java)
        val trains = trainScheduleViewModel.getNextCancelledTrains(listOf(sTrip1,sTrip2,sTrip3,sTrip4),listOf(cTrip2,cTrip4,cTrip6))
        Truth.assertThat(trains[0].cancelledAlert).isEqualTo(0)
        Truth.assertThat(trains[1].cancelledAlert).isEqualTo(1)
        Truth.assertThat(trains[2].cancelledAlert).isEqualTo(0)
        Truth.assertThat(trains[3].cancelledAlert).isEqualTo(1)
    }
}