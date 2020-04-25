package com.msudenver.nighttrain.rtd_rider_alerts

import android.graphics.Color
import com.google.common.truth.Truth
import com.msudenver.nighttrain.rtd_rider_alerts.db.ScheduledTrain
import org.junit.Assert.*
import org.junit.Test
import java.util.*

class DisplayUtilsTest {
    @Test
    fun testGetCancelDisplay() {
        val scheduledTrain = ScheduledTrain()
        scheduledTrain.cancelledAlert = 1
        Truth.assertThat(DisplayUtils.getTrainTime(scheduledTrain)).isEqualTo("CANCELLED")
    }

    @Test
    fun testGetTimeDisplay() {
        val scheduledTrain = ScheduledTrain()
        scheduledTrain.cancelledAlert = 0
        val cal = Calendar.getInstance()
        cal.set(70,1,1,15,11)
        scheduledTrain.time = cal.time
        Truth.assertThat(DisplayUtils.getTrainTime(scheduledTrain)).isEqualTo("10:11 PM")
    }

    @Test
    fun testGetCancelledColor() {
        val scheduledTrain = ScheduledTrain()
        scheduledTrain.cancelledAlert = 1
        Truth.assertThat(DisplayUtils.getTimeColor(scheduledTrain)).isEqualTo(Color.RED)
    }

    @Test
    fun testGetRegularColor() {
        val scheduledTrain = ScheduledTrain()
        scheduledTrain.cancelledAlert = 0
        Truth.assertThat(DisplayUtils.getTimeColor(scheduledTrain)).isEqualTo(Color.BLACK)
    }
}