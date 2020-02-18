package com.msudenver.nighttrain.rtd_rider_alerts.db

import androidx.room.*
import java.util.*

@Dao
interface StopTimeDao {
    @Query("SELECT * FROM stoptimeentity WHERE trip_id= :trip")
    fun findByTripID(trip: Int): List<StopTimeEntity>

    //get next scheduled trains for departure at specific stop (note that a station may have multiple stops, see parent_station col in stopentity table
    @Query("SELECT trip_headsign as tripHeader, route_short_name AS trainName, departure_time AS time, " +
            "route_color AS routeColor, route_text_color AS routeTextColor" +
            " FROM stoptimeentity " +
            "INNER JOIN tripentity ON trip_id=tripentity.id " +
            "INNER JOIN calendarentity ON service_id=calendarentity.id " +
            "INNER JOIN routeentity ON route_id=routeentity.id " +
            "INNER JOIN stopentity ON stop_id=stopentity.id " +
            "WHERE arrival_time >= :time " +
            " AND (CASE :dayOfWeek WHEN 'sunday' THEN sunday WHEN 'monday' THEN monday " +
            "WHEN 'tuesday' THEN tuesday WHEN 'wednesday' THEN wednesday WHEN 'thursday' THEN thursday WHEN 'friday' THEN friday WHEN 'saturday' THEN saturday END) = 1" +
            " AND stop_name = :station " +
            "ORDER BY departure_time ASC " +
            "LIMIT :maxResults")
    fun getNextTrains(time: Date, dayOfWeek: String, station: String, maxResults: Int) : List<ScheduledTrain>

    @Query("SELECT trip_id FROM stoptimeentity " +
            "INNER JOIN tripentity ON trip_id=tripentity.id " +
            "INNER JOIN routeentity ON route_id=routeentity.id" +
            " INNER JOIN stopentity ON stop_id=stopentity.id" +
            " INNER JOIN calendarentity ON service_id=calendarentity.id" +
            " WHERE route_short_name = :routeName" +
            " AND arrival_time >= :startTime" +
            " AND arrival_time <= :endTime" +
            " AND (CASE :dayOfWeek WHEN 'sunday' THEN sunday WHEN 'monday' THEN monday " +
            "WHEN 'tuesday' THEN tuesday WHEN 'wednesday' THEN wednesday WHEN 'thursday' THEN thursday WHEN 'friday' THEN friday WHEN 'saturday' THEN saturday END) = 1" +
            " AND stop_name = :startStation" +
            " LIMIT 1"
    )
    fun getCancelledTrip(dayOfWeek:String, startTime:Date, endTime:Date, routeName: String, startStation: String) : Int //Ridgegate Parkway Station

    @Insert
    fun insertAll(vararg stopTime: StopTimeEntity)

    @Delete
    fun delete(stopTime: StopTimeEntity)

    @Update
    fun updateStopTime(vararg stopTime: StopTimeEntity)
}


class ScheduledTrain {
    var trainName: String = ""
    var tripHeader: String = ""
    var time: Date = Date()
    var routeColor: Int = 0
    var routeTextColor: Int = 0
}