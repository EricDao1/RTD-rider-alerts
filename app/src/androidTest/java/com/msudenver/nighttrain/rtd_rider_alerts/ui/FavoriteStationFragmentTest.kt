package com.msudenver.nighttrain.rtd_rider_alerts.ui

import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.test.annotation.UiThreadTest
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.UiController
import androidx.test.espresso.ViewAction
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.typeText
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.rule.ActivityTestRule
import androidx.test.uiautomator.UiDevice
import com.google.common.truth.Truth
import com.msudenver.nighttrain.rtd_rider_alerts.MainActivity
import com.msudenver.nighttrain.rtd_rider_alerts.R
import org.hamcrest.Matcher
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

    //https://github.com/android/android-test/blob/7e834ce37faf52f2a65a73b0a6d83ab148707cbb/testapps/ui_testapp/javatests/androidx/test/ui/app/AdapterViewTest.java
    @Test
    fun clickOnAdapterButton() {
        val device = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation())
        device.waitForIdle()
        onView(withId(R.id.add_station)).perform(click())
        device.waitForIdle()
        onView(withId(R.id.search_text)).perform(typeText("Osage"))
        device.waitForIdle()
        onView(withId(R.id.fav_station_recycler))
            .perform(RecyclerViewActions.actionOnItemAtPosition<StationPickerAdapter.ViewHolder>(0,MyViewAction.clickChildViewWithId(
                R.id.favoriteSwitch)))
            //.atPosition(0)
            //.onChildView(withId(R.id.favoriteSwitch))
            //.perform(click())
        device.waitForIdle()

    }

    //https://stackoverflow.com/questions/28476507/using-espresso-to-click-view-inside-recyclerview-item
    object MyViewAction {
        fun clickChildViewWithId(id: Int): ViewAction {
            return object : ViewAction {
                override fun getConstraints(): Matcher<View>? {
                    return null
                }

                override fun getDescription(): String {
                    return "Click on a child view with specified id."
                }

                override fun perform(uiController: UiController?, view: View) {
                    val v: View = view.findViewById(id)
                    v.performClick()
                }
            }
        }
    }
}