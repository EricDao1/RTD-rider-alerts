package com.msudenver.nighttrain.rtd_rider_alerts.ui

import androidx.lifecycle.ViewModelProvider
import androidx.test.annotation.UiThreadTest
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.typeText
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.rule.ActivityTestRule
import androidx.test.uiautomator.UiDevice
import com.google.common.truth.Truth
import com.msudenver.nighttrain.rtd_rider_alerts.MainActivity
import com.msudenver.nighttrain.rtd_rider_alerts.R
import org.junit.Rule
import org.junit.Test

class FavoriteStationFragmentTest {

    @Rule @JvmField val activityRule = ActivityTestRule(MainActivity::class.java)

    @Test
    fun testInitializeRecyclerView() { // just make sure it doesn't crash :)
        val favoriteStationFragment = FavoriteStationFragment()
        favoriteStationFragment.stationRecyclerView = null
        favoriteStationFragment.initializeRecyclerView()
    }

    @Test
    fun testNullSearchText() {
        val favoriteStationFragment = FavoriteStationFragment()
        favoriteStationFragment.searchText = null
        val viewModel = ViewModelProvider(activityRule.activity).get(TrainScheduleViewModel::class.java)
        favoriteStationFragment.initializeSearchText(viewModel)
    }

    @Test
    fun testFilterTextEmpty() {
        val text = "hello"
        val favoriteStationFragment = FavoriteStationFragment()
        val results = favoriteStationFragment.getFilterText(text,2)
        Truth.assertThat(results).isEqualTo("")
    }

    @Test
    fun testFilterText() {
        val text = "hello"
        val favoriteStationFragment = FavoriteStationFragment()
        val results = favoriteStationFragment.getFilterText(text,3)
        Truth.assertThat(results).isEqualTo(text)
    }

    @Test
    @UiThreadTest
    fun updateNullAdapter() {
        val favStationFragment = FavoriteStationFragment()
        favStationFragment.stationRecyclerView = null
        val viewModel = ViewModelProvider(activityRule.activity).get(TrainScheduleViewModel::class.java)
        favStationFragment.updateAdapter(listOf(), viewModel)
    }

    @Test
    fun testOnTextChanged() {
        val device = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation())
        device.waitForIdle()
        onView(withId(R.id.add_station)).perform(click())
        device.waitForIdle()
        onView(withId(R.id.search_text)).perform(typeText("help"))
    }
}