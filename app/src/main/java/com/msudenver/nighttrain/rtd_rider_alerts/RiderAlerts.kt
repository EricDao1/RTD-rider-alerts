package com.msudenver.nighttrain.rtd_rider_alerts

class RiderAlerts(
    var type: String = "",
    var id: String = "",
    var attributes: RTDAlertAttribute
) {
    override fun toString() : String = id + attributes.toString()
}

