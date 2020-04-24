package com.msudenver.nighttrain.rtd_rider_alerts.ui

import android.content.Context
import android.widget.Spinner
import androidx.lifecycle.ViewModelProvider
import androidx.test.annotation.UiThreadTest
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.rule.ActivityTestRule
import androidx.test.uiautomator.UiDevice
import com.google.common.truth.Truth
import com.msudenver.nighttrain.rtd_rider_alerts.MainActivity
import com.msudenver.nighttrain.rtd_rider_alerts.R
import com.msudenver.nighttrain.rtd_rider_alerts.db.ScheduledTrain
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

import androidx.test.espresso.Espresso.*
import androidx.test.espresso.matcher.ViewMatchers.*

@RunWith(AndroidJUnit4::class)
class ScheduleFragmentTest {

    private val station = "16th & Stout"
    @Rule @JvmField val activityRule = ActivityTestRule(MainActivity::class.java)

    @Test
    @UiThreadTest
    fun testCreateAdapter() {
        val scheduledTrain1 = ScheduledTrain()
        val scheduledTrain2 = ScheduledTrain()
        scheduledTrain2.tripHeader = "trip222"
        scheduledTrain1.tripHeader = "trip111"

        val trainList = arrayListOf<ScheduledTrain>(scheduledTrain1, scheduledTrain2)
        Thread.sleep(250)

        val scheduleFragment = ScheduleFragment()
        activityRule.activity.addFragment(scheduleFragment)
        scheduleFragment.createAdapter(trainList)
        Truth.assertThat(scheduleFragment.recyclerView?.adapter?.itemCount).isEqualTo(2)

    }

    @Test
    fun testClickRefresh() {
        onView(withId(R.id.refresh_button)).perform(click())
    }

    @Test
    @UiThreadTest
    fun testNoClickSpinner() {

        val scheduleFragment = ScheduleFragment()
        activityRule.activity.addFragment(scheduleFragment)
        Thread.sleep(2500)
        scheduleFragment.stationsSpinner?.onItemSelectedListener?.onNothingSelected(scheduleFragment.stationsSpinner)

        Thread.sleep(1000)
        //onData(anything()).atPosition(-1).perform(ViewActions.pressBack())

    }
/*
    @Test(expected = UninitializedPropertyAccessException::class)
    fun preInitTest() {
        val scheduleFragment = ScheduleFragment()
        scheduleFragment.stationsSpinner.gravity
    }

    @Test(expected = UninitializedPropertyAccessException::class)
    fun preInitTest2() {
        val scheduleFragment = ScheduleFragment()
        scheduleFragment.recyclerView?.isAnimating
    }

    @Test(expected = UninitializedPropertyAccessException::class)
    fun preInitTest3() {
        val scheduleFragment = ScheduleFragment()
        scheduleFragment.viewModel.context
    }
*/

    @Test
    fun testCreateAdapterNull() {
        val scheduledTrain1 = ScheduledTrain()
        val scheduledTrain2 = ScheduledTrain()
        scheduledTrain2.tripHeader = "trip222"
        scheduledTrain1.tripHeader = "trip111"

        val trainList = arrayListOf<ScheduledTrain>(scheduledTrain1, scheduledTrain2)
        val scheduleFragment = ScheduleFragment()
        scheduleFragment.recyclerView = null
        scheduleFragment.createAdapter(trainList)
        Truth.assertThat(scheduleFragment.recyclerView?.adapter?.itemCount).isEqualTo(null)

    }

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
        onView(withId(R.id.stations_spinner))
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
    fun testUpdateSpinnerListNull() {
        val scheduleFragment = ScheduleFragment()
        val stationList = listOf("10th & Osage", station)
        scheduleFragment.testableContext = ApplicationProvider.getApplicationContext()
        scheduleFragment.updateSpinnerList(stationList)
        scheduleFragment.updateSelection(station)
        Truth.assertThat(scheduleFragment.stationsSpinner?.selectedItem).isEqualTo(null)
    }
    @Test
    fun testrefreshTrainsListNull() {
        val scheduleFragment = ScheduleFragment()
        scheduleFragment.viewModel = null
        scheduleFragment.refreshTrains()
    }
    @Test
    fun testOnspinerItemSelected() {
        val scheduleFragment = ScheduleFragment()
        scheduleFragment.viewModel = null
        scheduleFragment.stationsList = listOf("")
        scheduleFragment.onSpinerItemSelected(0)

    }
    @Test
    fun testUpdateSpinnerListNullEmpty() {
        val scheduleFragment = ScheduleFragment()
        val stationList = ArrayList<String>()
        scheduleFragment.testableContext = ApplicationProvider.getApplicationContext()
        scheduleFragment.updateSpinnerList(stationList)
        scheduleFragment.updateSelection(station)
        Truth.assertThat(scheduleFragment.stationsSpinner?.selectedItem).isEqualTo(null)
    }

    @Test
    fun testNullRecyclerView() {
        val scheduleFragment = ScheduleFragment()
        scheduleFragment.recyclerView = null
        scheduleFragment.initializeRecyclerView()
    }

    @Test
    fun testNullSpinner() {
        val scheduleFragment = ScheduleFragment()
        scheduleFragment.stationsSpinner=null
        scheduleFragment.initializeStationSpinner()
    }

    @Test
    fun testNullViewModel() {
        val scheduleFragment = ScheduleFragment()
        scheduleFragment.initializeRecyclerView()
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