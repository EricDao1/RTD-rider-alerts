package com.msudenver.nighttrain.rtd_rider_alerts.db

import androidx.room.*

@Dao
interface StopDao {
    @Query("SELECT DISTINCT stop_name FROM StopEntity " +
            "INNER JOIN StopTimeEntity on StopEntity.id = StopTimeEntity.stop_id " +
            "INNER JOIN TripEntity on trip_id = TripEntity.id " +
            "INNER JOIN RouteEntity on route_id = RouteEntity.id " +
            "where route_type = 0 OR route_type = 2 " +
            "order by stop_name")
    fun getTrainStops(): List<String>

    @Insert
    fun insertAll(vararg stop: StopEntity)

    @Delete
    fun delete(stop: StopEntity)

    @Update
    fun updateStop(vararg stops: StopEntity)
}