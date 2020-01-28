package com.msudenver.nighttrain.rtd_rider_alerts

import java.lang.StringBuilder

class RTDAlertAttribute(
    var routeID: String = "",
    var routeName: String = "",
    var displayName: String = "",
    var routeType: String = "",
    var alerts: List<RTDAlert>
) {

    override fun toString(): String {
        var sb = StringBuilder()

        if (this.alerts != null) {
            for (value in this.alerts) {
                sb.append(value)
                sb.append("\n\n")
            }
        }
        return sb.toString()
    }
}