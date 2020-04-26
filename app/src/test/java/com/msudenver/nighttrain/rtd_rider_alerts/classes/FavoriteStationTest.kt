package com.msudenver.nighttrain.rtd_rider_alerts.classes

import com.google.common.truth.Truth
import org.junit.Test

class FavoriteStationTest {

    @Test
    fun testGetSetID() {
        val favoriteStation : FavoriteStation = FavoriteStation(1, "abc", true)
        favoriteStation.id = 2
        Truth.assertThat(favoriteStation.id).isEqualTo(2)
    }
}