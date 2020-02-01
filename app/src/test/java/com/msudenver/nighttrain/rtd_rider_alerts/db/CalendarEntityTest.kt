package com.msudenver.nighttrain.rtd_rider_alerts.db

import android.content.Context
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4

import org.junit.After
import org.junit.Before
import org.junit.Test

import com.google.common.truth.Truth.assertThat
import org.junit.runner.RunWith
import org.junit.rules.TestRule
import org.junit.Rule

//import androidx.arch.core.executor.testing.InstantTaskExecutorRule

@RunWith(AndroidJUnit4::class)
class CalendarEntityTest {
    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()

    private lateinit var calendarDao: CalendarDao
    private lateinit var db: RTDDatabase
    private val context: Context = ApplicationProvider.getApplicationContext()

    @Before
    fun setUp() {
        db = Room.inMemoryDatabaseBuilder(
            context, RTDDatabase::class.java).allowMainThreadQueries().build()
        calendarDao = db.calendarDao()
    }

    @After
    fun tearDown() {
        db.close()
    }

    @Test
    fun writeCalendarAndReadDB() {
        val calendar = CalendarEntity(
            "TTh",
            tuesday = 1,
            thursday = 1,
            monday = 0,
            wednesday = 0,
            friday = 0,
            saturday = 0,
            sunday = 0
        )
        calendarDao.insertAll(calendar)
        val calendarRetrieve = calendarDao.getAll()

        assertThat(calendar).isEqualTo(calendarRetrieve[0])
        assertThat(calendar.thursday).isEqualTo(calendarRetrieve[0].thursday)

    }
}