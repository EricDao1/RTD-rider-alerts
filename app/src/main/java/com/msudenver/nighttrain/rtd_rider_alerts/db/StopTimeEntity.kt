package com.msudenver.nighttrain.rtd_rider_alerts.db

import androidx.room.*
import java.util.*

@Entity(foreignKeys = [
    ForeignKey(entity = StopEntity::class,
        parentColumns = arrayOf("id"),
        childColumns = arrayOf("stop_id"),
        onDelete = ForeignKey.CASCADE),
    ForeignKey(entity = TripEntity::class,
        parentColumns = arrayOf("id"),
        childColumns = arrayOf("trip_id"),
        onDelete = ForeignKey.CASCADE)
    ]
)
data class StopTimeEntity(
    @PrimaryKey(autoGenerate = true) var id: Int=0,
    @TypeConverters(TimeConverters::class)
    @ColumnInfo(name ="arrival_time")
    var arrivalTime: Date,
    @ColumnInfo(name = "departure_time")
    @TypeConverters(TimeConverters::class)
    var departureTime: Date,
    @ColumnInfo(name = "stop_id") var stopId: Int,
    @ColumnInfo(name = "trip_id") var tripId: Int,
    @ColumnInfo(name = "stop_sequence") var stopSequence: Int
    )

class TimeConverters {
    @TypeConverter
    fun fromTimestamp(value: Long): Date {
        return Date(value)
    }

    @TypeConverter
    fun dateToTimestamp(date: Date): Long {
        return date.time
    }
}