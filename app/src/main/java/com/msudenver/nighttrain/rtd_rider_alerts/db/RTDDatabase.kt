package com.msudenver.nighttrain.rtd_rider_alerts.db


import android.content.Context
import androidx.annotation.NonNull
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

//https://gabrieltanner.org/blog/android-room
//https://developer.android.com/reference/androidx/room/package-summary.html

@Database(
    entities = [CalendarEntity::class, RouteEntity::class, StopEntity::class, StopTimeEntity::class, TripEntity::class, CancelledTripEntity::class],
    version = 1
)
@TypeConverters(TimeConverters::class)
abstract class RTDDatabase : RoomDatabase() {
    abstract fun calendarDao(): CalendarDao
    abstract fun routeDao(): RouteDao
    abstract fun stopDao(): StopDao
    abstract fun stopTimeDao(): StopTimeDao
    abstract fun tripDao(): TripDao
    abstract fun cancelledTripDao(): CancelledTripDao

    companion object {
        @Volatile private var instance: RTDDatabase? = null
        private val LOCK = Any()

        operator fun invoke(context: Context)= instance ?: synchronized(LOCK){
            instance ?: buildDatabase(context).also {instance = it}
        }

        private fun buildDatabase(context: Context) = Room.databaseBuilder(context,
            RTDDatabase::class.java, "rtd.db")
            .createFromAsset("rtd.db")
            .build()
    }
}