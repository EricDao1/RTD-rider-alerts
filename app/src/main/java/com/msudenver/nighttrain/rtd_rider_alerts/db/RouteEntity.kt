package com.msudenver.nighttrain.rtd_rider_alerts.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class RouteEntity(
    @PrimaryKey var id: String,
    @ColumnInfo(name = "route_short_name") var name: String,
    @ColumnInfo(name = "route_type") var type: Int,
    @ColumnInfo(name = "route_color") var backgroundColor: Int,
    @ColumnInfo(name = "route_text_color") var textColor: Int
)