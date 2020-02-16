package com.msudenver.nighttrain.rtd_rider_alerts.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface RouteDao {
    @Query("SELECT * FROM routeentity WHERE route_type=0 or route_type=2")
    fun getTrainRoutes(): List<RouteEntity>

    @Insert
    fun insertAll(vararg route: RouteEntity)
}