package com.msudenver.nighttrain.rtd_rider_alerts.ui

import android.content.Context
import android.widget.Spinner
import androidx.lifecycle.ViewModelProvider
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.rule.ActivityTestRule
import androidx.test.uiautomator.UiDevice
import com.google.common.truth.Truth
import com.msudenver.nighttrain.rtd_rider_alerts.MainActivity
import com.msudenver.nighttrain.rtd_rider_alerts.R
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class ScheduleFragmentTest {

    val station = "16th & Stout"
    @Rule @JvmField val activityRule = ActivityTestRule(MainActivity::class.java)

    @Test
    fun testRotate() {
        val viewModel = ViewModelProvider(activityRule.activity).get(TrainScheduleViewModel::class.java)
        val device = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation())
        device.waitForIdle()
        Thread.sleep(3000)
        viewModel.stationNames.postValue(listOf("10th & Osagio", station))
        viewModel.stationSelected.postValue(station)
        device.setOrientationRight()

        Thread.sleep(3000)
        Espresso.onView(ViewMatchers.withId(R.id.stations_spinner))
            .check(ViewAssertions.matches(ViewMatchers.withSpinnerText(station)))
    }

    @Test
    fun testUpdateSpinnerList() {
        val scheduleFragment = ScheduleFragment()
        val spinner = Spinner(ApplicationProvider.getApplicationContext<Context>())
        val stationList = listOf("10th & Osage", station)
        scheduleFragment.stationsSpinner = spinner
        scheduleFragment.testableContext = ApplicationProvider.getApplicationContext()
        scheduleFragment.updateSpinnerList(stationList)
        scheduleFragment.updateSelection(station)
        Truth.assertThat(scheduleFragment.stationsSpinner?.selectedItem).isEqualTo(station)
    }

    @Test
    fun testSetStationList() {
        val scheduleFragment = ScheduleFragment()
        val spinner = Spinner(ApplicationProvider.getApplicationContext<Context>())
        val stationList = listOf("10th & Osage", station)
        scheduleFragment.stationsSpinner = spinner
        scheduleFragment.testableContext = ApplicationProvider.getApplicationContext()
        scheduleFragment.updateSpinnerList(stationList)
        Truth.assertThat(scheduleFragment.stationsSpinner?.adapter?.getItem(1)).isEqualTo(station)
    }

    @Test
    fun testViewFavoriteStations() {

        val device = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation())
        device.waitForIdle()
        val viewModel = ViewModelProvider(activityRule.activity).get(InterfaceViewModel::class.java)
        onView(withId(R.id.add_station)).perform(click())
        device.waitForIdle()

        Truth.assertThat(viewModel.showStations.value).isTrue()

        //click other button
        onView(withId(R.id.back_schedule_button)).perform(click())
        device.waitForIdle()

        Truth.assertThat(viewModel.showStations.value).isFalse()
        //confirm fragment is Schedule fragment
    }
}