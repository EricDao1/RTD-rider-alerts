package com.msudenver.nighttrain.rtd_rider_alerts.db

import androidx.room.Dao
import androidx.room.Query
import com.msudenver.nighttrain.rtd_rider_alerts.classes.FavoriteStation

@Dao
interface FavoriteStationDao {
    @Query("SELECT stop_name " +
            "FROM FavoriteStationEntity " +
            "INNER JOIN StopEntity on StopEntity.id == stop_id " +
            "WHERE is_favorite=1")
    fun getFavoriteStations() : List<String>

    @Query("SELECT FavoriteStationEntity.id, stop_name AS stationName, is_favorite AS isFavorite " +
            "FROM FavoriteStationEntity " +
            "INNER JOIN StopEntity on StopEntity.id == stop_id")
    fun getAllStations() : List<FavoriteStation>

    @Query("UPDATE FavoriteStationEntity SET is_favorite= :isFav WHERE id= :id")
    fun updateStationFavorite(id: Int, isFav: Boolean)
}
