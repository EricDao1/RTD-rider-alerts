package com.msudenver.nighttrain.rtd_rider_alerts.db

import androidx.room.Dao
import androidx.room.Query
import com.msudenver.nighttrain.rtd_rider_alerts.classes.FavoriteStation

@Dao
interface FavoriteStationDao {
    @Query("SELECT stop_name " +
            "FROM FavoriteStationEntity " +
            "INNER JOIN StopEntity on StopEntity.id == stop_id" +
            "WHERE isFavorite=1")
    fun getFavoriteStations() : List<String>

    @Query("SELECT id, stop_name AS stationName, is_favorite AS isFavorite " +
            "INNER JOIN StopEntity on StopEntity.id == stop_id" +
            "FROM FavoriteStationEntity")
    fun getAllStations() : List<FavoriteStation>

    @Query("UPDATE FavoriteStationEntity SET is_favorite= :isFav WHERE id= :id")
    fun updateStationFavorite(id: Int, isFav: Boolean)
}
