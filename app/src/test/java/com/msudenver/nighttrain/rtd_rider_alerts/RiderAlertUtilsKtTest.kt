package com.msudenver.nighttrain.rtd_rider_alerts

import androidx.test.ext.junit.runners.AndroidJUnit4
import com.google.common.truth.Truth
import org.junit.Test
import org.junit.runner.RunWith
import java.text.SimpleDateFormat
import java.util.*

@RunWith(AndroidJUnit4::class)
class RiderAlertUtilsKtTest {
    private val unionStation12 = "Union Station Track 12"
    @Test
    fun testOneStationParse() {
        val alert = RiderAlertUtils.parseStationAlert("6:49 pm from Union Station Track 12 to Littleton / Mineral Ave Station")
        Truth.assertThat(alert.fromStation).isEqualTo(unionStation12)
        Truth.assertThat(alert.startTime.time).isEqualTo(67740000) //Date(70,0,1,11,49,0)) //6:49 pm in UTC time
    }

    //RTD does a funny thing where before 3:30 a.m. it is considered the previous day and the time is just > 24 hours, i.e. 24:51
    @Test
    fun testAfterMidnightStationParse() {
        val alert = RiderAlertUtils.parseStationAlert("12:51 am from Union Station Track 12 to Littleton / Mineral Ave Station")
        Truth.assertThat(alert.fromStation).isEqualTo(unionStation12)
        Truth.assertThat(alert.startTime.time).isEqualTo(89460000) //Date(70,0,1,17,51,0)) //24:51 in UTC time
    }

    @Test
    fun testEarlyMorning() {
        val alert = RiderAlertUtils.parseStationAlert("5:48 am from Evans Station to Union Station Track 11")
        Truth.assertThat(alert.fromStation).isEqualTo("Evans Station")
        Truth.assertThat(alert.startTime.time).isEqualTo(20880000)
    }

    @Test
    fun testOneStationParseError() {
        val alert = RiderAlertUtils.parseStationAlert("gibberish")
        Truth.assertThat(alert.fromStation).isEqualTo("")
        Truth.assertThat(alert.startTime.year).isEqualTo(Date().year)
    }

    @Test
    fun testAlertParse() {
        val wholeAlert = "C Line Trip 6:11 pm from Littleton / Mineral Ave Station to Union Station Track 11 and 9 other trips cancelled today due to operator shortage.<br>" +
                "<br>Affected trips:<br>6:11 pm from Littleton / Mineral Ave Station to Union Station Track 11<br>6:49 pm from Union Station Track 12 to " +
                "Littleton / Mineral Ave Station<br>7:41 pm from Littleton / Mineral Ave Station to Union Station Track 11<br>8:19 pm from Union Station Tra" +
                "ck 12 to Littleton / Mineral Ave Station<br>9:11 pm from Littleton / Mineral Ave Station to Union Station Track 11<br>9:49 pm from Union Station Track 12 to Littleton / Mineral Ave Station<br>10:41 pm from Littleton / Mineral Ave Station to Union Station Track 11<br>11:21 pm from Union Station Track 12 to Littleton / Mineral Ave Station<br>12:11 am from Littleton / Mineral Ave Station to Union Station Track 11<br>12:51 am from Union Station Track 12 to Littleton / Mineral Ave Station"
        val alerts = RiderAlertUtils.toRTDStationTimeObj(wholeAlert, "C")
        Truth.assertThat(alerts.size).isEqualTo(10)
        Truth.assertThat(alerts[0].fromStation).isEqualTo("Littleton / Mineral Ave Station")
        Truth.assertThat(alerts[0].startTime.minutes==11).isTrue()
        Truth.assertThat(alerts[9].fromStation).isEqualTo(unionStation12)
    }

    @Test
    fun getDayOfWeekAfter() {
        val simpleDateFormat = SimpleDateFormat("MMMM dd, yyyy hh:mmaa", Locale.US)
        val alertStartDate = simpleDateFormat.parse("February 14, 2020 2:26PM")
        val day = RiderAlertUtils.getDayOfWeek(alertStartDate)
        Truth.assertThat(day).isEqualTo("friday")
    }

    @Test
    fun checkSunday() {
        getDayOfWeekAfterHelper("March 8, 2020 2:26PM", "sunday")
    }

    @Test
    fun checkMonday() {
        getDayOfWeekAfterHelper("March 9, 2020 2:26PM", "monday")
    }

    @Test
    fun checkTuesday() {
        getDayOfWeekAfterHelper("March 10, 2020 2:26PM", "tuesday")
    }

    @Test
    fun checkWednesday() {
        getDayOfWeekAfterHelper("March 11, 2020 2:26PM", "wednesday")
    }

    @Test
    fun checkThursday() {
        getDayOfWeekAfterHelper("March 12, 2020 2:26PM", "thursday")
    }

    private fun getDayOfWeekAfterHelper(date: String, day: String) {
        val simpleDateFormat = SimpleDateFormat("MMMM dd, yyyy hh:mmaa", Locale.US)
        val alertStartDate = simpleDateFormat.parse(date)
        val day = RiderAlertUtils.getDayOfWeek(alertStartDate)
        Truth.assertThat(day).isEqualTo(day)
    }

    @Test
    fun testToString() {
        val now = Calendar.getInstance()
        val nowDate = now.time
        var util = RiderAlertUtils()
        util.fromStation = "AaA"
        util.routeName = "cC"
        util.startTime = now.time
        Truth.assertThat(util.toString()).isEqualTo("AaA via cC at $nowDate")
    }
}