package com.msudenver.nighttrain.rtd_rider_alerts.ui

import android.content.Context
import android.widget.Spinner
import androidx.lifecycle.MutableLiveData
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.rule.ActivityTestRule
import androidx.test.uiautomator.UiDevice
import com.google.common.truth.Truth
import com.msudenver.nighttrain.rtd_rider_alerts.MainActivity
import com.msudenver.nighttrain.rtd_rider_alerts.R
import com.msudenver.nighttrain.rtd_rider_alerts.TrainScheduleViewModel
import com.msudenver.nighttrain.rtd_rider_alerts.db.ScheduledTrain
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class ScheduleFragmentTest {

    @Rule @JvmField val activityRule = ActivityTestRule(MainActivity::class.java)

    @Test
    fun testRotate() {
        val station = "16th & Stout"
        activityRule.activity.theFragment.viewModel.stationNames.postValue(listOf("10th & Osagio", station))
        activityRule.activity.theFragment.viewModel.stationSelected.postValue(station)
        val device = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation())
        device.waitForIdle()
        Thread.sleep(3000)
        activityRule.activity.theFragment.viewModel.stationNames.postValue(listOf("10th & Osagio", station))
        activityRule.activity.theFragment.viewModel.stationSelected.postValue(station)
        device.setOrientationRight()

        Thread.sleep(3000)
        Espresso.onView(ViewMatchers.withId(R.id.stations_spinner))
            .check(ViewAssertions.matches(ViewMatchers.withSpinnerText(station)))
    }

    @Test
    fun testUpdateSpinnerList() {
        val scheduleFragment = ScheduleFragment()
        val spinner = Spinner(ApplicationProvider.getApplicationContext<Context>())
        val testStation = "16th & Stout"
        val stationList = listOf("10th & Osage", testStation)
        scheduleFragment.stationsSpinner = spinner
        scheduleFragment.testableContext = ApplicationProvider.getApplicationContext()
        scheduleFragment.updateSpinnerList(stationList)
        scheduleFragment.updateSelection(testStation)
        Truth.assertThat(scheduleFragment.stationsSpinner?.selectedItem).isEqualTo(testStation)
    }

    @Test
    fun testSetStationList() {
        val scheduleFragment = ScheduleFragment()
        val spinner = Spinner(ApplicationProvider.getApplicationContext<Context>())
        val testStation = "16th & Stout"
        val stationList = listOf("10th & Osage", testStation)
        scheduleFragment.stationsSpinner = spinner
        scheduleFragment.testableContext = ApplicationProvider.getApplicationContext()
        scheduleFragment.updateSpinnerList(stationList)
        Truth.assertThat(scheduleFragment.stationsSpinner?.adapter?.getItem(1)).isEqualTo(testStation)
    }
}