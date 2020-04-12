package com.msudenver.nighttrain.rtd_rider_alerts.classes

import com.msudenver.nighttrain.rtd_rider_alerts.classes.RTDAlert

class RTDAlertAttribute(
    var routeID: String = "",
    var routeName: String = "",
    var displayName: String = "",
    var routeType: String = "",
    var alerts: List<RTDAlert>
)