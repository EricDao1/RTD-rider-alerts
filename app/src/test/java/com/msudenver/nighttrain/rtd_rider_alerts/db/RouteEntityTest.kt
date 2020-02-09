package com.msudenver.nighttrain.rtd_rider_alerts.db

import android.content.Context
import android.provider.Settings
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.google.common.truth.Truth
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.junit.After
import org.junit.Before

import org.junit.Assert.*
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class RouteEntityTest {
    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()

    private lateinit var routeDao: RouteDao
    private lateinit var db: RTDDatabase
    private val context: Context = ApplicationProvider.getApplicationContext()

    @Before
    fun setUp() {
        db = Room.inMemoryDatabaseBuilder(
            context, RTDDatabase::class.java).allowMainThreadQueries().build()
        routeDao = db.routeDao()
    }

    @After
    fun tearDown() {
        db.close()
    }

    @Test
    fun simpleInsertRecall() {
            val route1 = RouteEntity(
                id="A",
                name="A",
                type=1,
                backgroundColor=0xFFFFFF,
                textColor = 0XD11D11
            )
            val route0 = RouteEntity(
                id="113B",
                name="B",
                type=0,
                backgroundColor=0xFFFFF0,
                textColor = 0XD11D1F
            )
            routeDao.insertAll(route1)
            routeDao.insertAll(route0)
            val routeRetrieve = routeDao.getTrainRoutes()

            //confirm "bus" route (route_type of 1) is not picked up
            Truth.assertThat(routeRetrieve[0]).isEqualTo(route0)
            Truth.assertThat(routeRetrieve.size).isEqualTo(1)
        }
}