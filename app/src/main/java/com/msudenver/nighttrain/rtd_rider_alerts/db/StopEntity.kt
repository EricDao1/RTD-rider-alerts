package com.msudenver.nighttrain.rtd_rider_alerts.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class StopEntity(
    @PrimaryKey var id: Int,
    @ColumnInfo(name = "stop_name") var name: String,
    @ColumnInfo(name = "stop_lat") var lat: String,
    @ColumnInfo(name = "stop_lon") var lon: String,
    @ColumnInfo(name = "parent_station") var parentStation: Int
)