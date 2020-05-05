package com.msudenver.nighttrain.rtd_rider_alerts.db

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.google.common.truth.Truth
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class FavoriteStationEntityTest {

    private lateinit var db: RTDDatabase
    private val context: Context = ApplicationProvider.getApplicationContext()

    private lateinit var favoriteDao: FavoriteStationDao
    private lateinit var stopDao: StopDao


    @Before
    fun setUp() {

        db = Room.inMemoryDatabaseBuilder(
            context, RTDDatabase::class.java
        ).allowMainThreadQueries().build()
        favoriteDao = db.favoriteStationDao()
        stopDao = db.stopDao()

        val stop10 = StopEntity(10,"station 10","37", "38", "stations",0)
        val stop11 = StopEntity(11,"station 11","37", "38", "stations",0)
        val stop12 = StopEntity(12,"station 12","37", "38", "stations",0)
        val stop13 = StopEntity(13,"station 13","37", "38", "stations",0)
        stopDao.insertAll(stop10,stop11,stop12,stop13)

        val fav1 = FavoriteStationEntity(1, 10, true)
        val fav2 = FavoriteStationEntity(2, 11, false)
        val fav3 = FavoriteStationEntity(3, 12, true)
        favoriteDao.insertAll(fav1,fav2,fav3)
    }

    @Test
    fun testGetFavorites() {
        Truth.assertThat(favoriteDao.getFavoriteStations().size).isEqualTo(2)
    }

    @Test
    fun testGetAllFavorites() {
        Truth.assertThat(favoriteDao.getAllStations().size).isEqualTo(3)
    }
}