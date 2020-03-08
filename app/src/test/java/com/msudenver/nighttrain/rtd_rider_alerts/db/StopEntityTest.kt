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

import org.junit.Assert.*
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class StopEntityTest {
    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()

    private lateinit var stopDao: StopDao
    private lateinit var db: RTDDatabase
    private val context: Context = ApplicationProvider.getApplicationContext()

    @Before
    fun setUp() {
        db = Room.inMemoryDatabaseBuilder(
            context, RTDDatabase::class.java).allowMainThreadQueries().build()
        stopDao = db.stopDao()
    }

    @After
    fun tearDown() {
        db.close()
    }
    @Test
    fun insertRead() {
/* killed for now -- have temp solution here, need to fix when adding back train stops
            val stop1 = StopEntity(
                id=23043,
                description = "Vehicle Travelling West",
                name="10th & Osage Station",
                lat = "39.732222",
                lon = "-105.005654"
            )

            val stop2 = StopEntity(
                id=23059,
                name="10th & Osage Station",
                description = "Vehicle Travelling West",
                lat = "39.732222",
                lon = "-105.005654",
                parentStation=34109
            )
            stopDao.insertAll(stop1, stop2)

            val returnedStops = stopDao.getTrainStops()

            Truth.assertThat(returnedStops[0]).isEqualTo("10th & Osage Station")
*/
    }

}