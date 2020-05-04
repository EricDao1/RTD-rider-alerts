package com.msudenver.nighttrain.rtd_rider_alerts.db


import android.content.Context
import android.database.Cursor
import androidx.annotation.NonNull
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

//https://gabrieltanner.org/blog/android-room
//https://developer.android.com/reference/androidx/room/package-summary.html

@Database(
    entities = [CalendarEntity::class, RouteEntity::class,
        StopEntity::class, StopTimeEntity::class, TripEntity::class, CancelledTripEntity::class,
        FavoriteStationEntity::class],
    version = 2
)
@TypeConverters(TimeConverters::class)
abstract class RTDDatabase : RoomDatabase() {
    abstract fun calendarDao(): CalendarDao
    abstract fun routeDao(): RouteDao
    abstract fun stopDao(): StopDao
    abstract fun stopTimeDao(): StopTimeDao
    abstract fun tripDao(): TripDao
    abstract fun cancelledTripDao(): CancelledTripDao
    abstract fun favoriteStationDao(): FavoriteStationDao

    companion object {
        @Volatile private var instance: RTDDatabase? = null
        private val LOCK = Any()
        private val MIGRATION_1_2 = object : Migration(1,2) {
            override fun migrate(database: SupportSQLiteDatabase) {
                database.execSQL("CREATE TABLE `FavoriteStationEntity` (" +
                        "`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `stop_id` INTEGER NOT NULL, `is_favorite` INTEGER NOT NULL," +
                        " FOREIGN KEY(`stop_id`) REFERENCES `StopEntity`(`id`) ON UPDATE NO ACTION ON DELETE CASCADE)")
                database.execSQL("INSERT INTO FavoriteStationEntity " +
                                "SELECT id as id, id as stop_id, 0 as is_favorite FROM StopEntity WHERE parent_station=0")
            }
        }

        operator fun invoke(context: Context)= instance ?: synchronized(LOCK){
            instance ?: buildDatabase(context).also {instance = it} }



        private fun buildDatabase(context: Context) = Room.databaseBuilder(context,
            RTDDatabase::class.java, "rtd.db")
            .createFromAsset("rtd.db")
            .addMigrations(MIGRATION_1_2)
            .build()
    }
}