package com.msudenver.nighttrain.rtd_rider_alerts.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class CalendarEntity(
    @PrimaryKey var id: Int,
    @ColumnInfo(name ="monday") var monday: Int,
    @ColumnInfo(name ="tuesday") var tuesday: Int,
    @ColumnInfo(name ="wednesday") var wednesday: Int,
    @ColumnInfo(name ="thursday") var thursday: Int,
    @ColumnInfo(name ="friday") var friday: Int,
    @ColumnInfo(name ="saturday") var saturday: Int,
    @ColumnInfo(name ="sunday") var sunday: Int
    )
