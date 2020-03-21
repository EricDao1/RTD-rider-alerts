package com.msudenver.nighttrain.rtd_rider_alerts

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.android.volley.Network
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.ResponseDelivery
import com.android.volley.toolbox.NoCache
import com.google.common.truth.Truth
import com.msudenver.nighttrain.rtd_rider_alerts.db.*
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito.*

import java.text.SimpleDateFormat
import java.util.*

@RunWith(AndroidJUnit4::class)
class RiderAlertServiceTest {
    private val context: Context = ApplicationProvider.getApplicationContext()

    @Test
    fun processAlertsTripNotFound() {
        val rtdAlert1 = RTDAlert("Rider Alert", "C Line Trip 3:56 pm from Littleton / Mineral Ave Station to Union Station Track 11 and 11 other trips cancelled on Fri Feb 21 due to operator shortage.<br><br>Affected trips:<br>3:56 pm from Littleton / Mineral Ave Station to Union Station Track 11<br>4:34 pm from Union Station Track 12 to Littleton / Mineral Ave Station<br>6:41 pm from Littleton / Mineral Ave Station to Union Station Track 11<br>7:19 pm from Union Station Track 12 to Littleton / Mineral Ave Station<br>8:11 pm from Littleton / Mineral Ave Station to Union Station Track 11<br>8:49 pm from Union Station Track 12 to Littleton / Mineral Ave Station<br>9:41 pm from Littleton / Mineral Ave Station to Union Station Track 11<br>10:21 pm from Union Station Track 12 to Littleton / Mineral Ave Station<br>11:11 pm from Littleton / Mineral Ave Station to Union Station Track 11<br>11:51 pm from Union Station Track 12 to Littleton / Mineral Ave Station<br>12:41 am from Littleton / Mineral Ave Station to Union Station Track 11<br>1:21 am from Union Station Track 12 to Littleton / Mineral Ave Station",
            "February 21, 2020 3:56PM", "February 22, 2020 2:51AM")
        val rtdAlert2 = RTDAlert("Rider Alert", "", "February 22, 2020 6:35PM", "February 23, 2020 2:51AM")
        val alertList = listOf(rtdAlert1, rtdAlert2)
        val alertAttribute = RTDAlertAttribute("C", "C", "C Line", "rail", alerts = alertList)
        val riderAlerts = RiderAlerts(type="alert-routes",id="C",attributes = alertAttribute)
        val alertData = RTDAlertData(data=riderAlerts)
        val riderAlertService = RiderAlertService()
        val db = Room.inMemoryDatabaseBuilder(
            context, RTDDatabase::class.java).allowMainThreadQueries().build()
        riderAlertService.processAlerts(alertData, db)
       // val today = Date(2020,1,21)
       // val tomorrow = Date(2020,1,22)


        val rightnow = Calendar.getInstance()
        rightnow.set(rightnow.get(Calendar.YEAR) , (rightnow.get(Calendar.MONTH)), (rightnow.get(Calendar.DAY_OF_MONTH) -1), 0, 0)
        val today = rightnow.time
        rightnow.set(rightnow.get(Calendar.YEAR), (rightnow.get(Calendar.MONTH)), (rightnow.get(Calendar.DAY_OF_MONTH ) +1), 0,0)
        val tomorrow = rightnow.time

        Truth.assertThat(db.cancelledTripDao().getTrainsForToday(today,tomorrow)).isEmpty()
    }

    @Test
    fun processAlertsTripFound() {
        val rtdAlert1 = RTDAlert("Rider Alert", "C Line Trip 3:56 pm from Littleton / Mineral Ave Station to Union Station Track 11 and 11 other trips cancelled on Fri Feb 21 due to operator shortage.<br><br>Affected trips:<br>3:56 pm from Littleton / Mineral Ave Station to Union Station Track 11<br>4:34 pm from Union Station Track 12 to Littleton / Mineral Ave Station<br>6:41 pm from Littleton / Mineral Ave Station to Union Station Track 11<br>7:19 pm from Union Station Track 12 to Littleton / Mineral Ave Station<br>8:11 pm from Littleton / Mineral Ave Station to Union Station Track 11<br>8:49 pm from Union Station Track 12 to Littleton / Mineral Ave Station<br>9:41 pm from Littleton / Mineral Ave Station to Union Station Track 11<br>10:21 pm from Union Station Track 12 to Littleton / Mineral Ave Station<br>11:11 pm from Littleton / Mineral Ave Station to Union Station Track 11<br>11:51 pm from Union Station Track 12 to Littleton / Mineral Ave Station<br>12:41 am from Littleton / Mineral Ave Station to Union Station Track 11<br>1:21 am from Union Station Track 12 to Littleton / Mineral Ave Station",
            "February 21, 2020 3:56PM", "February 22, 2020 2:51AM")
        val rtdAlert2 = RTDAlert("Rider Alert", "C Line Trip 6:35 pm from Littleton / Mineral Ave Station to Union Station Track 11 and 9 other trips cancelled on Sat Feb 22 due to operator shortage.<br><br>Affected trips:<br>6:35 pm from Littleton / Mineral Ave Station to Union Station Track 11<br>7:12 pm from Union Station Track 12 to Littleton / Mineral Ave Station<br>8:05 pm from Littleton / Mineral Ave Station to Union Station Track 11<br>8:42 pm from Union Station Track 12 to Littleton / Mineral Ave Station<br>9:41 pm from Littleton / Mineral Ave Station to Union Station Track 11<br>10:20 pm from Union Station Track 12 to Littleton / Mineral Ave Station<br>11:11 pm from Littleton / Mineral Ave Station to Union Station Track 11<br>11:50 pm from Union Station Track 12 to Littleton / Mineral Ave Station<br>12:41 am from Littleton / Mineral Ave Station to Union Station Track 11<br>1:20 am from Union Station Track 12 to Littleton / Mineral Ave Station",
            "February 22, 2020 6:35PM", "February 23, 2020 2:51AM")
        val alertList = listOf(rtdAlert1, rtdAlert2)
        val alertAttribute = RTDAlertAttribute("C", "C", "C Line", "rail", alerts = alertList)
        val riderAlerts = RiderAlerts(type="alert-routes",id="C",attributes = alertAttribute)
        val alertData = RTDAlertData(data=riderAlerts)
        val riderAlertService = RiderAlertService()
        val clineMineral = "C-Line Mineral"
        val cLineUnion = "C-Line Union Station"

        val db = Room.inMemoryDatabaseBuilder(
            context, RTDDatabase::class.java).allowMainThreadQueries().build()

        //inject calendar (FRI/SAT)
        val calendar1 = CalendarEntity(id="FR", monday = 0, tuesday = 0, wednesday = 0, thursday = 0, friday = 1, saturday = 0, sunday = 0)
        val calendar3 = CalendarEntity(id="SA", monday = 0, tuesday = 0, wednesday = 0, thursday = 0, friday = 0, saturday = 1, sunday = 0)
        db.calendarDao().insertAll(calendar1,calendar3)

        //inject routeentity
        val route1 = RouteEntity(id="101C", name="C",type=0, backgroundColor=0x57C1E9, textColor=0xFFFFFF)
        db.routeDao().insertAll(route1)

        //inject stops
        val stop = StopEntity(id = 24896, name = "Littleton / Mineral Ave Station",lat = "39.580413", lon = "-105.024812",parentStation = 33716,description = "Vehicles Travelling West")
        val stop2 = StopEntity(id = 25434, name = "Union Station Track 12",lat = "39.755125", lon = "-105.003351",parentStation = 33727,description = "Vehicles Travelling West")
        db.stopDao().insertAll(stop,stop2)

        //inject trips
        val tripEntity1 = TripEntity(id = 113107730,routeId = "101C",description = cLineUnion,serviceId = "FR",directionId = 0)
        val tripEntity2 = TripEntity(id = 113107812,routeId = "101C",description = cLineUnion,serviceId = "FR",directionId = 0)
        val tripEntity3 = TripEntity(id = 113107852,routeId = "101C",description = cLineUnion,serviceId = "FR",directionId = 0)
        val tripEntity4 = TripEntity(id = 113107993,routeId = "101C",description = cLineUnion,serviceId = "FR",directionId = 0)
        val tripEntity5 = TripEntity(id = 113108006,routeId = "101C",description = cLineUnion,serviceId = "FR",directionId = 0)
        val tripEntity6 = TripEntity(id = 113108144,routeId = "101C",description = clineMineral,serviceId = "FR",directionId = 0)
        val tripEntity7 = TripEntity(id = 113108181,routeId = "101C",description = clineMineral,serviceId = "FR",directionId = 0)
        val tripEntity8 = TripEntity(id = 113108196,routeId = "101C",description = clineMineral,serviceId = "FR",directionId = 0)
        val tripEntity9 = TripEntity(id = 113108297,routeId = "101C",description = clineMineral,serviceId = "FR",directionId = 0)
        val tripEntity10 = TripEntity(id = 113108451,routeId = "101C",description = clineMineral,serviceId = "FR",directionId = 0)
        val tripEntity11 = TripEntity(id = 113109439,routeId = "101C",description = cLineUnion,serviceId = "SA",directionId = 0)
        val tripEntity12 = TripEntity(id = 113109487,routeId = "101C",description = cLineUnion,serviceId = "SA",directionId = 0)
        val tripEntity13 = TripEntity(id = 113109491,routeId = "101C",description = cLineUnion,serviceId = "SA",directionId = 0)
        val tripEntity14 = TripEntity(id = 113109493,routeId = "101C",description = cLineUnion,serviceId = "SA",directionId = 0)
        val tripEntity15 = TripEntity(id = 113109495,routeId = "101C",description = cLineUnion,serviceId = "SA",directionId = 0)
        val tripEntity16 = TripEntity(id = 113109548,routeId = "101C",description = clineMineral,serviceId = "SA",directionId = 0)
        val tripEntity17 = TripEntity(id = 113109551,routeId = "101C",description = clineMineral,serviceId = "SA",directionId = 0)
        val tripEntity18 = TripEntity(id = 113109556,routeId = "101C",description = clineMineral,serviceId = "SA",directionId = 0)
        val tripEntity19 = TripEntity(id = 113109562,routeId = "101C",description = clineMineral,serviceId = "SA",directionId = 0)
        val tripEntity20 = TripEntity(id = 113109633,routeId = "101C",description = clineMineral,serviceId = "SA",directionId = 0)
        db.tripDao().insertAll(tripEntity1, tripEntity2, tripEntity3, tripEntity4, tripEntity5, tripEntity6, tripEntity7, tripEntity8, tripEntity9, tripEntity10, tripEntity11, tripEntity12, tripEntity13, tripEntity14, tripEntity15, tripEntity16, tripEntity17, tripEntity18, tripEntity19, tripEntity20)

        //inject stoptimes
        // missing 9:41 p.m and 11:51 p.m. on friday -- NOTE that these times are -7 hours to be on UTC time
        val stopTime1 = StopTimeEntity(id=836938,tripId = 113107730,    arrivalTime = Date(Date.UTC(70,0,1,18,41,15)), departureTime = Date(70,0,1,25,41,15),   stopId = 24896, stopSequence =1)
        val stopTime2 = StopTimeEntity(id=838182,tripId = 113107812,    arrivalTime = Date(Date.UTC(70,0,1,15,56,15)), departureTime = Date(70,0,1,22,56,15),   stopId = 24896, stopSequence =1)
        val stopTime3 = StopTimeEntity(id=838850,tripId = 113107852,    arrivalTime = Date(Date.UTC(70,0,1,24,41,15)), departureTime = Date(70,0,1,31,41,15),   stopId = 24896, stopSequence =1)
        val stopTime4 = StopTimeEntity(id=840283,tripId = 113107993,    arrivalTime = Date(Date.UTC(70,0,1,23,11,15)), departureTime = Date(70,0,1,30,11,15),   stopId = 24896, stopSequence =1)
        val stopTime5 = StopTimeEntity(id=840365,tripId = 113108006,    arrivalTime = Date(Date.UTC(70,0,1,20,11,15)), departureTime = Date(70,0,1,27,11,15),   stopId = 24896, stopSequence =1)
        val stopTime6 = StopTimeEntity(id=842051,tripId = 113108144,    arrivalTime = Date(Date.UTC(70,0,1,25,21,0)), departureTime = Date(70,0,1,32,21,0),   stopId = 25434, stopSequence =1)
        val stopTime7 = StopTimeEntity(id=842535,tripId = 113108181,    arrivalTime = Date(Date.UTC(70,0,1,19,19,0)), departureTime = Date(70,0,1,26,19,0),   stopId = 25434, stopSequence =1)
        val stopTime8 = StopTimeEntity(id=842794,tripId = 113108196,    arrivalTime = Date(Date.UTC(70,0,1,16,34,0)), departureTime = Date(70,0,1,23,34,0),   stopId = 25434, stopSequence =1)
        val stopTime9 = StopTimeEntity(id=844057,tripId = 113108297,    arrivalTime = Date(Date.UTC(70,0,1,22,21,0)), departureTime = Date(70,0,1,29,21,0),   stopId = 25434, stopSequence =1)
        val stopTime10 = StopTimeEntity(id=846375,tripId = 113108451,    arrivalTime = Date(Date.UTC(70,0,1,20,49,0)), departureTime = Date(70,0,1,27,49,0),   stopId = 25434, stopSequence =1)
        val stopTime11 = StopTimeEntity(id=859448,tripId = 113109439,    arrivalTime = Date(Date.UTC(70,0,1,18,35,0)), departureTime = Date(70,0,1,25,35,0),   stopId = 24896, stopSequence =1)
        val stopTime12 = StopTimeEntity(id=859935,tripId = 113109487,    arrivalTime = Date(Date.UTC(70,0,1,24,41,0)), departureTime = Date(70,0,1,7,41,0),   stopId = 24896, stopSequence =1)
        val stopTime13 = StopTimeEntity(id=859983,tripId = 113109491,    arrivalTime = Date(Date.UTC(70,0,1,23,11,0)), departureTime = Date(70,0,1,30,11,0),   stopId = 24896, stopSequence =1)
        val stopTime14 = StopTimeEntity(id=860007,tripId = 113109493,    arrivalTime = Date(Date.UTC(70,0,1,21,41,0)), departureTime = Date(70,0,1,28,41,0),   stopId = 24896, stopSequence =1)
        val stopTime15 = StopTimeEntity(id=860031,tripId = 113109495,    arrivalTime = Date(Date.UTC(70,0,1,20,5,0)), departureTime = Date(70,0,1,27,5,0),   stopId = 24896, stopSequence =1)
        val stopTime16 = StopTimeEntity(id=860769,tripId = 113109548,    arrivalTime = Date(Date.UTC(70,0,1,25,20,30)), departureTime = Date(70,0,1,8,20,30),   stopId = 25434, stopSequence =1)
        val stopTime17 = StopTimeEntity(id=860805,tripId = 113109551,    arrivalTime = Date(Date.UTC(70,0,1,23,50,30)), departureTime = Date(70,0,1,30,50,30),   stopId = 25434, stopSequence =1)
        val stopTime18 = StopTimeEntity(id=860866,tripId = 113109556,    arrivalTime = Date(Date.UTC(70,0,1,20,42,30)), departureTime = Date(70,0,1,27,42,30),   stopId = 25434, stopSequence =1)
        val stopTime19 = StopTimeEntity(id=860941,tripId = 113109562,    arrivalTime = Date(Date.UTC(70,0,1,19,12,30)), departureTime = Date(70,0,1,26,12,30),   stopId = 25434, stopSequence =1)
        val stopTime20 = StopTimeEntity(id=862060,tripId = 113109633,    arrivalTime = Date(Date.UTC(70,0,1,22,20,30)), departureTime = Date(70,0,1,29,20,30),   stopId = 25434, stopSequence =1)
        db.stopTimeDao().insertAll(stopTime1, stopTime2, stopTime3, stopTime4, stopTime5, stopTime6, stopTime7,stopTime8, stopTime9,   stopTime10, stopTime11, stopTime12,stopTime13,stopTime14 ,stopTime15, stopTime16, stopTime17, stopTime18 ,stopTime19, stopTime20)

        riderAlertService.processAlerts(alertData, db)
        val today = Date(Date.UTC(120,1,21,0,0,0))
        val tomorrow = Date(Date.UTC(120,1,23,0,0,0))

        Truth.assertThat(db.cancelledTripDao().getTrainsForToday(today, tomorrow).size).isEqualTo(20)
    }

    @Test
    fun testDownloadAlerts() {
        val riderAlertService = RiderAlertService()
        /*val requestListener = mock(RequestQueue::class.java)
        val mNetwork = mock(Network::class.java)
        val mDelivery = mock(ResponseDelivery::class.java)
        val request = RequestQueue(NoCache(), mNetwork, 0, mDelivery)*/
        val request = mock(RequestQueue::class.java)
        val db = Room.inMemoryDatabaseBuilder(
            context, RTDDatabase::class.java).allowMainThreadQueries().build()

        /* When */
        riderAlertService.downloadRTDAlerts("C", db, request)

        /* Then */
        verify(request, times(1)).add(any(GsonRequest::class.java))
    }

}