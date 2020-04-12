package com.msudenver.nighttrain.rtd_rider_alerts

import com.google.common.truth.Truth
import com.msudenver.nighttrain.rtd_rider_alerts.classes.RTDAlert
import com.msudenver.nighttrain.rtd_rider_alerts.classes.RTDAlertAttribute
import com.msudenver.nighttrain.rtd_rider_alerts.classes.RTDAlertData
import com.msudenver.nighttrain.rtd_rider_alerts.classes.RiderAlerts
import org.junit.Test

import kotlin.collections.ArrayList

class RTDAlertAttributeTest {

    @Test
    fun testInputOutput() {
        val riderAlert =
            RTDAlert(
                "abc",
                "xyz",
                "bbb",
                "ppp"
            )
        val alerts = ArrayList<RTDAlert>()

        alerts.add(riderAlert)
        val routeID = "dkfjkd"
        val rtdAlert =
            RTDAlertAttribute(
                routeID, "kjfldjfl", "jflkdjl", "kfjldkj",
                alerts
            )
        val riderAlerts =
            RiderAlerts(
                "KK",
                "LPD",
                rtdAlert
            )
        val alertData =
            RTDAlertData(
                riderAlerts
            )

        //Truth.assertThat(alerts.)
        Truth.assertThat(alertData.data.type).isEqualTo("KK")
        Truth.assertThat(alertData.data.id).isEqualTo("LPD")

        Truth.assertThat(alertData.data.attributes.routeID).isEqualTo(routeID)
        Truth.assertThat(alertData.data.attributes.routeName).isEqualTo("kjfldjfl")
        Truth.assertThat(alertData.data.attributes.displayName).isEqualTo("jflkdjl")
        Truth.assertThat(alertData.data.attributes.routeType).isEqualTo("kfjldkj")
        Truth.assertThat(alertData.data.attributes.alerts).isEqualTo(alerts)
        Truth.assertThat((alertData.data.attributes.alerts[0].category)).isEqualTo("abc")
        Truth.assertThat((alertData.data.attributes.alerts[0].endDate)).isEqualTo("ppp")

    }
}