package com.msudenver.nighttrain.rtd_rider_alerts.db

import android.content.Context
import android.icu.util.Calendar
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.google.common.truth.Truth
import com.ibm.icu.impl.Grego
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.junit.runner.RunWith
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

            val calendar0 = CalendarEntity(id="DPSWK", monday = 1, tuesday = 1, wednesday = 1, thursday = 1, friday = 1, saturday = 0, sunday = 0)
            val calendar1 = CalendarEntity(id="FR", monday = 0, tuesday = 0, wednesday = 0, thursday = 0, friday = 1, saturday = 0, sunday = 0)
            val calendar2 = CalendarEntity(id="MT", monday = 1, tuesday = 1, wednesday = 1, thursday = 1, friday = 0, saturday = 0, sunday = 0)
            val calendar3 = CalendarEntity(id="SA", monday = 0, tuesday = 0, wednesday = 0, thursday = 0, friday = 0, saturday = 1, sunday = 0)
            val calendar4 = CalendarEntity(id="SU", monday = 0, tuesday = 0, wednesday = 0, thursday = 0, friday = 0, saturday = 0, sunday = 1)
            val calendar5 = CalendarEntity(id="WK", monday = 1, tuesday = 1, wednesday = 1, thursday = 1, friday = 1, saturday = 0, sunday = 0)

            calendarDao.insertAll(calendar0,calendar1,calendar2,calendar3,calendar4,calendar5)


            val route0 = RouteEntity(id="101C", name="C",type=0, backgroundColor=0xF79239, textColor=0xFFFFFF)
            val route1 = RouteEntity(id="101D", name="D",type=0, backgroundColor=0x8348, textColor=0xFFFFFF)
            val route2 = RouteEntity(id="101E", name="E",type=0, backgroundColor=0x552683, textColor=0xFFFFFF)
            val route3 = RouteEntity(id="101F", name="F",type=0, backgroundColor=0xEE3E33, textColor=0xFFFFFF)
            val route4 = RouteEntity(id="101H", name="H",type=0, backgroundColor=0x0075BE, textColor=0xFFFFFF)
            val route5 = RouteEntity(id="103W", name="W",type=0, backgroundColor=0x009DAA, textColor=0xFFFFFF)
            val route6 = RouteEntity(id="107R", name="R",type=0, backgroundColor=0xC4D600, textColor=0x0)
            val route7 = RouteEntity(id="109L", name="L",type=0, backgroundColor=0xFFCE00, textColor=0x0)
            val route8 = RouteEntity(id="113B", name="B",type=2, backgroundColor=0x4E9D2D, textColor=0xFFFFFF)
            val route9 = RouteEntity(id="113G", name="G",type=2, backgroundColor=0xF6B221, textColor=0xFFFFFF)
            val route10 = RouteEntity(id="A", name="A",type=2, backgroundColor=0x57C1E9, textColor=0xFFFFFF)

            routeDao.insertAll(route0, route1, route2, route3, route4, route5, route6, route7, route8, route9, route10)

    }

    @After
    fun tearDown() {
        db.close()
    }

    @Test
    fun insertRead() {

            val stopTime = StopTimeEntity(
                id = 10,
                tripId = 113107684,
                arrivalTime = Date(70, 0, 1, 10, 1, 30),
                departureTime = Date(70, 0, 1, 10, 1, 30),
                stopId = 23043,
                stopSequence = 8
            )

            val stop = StopEntity(
                id = 23043,
                name = "10th & Osage Station",
                lat = "",
                lon = "",
                parentStation = 34109,
                description = "Vehicles Travelling West"
            )
            val tripEntity1 = TripEntity(
                id = 113107684,
                routeId = "101C",
                description = "C-Line Union Station",
                serviceId = "FR",
                directionId = 0
            )
            val scheduledTrainExpected = ScheduledTrain()

            scheduledTrainExpected.routeColor = 0xF79239
            scheduledTrainExpected.routeTextColor = 0xFFFFFF
            scheduledTrainExpected.time = Date(70, 0, 1, 10, 1, 30)
            scheduledTrainExpected.trainName = "C"
            scheduledTrainExpected.tripHeader = "C-Line Union Station"

            stopDao.insertAll(stop)
            tripDao.insertAll(tripEntity1)
            stopTimeDao.insertAll(stopTime)

            val scheduledTrain = stopTimeDao.getNextTrains(
                maxResults = 1,
                dayOfWeek = "friday",
                stopId = 23043,
                time = Date(70, 0, 1, 10, 1, 0)
            )
            Truth.assertThat(scheduledTrain[0].time).isEqualTo(scheduledTrainExpected.time)
            Truth.assertThat(scheduledTrain[0].routeColor)
                .isEqualTo(scheduledTrainExpected.routeColor)
            Truth.assertThat(scheduledTrain[0].routeTextColor)
                .isEqualTo(scheduledTrainExpected.routeTextColor)
            Truth.assertThat(scheduledTrain[0].trainName)
                .isEqualTo(scheduledTrainExpected.trainName)
            Truth.assertThat(scheduledTrain[0].tripHeader)
                .isEqualTo(scheduledTrainExpected.tripHeader)
    }

    @Test
    fun findCancelledTrain() {

            val trip1 = TripEntity(
                id = 113109947,
                description = "E-Line Union Station",
                routeId = "101E",
                serviceId = "SU",
                directionId = 0
            )
            val trip2 = TripEntity(
                id = 113109953,
                description = "E-Line Union Station",
                routeId = "101E",
                serviceId = "SU",
                directionId = 0
            )
            val trip3 = TripEntity(
                id = 113109956,
                description = "E-Line Union Station",
                routeId = "101E",
                serviceId = "SU",
                directionId = 0
            )
            val trip4 = TripEntity(
                id = 113109959,
                description = "E-Line Union Station",
                routeId = "101E",
                serviceId = "SU",
                directionId = 0
            )
            val trip5 = TripEntity(
                id = 113109962,
                description = "E-Line Union Station",
                routeId = "101E",
                serviceId = "SU",
                directionId = 0
            )
            val trip6 = TripEntity(
                id = 113109967,
                description = "E-Line Union Station",
                routeId = "101E",
                serviceId = "SU",
                directionId = 0
            )
            val trip7 = TripEntity(
                id = 113109968,
                description = "E-Line Union Station",
                routeId = "101E",
                serviceId = "SU",
                directionId = 0
            )
            val trip8 = TripEntity(
                id = 113109973,
                description = "E-Line Union Station",
                routeId = "101E",
                serviceId = "SU",
                directionId = 0
            )
            val trip9 = TripEntity(
                id = 113109976,
                description = "E-Line Union Station",
                routeId = "101E",
                serviceId = "SU",
                directionId = 0
            )
            val trip10 = TripEntity(
                id = 113109979,
                description = "E-Line Union Station",
                routeId = "101E",
                serviceId = "SU",
                directionId = 0
            )
            val trip11 = TripEntity(
                id = 113109981,
                description = "E-Line Union Station",
                routeId = "101E",
                serviceId = "SU",
                directionId = 0
            )
            val trip12 = TripEntity(
                id = 113109247,
                description = "E-Line Union Station",
                routeId = "101E",
                serviceId = "SA",
                directionId = 0
            )
            val trip13 = TripEntity(
                id = 113109605,
                description = "E-Line RidgeGate",
                routeId = "101E",
                serviceId = "SA",
                directionId = 0
            )
            val trip14 = TripEntity(
                id = 113110087,
                description = "E-Line RidgeGate",
                routeId = "101E",
                serviceId = "SU",
                directionId = 0
            )
            val trip15 = TripEntity(
                id = 113107809,
                description = "F-Line 18th & California",
                routeId = "101F",
                serviceId = "FR",
                directionId = 0
            )
            val trip16 = TripEntity(
                id = 113108347,
                description = "F-Line RidgeGate",
                routeId = "101F",
                serviceId = "FR",
                directionId = 0
            )
            val trip17 = TripEntity(
                id = 113108518,
                description = "F-Line 18th & California",
                routeId = "101F",
                serviceId = "MT",
                directionId = 0
            )
            val trip18 = TripEntity(
                id = 113108917,
                description = "F-Line RidgeGate",
                routeId = "101F",
                serviceId = "MT",
                directionId = 0
            )

            tripDao.insertAll(
                trip1,
                trip2,
                trip3,
                trip4,
                trip5,
                trip6,
                trip7,
                trip8,
                trip9,
                trip10,
                trip11,
                trip12,
                trip13,
                trip14,
                trip15,
                trip16,
                trip17,
                trip18
            )

            val stop0 = StopEntity(
                id = 35211,
                name = "Ridgegate Parkway Station",
                lat = "39.520244",
                lon = "-104.865725",
                parentStation = 35245,
                description = "Vehicles Travelling West"
            )
            val stop1 = StopEntity(
                id = 35212,
                name = "Ridgegate Parkway Station",
                lat = "39.520791",
                lon = "-104.86521",
                parentStation = 35245,
                description = "Vehicles Travelling West"
            )

            stopDao.insertAll(stop0, stop1)

            val stopTime1 = StopTimeEntity(
                tripId = 113107809,
                arrivalTime = Date(70, 0, 1, 15, 42, 0),
                departureTime = Date(70, 0, 1, 15, 42, 0),
                stopId = 35212,
                stopSequence = 1
            )
            val stopTime2 = StopTimeEntity(
                tripId = 113108347,
                arrivalTime = Date(70, 0, 1, 15, 40, 15),
                departureTime = Date(70, 0, 1, 15, 40, 15),
                stopId = 35211,
                stopSequence = 22
            )
            val stopTime3 = StopTimeEntity(
                tripId = 113108518,
                arrivalTime = Date(70, 0, 1, 15, 42, 0),
                departureTime = Date(70, 0, 1, 15, 42, 0),
                stopId = 35212,
                stopSequence = 1
            )
            val stopTime4 = StopTimeEntity(
                tripId = 113108917,
                arrivalTime = Date(70, 0, 1, 15, 40, 15),
                departureTime = Date(70, 0, 1, 15, 40, 15),
                stopId = 35211,
                stopSequence = 22
            )
            val stopTime5 = StopTimeEntity(
                tripId = 113109247,
                arrivalTime = Date(70, 0, 1, 15, 41, 0),
                departureTime = Date(70, 0, 1, 15, 41, 0),
                stopId = 35212,
                stopSequence = 1
            )
            val stopTime6 = StopTimeEntity(
                tripId = 113109605,
                arrivalTime = Date(70, 0, 1, 15, 41, 15),
                departureTime = Date(70, 0, 1, 15, 41, 15),
                stopId = 35211,
                stopSequence = 21
            )
            val stopTime7 = StopTimeEntity(
                tripId = 113109956,
                arrivalTime = Date(70, 0, 1, 15, 41, 0),
                departureTime = Date(70, 0, 1, 15, 41, 0),
                stopId = 35212,
                stopSequence = 1
            )
            val stopTime8 = StopTimeEntity(
                tripId = 113110087,
                arrivalTime = Date(70, 0, 1, 15, 41, 15),
                departureTime = Date(70, 0, 1, 15, 41, 15),
                stopId = 35211,
                stopSequence = 21
            )

            stopTimeDao.insertAll(
                stopTime1,
                stopTime2,
                stopTime3,
                stopTime4,
                stopTime5,
                stopTime6,
                stopTime7,
                stopTime8
            )

            val cancelledTrip = stopTimeDao.getCancelledTrip(
                "sunday",
                Date(70, 0, 1, 15, 40),
                Date(70, 0, 1, 15, 42),
                "E",
                "Ridgegate Parkway Station"
            ) //"sunday",
            Truth.assertThat(cancelledTrip).isEqualTo(113109956)
    }
}