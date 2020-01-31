package com.msudenver.nighttrain.rtd_rider_alerts.db


import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

//https://gabrieltanner.org/blog/android-room
//https://developer.android.com/reference/androidx/room/package-summary.html

@Database(
    entities = [CalendarEntity::class, RouteEntity::class, StopEntity::class, StopTimeEntity::class, TripEntity::class],
    version = 1
)
@TypeConverters(TimeConverters::class)
abstract class RTDDatabase : RoomDatabase() {
    abstract fun calendarDao(): CalendarDao
    abstract fun routeDao(): RouteDao
    abstract fun stopDao(): StopDao
    abstract fun stopTimeDao(): StopTimeDao
    abstract fun tripDao(): TripDao
}