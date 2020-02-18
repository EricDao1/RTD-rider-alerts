package com.msudenver.nighttrain.rtd_rider_alerts.db

import androidx.room.*
import java.util.*

@Dao
interface CancelledTripDao {

    @Query("DELETE FROM cancelledtripentity WHERE id NOT IN (SELECT id FROM cancelledtripentity GROUP BY trip_id, day_date)")
    fun deleteDuplicateAlerts() : Int

    @Query("SELECT * FROM cancelledtripentity WHERE day_date= :date")
    fun getTrainsForToday(date: Date) : List<CancelledTripEntity>

    @Insert
    fun insertAll(vararg trip: CancelledTripEntity)

}