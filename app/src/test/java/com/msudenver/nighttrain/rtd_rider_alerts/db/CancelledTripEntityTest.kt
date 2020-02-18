package com.msudenver.nighttrain.rtd_rider_alerts.db

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.google.common.truth.Truth
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.util.*

@RunWith(AndroidJUnit4::class)
class CancelledTripEntityTest {

    private lateinit var db: RTDDatabase
    private val context: Context = ApplicationProvider.getApplicationContext()

    private lateinit var tripDao: TripDao
    private lateinit var routeDao: RouteDao
    private lateinit var calendarDao: CalendarDao

    @Before
    fun setUp() {

        db = Room.inMemoryDatabaseBuilder(
            context, RTDDatabase::class.java).allowMainThreadQueries().build()
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

        val route10 = RouteEntity(id="A", name="A",type=2, backgroundColor=0x57C1E9, textColor=0xFFFFFF)

        routeDao.insertAll(route10)
    }
    @Test
    fun deleteDuplicateRiderAlerts() {
        //val calendar = Calendar.getInstance()
        //calendar.set(70,0,1,12,0,0)
        val calendar = Date(70,0,1)
        val trip = TripEntity(id=100, description = "something",routeId = "A", serviceId = "SA", directionId = 0)
        val trip2 = TripEntity(id=101, description = "something",routeId = "A", serviceId = "SA", directionId = 0)

        tripDao = db.tripDao()
        tripDao.insertAll(trip, trip2)

        val cancelledTripDao = db.cancelledTripDao()
        val cancelledTrip1 = CancelledTripEntity(tripId = 100, dayDate = calendar)
        val cancelledTrip2 = CancelledTripEntity(tripId = 100, dayDate = calendar)
        val cancelledTrip3 = CancelledTripEntity(tripId = 101, dayDate = calendar)

        cancelledTripDao.insertAll(cancelledTrip1,cancelledTrip2, cancelledTrip3)
        cancelledTripDao.deleteDuplicateAlerts()

        val cancelled = cancelledTripDao.getTrainsForToday(calendar)
        Truth.assertThat(cancelled.size).isEqualTo(2)
    }
}