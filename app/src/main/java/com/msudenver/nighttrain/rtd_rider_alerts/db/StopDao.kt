package com.msudenver.nighttrain.rtd_rider_alerts.db

import androidx.room.*

@Dao
interface StopDao {
    @Query("SELECT * FROM stopentity WHERE parent_station > 0")
    fun getTrainStops(): List<StopEntity>

    @Insert
    fun insertAll(vararg stop: StopEntity)

    @Delete
    fun delete(stop: StopEntity)

    @Update
    fun updateStop(vararg stops: StopEntity)
}