package com.msudenver.nighttrain.rtd_rider_alerts.db

import androidx.room.*

@Dao
interface CalendarDao {
    @Query("SELECT * FROM calendarentity")
    fun getAll(): List<CalendarEntity>

    @Insert
    fun insertAll(vararg calendar: CalendarEntity)

    @Delete
    fun delete(calendar: CalendarEntity)

    @Update
    fun updateCalendar(vararg calendar: CalendarEntity)
}