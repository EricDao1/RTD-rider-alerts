package com.msudenver.nighttrain.rtd_rider_alerts.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    foreignKeys=[
        ForeignKey(entity=StopEntity::class,
            parentColumns = arrayOf("id"),
            childColumns = arrayOf("stop_id"),
            onDelete = ForeignKey.CASCADE
        )
    ]
)
class FavoriteStationEntity(
    @PrimaryKey(autoGenerate = true) var id: Int = 0,
    @ColumnInfo(name="stop_id") var stopIDForeignKey: Int =0,
    @ColumnInfo(name="is_favorite") var isFavorite: Boolean = false
)