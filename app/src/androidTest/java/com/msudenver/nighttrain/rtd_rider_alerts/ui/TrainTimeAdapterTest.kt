package com.msudenver.nighttrain.rtd_rider_alerts.ui

import androidx.test.ext.junit.runners.AndroidJUnit4
import com.google.common.truth.Truth
import com.msudenver.nighttrain.rtd_rider_alerts.db.ScheduledTrain
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class TrainTimeAdapterTest {
    @Test
    fun checkSize() {
        val scheduledTrain1 = ScheduledTrain()
        val scheduledTrain2 = ScheduledTrain()
        val adapter = TrainTimeAdapter(listOf(scheduledTrain1,scheduledTrain2))
        Truth.assertThat(adapter.itemCount).isEqualTo(2)
    }
}