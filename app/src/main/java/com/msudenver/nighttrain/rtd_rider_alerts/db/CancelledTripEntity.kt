package com.msudenver.nighttrain.rtd_rider_alerts.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import java.util.*

@Entity(
    foreignKeys=[
        ForeignKey(entity=TripEntity::class,
            parentColumns = arrayOf("id"),
            childColumns = arrayOf("trip_id"),
            onDelete = ForeignKey.CASCADE
        )
    ]
)

class CancelledTripEntity(
    @PrimaryKey(autoGenerate = true) var id: Int=0,
    @ColumnInfo(name="trip_id") var tripId: Int,
    @ColumnInfo(name="day_date") var dayDate: Date= Date()
)