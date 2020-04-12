package com.msudenver.nighttrain.rtd_rider_alerts.ui

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.google.common.truth.Truth
import com.msudenver.nighttrain.rtd_rider_alerts.classes.FavoriteStation
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class FavoriteStationViewModelTest {
    @JvmField @Rule val rule = InstantTaskExecutorRule()

    @Test
    fun filterStations() {
        //val signal = CountDownLatch(1)

        val fav1 = FavoriteStation("10th & Osage", true)
        val fav3 = FavoriteStation("OS",false)
        val fav2 = FavoriteStation("Auraria West & Colfax", false)
        val fav4 = FavoriteStation("positive", true)
        val favList = listOf(fav1,fav2,fav3,fav4)

        val viewModel = FavoriteStationViewModel()
        viewModel.stationNames = favList
        viewModel.filterStations("os")
        Truth.assertThat(viewModel.filteredStationNames.value?.size).isEqualTo(3)

    }
}