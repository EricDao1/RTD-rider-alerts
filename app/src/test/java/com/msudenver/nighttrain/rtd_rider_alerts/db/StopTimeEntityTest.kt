package com.msudenver.nighttrain.rtd_rider_alerts.db

import android.content.Context
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.google.common.truth.Truth
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.junit.runner.RunWith
import java.time.LocalDateTime
import java.util.*

@RunWith(AndroidJUnit4::class)
class StopTimeEntityTest {
    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()

    private lateinit var stopTimeDao: StopTimeDao
    private lateinit var stopDao: StopDao
    private lateinit var tripDao: TripDao
    private lateinit var routeDao: RouteDao
    private lateinit var calendarDao: CalendarDao

    private lateinit var db: RTDDatabase
    private val context: Context = ApplicationProvider.getApplicationContext()

    @Before
    fun setUp() {
        db = Room.inMemoryDatabaseBuilder(
            context, RTDDatabase::class.java).allowMainThreadQueries().build()
        stopTimeDao = db.stopTimeDao()
        stopDao = db.stopDao()
        tripDao = db.tripDao()
        routeDao = db.routeDao()
        calendarDao = db.calendarDao()
    }

    @After
    fun tearDown() {
        db.close()
    }

    @Test
    fun insertRead() {

        val stopTime = StopTimeEntity(
            id=10,
            tripId = 113107684,
            arrivalTime = Date(1970,1,1,10,1,30),
            departureTime = Date(1970, 1,1,10,1,30),
            stopId=23043,
            stopSequence=8
        )

        val stop = StopEntity(
            id=23043,
            name="10th & Osage Station",
            lat="",
            lon="",
            parentStation = 34109
        )

        val route0 = RouteEntity(
            id="101C",
            name="C",
            type=0,
            backgroundColor=0xFFFFF0,
            textColor = 0XD11D1F
        )

        val calendar = CalendarEntity(
            "FR",
            tuesday = 0,
            thursday = 0,
            monday = 0,
            wednesday = 0,
            friday = 1,
            saturday = 0,
            sunday = 0
        )

        val tripEntity1 = TripEntity(
            id=113107684,
            routeId="101C",
            description="C-Line Union Station",
            serviceId = "FR"
        )

        val scheduledTrainExpected = ScheduledTrain()

        scheduledTrainExpected.routeColor = 0xFFFFF0
        scheduledTrainExpected.routeTextColor = 0XD11D1F
        scheduledTrainExpected.time = Date(1970,1,1,10,1,30)
        scheduledTrainExpected.trainName = "C"
        scheduledTrainExpected.tripHeader = "C-Line Union Station"

        stopDao.insertAll(stop)
        routeDao.insertAll(route0)
        calendarDao.insertAll(calendar)
        tripDao.insertAll(tripEntity1)
        stopTimeDao.insertAll(stopTime)

        val scheduledTrain = stopTimeDao.getNextTrains(maxResults = 1,scheduleType = "FR",stopId = 23043,time=Date(1970,1,1,10,0,0))
        Truth.assertThat(scheduledTrain[0].time).isEqualTo(scheduledTrainExpected.time)
        Truth.assertThat(scheduledTrain[0].routeColor).isEqualTo(scheduledTrainExpected.routeColor)
        Truth.assertThat(scheduledTrain[0].routeTextColor).isEqualTo(scheduledTrainExpected.routeTextColor)
        Truth.assertThat(scheduledTrain[0].trainName).isEqualTo(scheduledTrainExpected.trainName)
        Truth.assertThat(scheduledTrain[0].tripHeader).isEqualTo(scheduledTrainExpected.tripHeader)
    }
}