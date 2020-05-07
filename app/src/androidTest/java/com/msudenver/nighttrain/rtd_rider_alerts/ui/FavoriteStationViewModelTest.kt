package com.msudenver.nighttrain.rtd_rider_alerts.ui

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.ViewModelProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.rule.ActivityTestRule
import com.google.common.truth.Truth
import com.msudenver.nighttrain.rtd_rider_alerts.MainActivity
import com.msudenver.nighttrain.rtd_rider_alerts.classes.FavoriteStation
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class FavoriteStationViewModelTest {
    @JvmField @Rule val rule = InstantTaskExecutorRule()
    @Rule @JvmField val activityRule = ActivityTestRule(MainActivity::class.java)

    @Test
    fun filterStations() {

        val fav1 = FavoriteStation(0,"10th & Osage", true)
        val fav3 = FavoriteStation(1,"OS",false)
        val fav2 = FavoriteStation(2,"Auraria West & Colfax", false)
        val fav4 = FavoriteStation(3,"positive", true)
        val favList = listOf(fav1,fav2,fav3,fav4)

        val viewModel = ViewModelProvider(activityRule.activity).get(TrainScheduleViewModel::class.java)
        viewModel.allStationNames = favList
        viewModel.filterStations("os")
        Truth.assertThat(viewModel.filteredStationNames.value?.size).isEqualTo(3)

    }
}