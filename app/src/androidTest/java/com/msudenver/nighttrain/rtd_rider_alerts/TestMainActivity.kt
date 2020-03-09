package com.msudenver.nighttrain.rtd_rider_alerts

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class TestMainActivity {

    @get:Rule
    val activityScenarioRule : ActivityScenarioRule<MainActivity> =
        ActivityScenarioRule(MainActivity::class.java)

    @Test
    fun selectStation() {
        onView(ViewMatchers.withId(R.id.stations_spinner))
            .perform(click())
        /*onView(withText("Osage"))
            .perform(click())
        onView(ViewMatchers.withId(R.id.recycler_view))*/
    }

    @Test
    fun onRotateKeepValues() {

    }

}