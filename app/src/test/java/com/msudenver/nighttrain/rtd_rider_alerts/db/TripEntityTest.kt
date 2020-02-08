package com.msudenver.nighttrain.rtd_rider_alerts.db

import android.content.Context
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.google.common.truth.Truth
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.junit.After
import org.junit.Before

import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class TripEntityTest {
    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()

    private lateinit var tripDao: TripDao
    private lateinit var routeDao: RouteDao
    private lateinit var calendarDao: CalendarDao
    private lateinit var db: RTDDatabase
    private val context: Context = ApplicationProvider.getApplicationContext()

    @Before
    fun setUp() {
        db = Room.inMemoryDatabaseBuilder(
            context, RTDDatabase::class.java).allowMainThreadQueries().build()
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
        GlobalScope.launch {
            val route0 = RouteEntity(
                id = "113B",
                name = "B",
                type = 0,
                backgroundColor = 0xFFFFF0,
                textColor = 0XD11D1F
            )
            routeDao.insertAll(route0)

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
            calendarDao.insertAll(calendar)

            val tripEntity1 = TripEntity(
                id = 113107684,
                routeId = "113B",
                description = "C-Line Union Station",
                serviceId = "FR",
                directionId = 0
            )
            tripDao.insertAll(tripEntity1)

            val tripEntities = tripDao.getAll()

            Truth.assertThat(tripEntities[0]).isEqualTo(tripEntity1)
        }
    }
}