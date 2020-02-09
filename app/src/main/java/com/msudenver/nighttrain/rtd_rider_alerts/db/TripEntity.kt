package com.msudenver.nighttrain.rtd_rider_alerts.db

import androidx.room.*

@Entity(foreignKeys = [
    ForeignKey(entity = RouteEntity::class,
        parentColumns = arrayOf("id"),
        childColumns = arrayOf("route_id"),
        onDelete = ForeignKey.CASCADE),
    ForeignKey(entity = CalendarEntity::class,
        parentColumns = arrayOf("id"),
        childColumns = arrayOf("service_id"),
        onDelete = ForeignKey.CASCADE)
    ],
    indices = [
        Index(value =["id"],
            name = "index_trip_id")
    ]
)
data class TripEntity(
    @PrimaryKey var id: Int,
    @ColumnInfo(name ="trip_headsign") var description: String,
    @ColumnInfo(name ="route_id") var routeId: String,
    @ColumnInfo(name ="service_id") var serviceId: String,
    @ColumnInfo(name ="direction_id") var directionId: Int
)