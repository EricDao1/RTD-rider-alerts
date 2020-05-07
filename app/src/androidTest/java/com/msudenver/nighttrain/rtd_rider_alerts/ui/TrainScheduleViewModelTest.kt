package com.msudenver.nighttrain.rtd_rider_alerts.ui

import android.content.Context
import androidx.lifecycle.ViewModelProvider
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.rule.ActivityTestRule
import com.google.common.truth.Truth
import com.msudenver.nighttrain.rtd_rider_alerts.MainActivity
import com.msudenver.nighttrain.rtd_rider_alerts.classes.FavoriteStation
import com.msudenver.nighttrain.rtd_rider_alerts.db.*
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import java.util.*

@RunWith(AndroidJUnit4::class)
class TrainScheduleViewModelTest {

    @JvmField @Rule val activityRule = ActivityTestRule(MainActivity::class.java)
    private val context: Context = ApplicationProvider.getApplicationContext()

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

    @Test
    fun testCheckContainsTrue() {
        val trainScheduleViewModel = ViewModelProvider(activityRule.activity).get(TrainScheduleViewModel::class.java)
        val favStation = FavoriteStation(0,"help",true)
        Truth.assertThat(trainScheduleViewModel.checkContains(favStation,"el")).isTrue()
        Truth.assertThat(trainScheduleViewModel.checkContains(favStation,"lp")).isTrue()
        Truth.assertThat(trainScheduleViewModel.checkContains(favStation,"he")).isTrue()
    }

    @Test
    fun testCheckContainsFalse() {
        val trainScheduleViewModel = ViewModelProvider(activityRule.activity).get(TrainScheduleViewModel::class.java)
        val favStation = FavoriteStation(0,"help",true)
        Truth.assertThat(trainScheduleViewModel.checkContains(favStation,"hela")).isFalse()
        Truth.assertThat(trainScheduleViewModel.checkContains(favStation,"elpo")).isFalse()
    }

    @Test
    fun checkUpdateValue() {

        val db2 = Room.inMemoryDatabaseBuilder(context, RTDDatabase::class.java).allowMainThreadQueries().build()
        val favoriteDao = db2.favoriteStationDao()
        val stopDao = db2.stopDao()

        val stop10 = StopEntity(10,"station 10","37", "38", "stations",0)
        val stop11 = StopEntity(11,"station 11","37", "38", "stations",0)
        val stop12 = StopEntity(12,"station 12","37", "38", "stations",0)
        val stop13 = StopEntity(13,"station 13","37", "38", "stations",0)
        stopDao.insertAll(stop10,stop11,stop12,stop13)

        val fav1 = FavoriteStationEntity(1, 10, true)
        val fav2 = FavoriteStationEntity(2, 11, false)
        val fav3 = FavoriteStationEntity(3, 12, true)
        favoriteDao.insertAll(fav1,fav2,fav3)

        val trainScheduleViewModel = ViewModelProvider(activityRule.activity).get(TrainScheduleViewModel::class.java)
        trainScheduleViewModel.db = db2
        Truth.assertThat(favoriteDao.getFavoriteStations().size).isEqualTo(2)
        trainScheduleViewModel.updateValueInternal(1,false)
        Truth.assertThat(favoriteDao.getFavoriteStations().size).isEqualTo(1)
        trainScheduleViewModel.updateValueInternal(2, true)
        trainScheduleViewModel.updateValueInternal(1,true)
        Truth.assertThat(favoriteDao.getFavoriteStations().size).isEqualTo(3)

    }
}