package com.msudenver.nighttrain.rtd_rider_alerts.db

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.google.common.truth.Truth
import org.junit.Assert.*
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class RTDDatabaseTest {
    @Test
    fun testDoubleInvoke() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        val db = RTDDatabase.invoke(context)
        val db2 = RTDDatabase.invoke(context)
        Truth.assertThat(db).isEqualTo(db2)
    }
}