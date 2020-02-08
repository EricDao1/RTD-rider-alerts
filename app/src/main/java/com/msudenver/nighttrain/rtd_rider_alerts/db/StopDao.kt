package com.msudenver.nighttrain.rtd_rider_alerts.db

import androidx.room.*

@Dao
interface StopDao {
    @Query("SELECT * FROM stopentity WHERE parent_station > 0")
    suspend fun getTrainStops(): List<StopEntity>

    @Insert
    suspend fun insertAll(vararg stop: StopEntity)

    @Delete
    suspend fun delete(stop: StopEntity)

    @Update
    suspend fun updateStop(vararg stops: StopEntity)
}