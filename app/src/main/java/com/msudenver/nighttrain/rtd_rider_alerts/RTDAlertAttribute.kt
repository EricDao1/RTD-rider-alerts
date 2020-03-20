package com.msudenver.nighttrain.rtd_rider_alerts

import java.lang.StringBuilder

class RTDAlertAttribute(
    var routeID: String = "",
    var routeName: String = "",
    var displayName: String = "",
    var routeType: String = "",
    var alerts: List<RTDAlert>
)