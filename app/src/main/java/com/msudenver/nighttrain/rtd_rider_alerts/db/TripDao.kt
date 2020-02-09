package com.msudenver.nighttrain.rtd_rider_alerts.db

import androidx.room.*

@Dao
interface TripDao {
    @Query("SELECT * FROM tripentity")
    suspend fun getAll(): List<TripEntity>

    @Insert
    suspend fun insertAll(vararg trip: TripEntity)

    @Delete
    suspend fun delete(trip: TripEntity)

    @Update
    suspend fun updateTrip(vararg trip: TripEntity)
}