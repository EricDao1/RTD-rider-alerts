package com.msudenver.nighttrain.rtd_rider_alerts.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import java.util.*

@Dao
interface CancelledTripDao {
    @Query("SELECT * FROM cancelledtripentity WHERE day_date= :date")
    suspend fun getTrainsForToday(date: Date) : List<CancelledTripEntity>

    @Insert
    suspend fun insertAll(vararg trip: CancelledTripEntity)

    @Update
    suspend fun updateAll(vararg trip: CancelledTripEntity)

}