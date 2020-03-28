package com.msudenver.nighttrain.rtd_rider_alerts

import android.content.Context
import android.content.Intent
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.rule.ServiceTestRule
import org.junit.Test

import org.junit.runner.RunWith
import java.util.concurrent.TimeUnit

@RunWith(AndroidJUnit4::class)
class RiderAlertServiceInsTest {

    private val serviceRule : ServiceTestRule = ServiceTestRule.withTimeout(60L, TimeUnit.SECONDS)

    @Test
    fun testWithBoundService() {
        val serviceIntent = Intent(ApplicationProvider.getApplicationContext<Context>(),
            RiderAlertService::class.java)
        serviceRule.startService(serviceIntent)
    }
}