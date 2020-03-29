package com.msudenver.nighttrain.rtd_rider_alerts

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry.getInstrumentation
import androidx.test.uiautomator.UiDevice
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class TestMainActivity {

    @get:Rule
    val activityScenarioRule : ActivityScenarioRule<MainActivity> =
        ActivityScenarioRule(MainActivity::class.java)
/*
    @Test
    fun selectStation() {
        onView(ViewMatchers.withId(R.id.stations_spinner))
            .perform(click())
    }*/

    @Test
    fun onRotateKeepValues() {
        val station = "16th & Stout Station"
        val device = UiDevice.getInstance(getInstrumentation())
        device.waitForIdle()
        Thread.sleep(3000)
        device.waitForIdle()
        onView(ViewMatchers.withId(R.id.stations_spinner))
            .perform(click())
        onView(withText(station))
            .perform(click())

        device.setOrientationRight()
        device.waitForIdle()
        Thread.sleep(3000)
        onView(ViewMatchers.withId(R.id.stations_spinner))
            .check(matches(withSpinnerText(station)))
    }

}