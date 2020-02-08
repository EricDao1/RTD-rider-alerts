package com.msudenver.nighttrain.rtd_rider_alerts.db

import androidx.room.*

@Dao
interface CalendarDao {
    @Query("SELECT * FROM calendarentity")
    suspend fun getAll(): List<CalendarEntity>

    @Insert
    suspend fun insertAll(vararg calendar: CalendarEntity)

    @Delete
    suspend fun delete(calendar: CalendarEntity)

    @Update
    suspend fun updateCalendar(vararg calendar: CalendarEntity)
}