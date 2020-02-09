package com.msudenver.nighttrain.rtd_rider_alerts.db

import androidx.room.*

@Dao
interface TripDao {
    @Query("SELECT * FROM tripentity")
    fun getAll(): List<TripEntity>

    @Insert
    fun insertAll(vararg trip: TripEntity)

    @Delete
    fun delete(trip: TripEntity)

    @Update
    fun updateTrip(vararg trip: TripEntity)
}