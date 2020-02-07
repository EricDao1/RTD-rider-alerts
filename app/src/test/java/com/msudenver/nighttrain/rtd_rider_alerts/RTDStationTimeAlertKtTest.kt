package com.msudenver.nighttrain.rtd_rider_alerts

import androidx.test.ext.junit.runners.AndroidJUnit4
import com.google.common.truth.Truth
import org.junit.Assert.*
import org.junit.Test
import org.junit.runner.RunWith
import java.util.*

@RunWith(AndroidJUnit4::class)
class RTDStationTimeAlertKtTest {
    @Test
    fun testOneStationParse() {
        val alert = RTDStationTimeAlert.parseStationAlert("6:49 pm from Union Station Track 12 to Littleton / Mineral Ave Station")
        Truth.assertThat(alert.fromStation).isEqualTo("Union Station")
        Truth.assertThat(alert.startTime).isEqualTo(Date(70,0,1,18,49,0))
    }

    @Test
    fun testOneStationParseError() {
        val alert = RTDStationTimeAlert.parseStationAlert("gibberish")
        Truth.assertThat(alert.fromStation).isEqualTo("")
        Truth.assertThat(alert.startTime.year).isEqualTo(Date().year)
    }

    @Test
    fun testAlertParse() {
        val wholeAlert = "C Line Trip 6:11 pm from Littleton / Mineral Ave Station to Union Station Track 11 and 9 other trips cancelled today due to operator shortage.<br>" +
                "<br>Affected trips:<br>6:11 pm from Littleton / Mineral Ave Station to Union Station Track 11<br>6:49 pm from Union Station Track 12 to " +
                "Littleton / Mineral Ave Station<br>7:41 pm from Littleton / Mineral Ave Station to Union Station Track 11<br>8:19 pm from Union Station Tra" +
                "ck 12 to Littleton / Mineral Ave Station<br>9:11 pm from Littleton / Mineral Ave Station to Union Station Track 11<br>9:49 pm from Union Station Track 12 to Littleton / Mineral Ave Station<br>10:41 pm from Littleton / Mineral Ave Station to Union Station Track 11<br>11:21 pm from Union Station Track 12 to Littleton / Mineral Ave Station<br>12:11 am from Littleton / Mineral Ave Station to Union Station Track 11<br>12:51 am from Union Station Track 12 to Littleton / Mineral Ave Station"
        val alerts = RTDStationTimeAlert.toRTDStationTime(wholeAlert, "C")
        Truth.assertThat(alerts.size).isEqualTo(10)
        Truth.assertThat(alerts[0].fromStation).isEqualTo("Littleton / Mineral Ave Station")
        Truth.assertThat(alerts[0].startTime.minutes==11).isTrue()
        Truth.assertThat(alerts[9].fromStation).isEqualTo("Union Station")
    }
}