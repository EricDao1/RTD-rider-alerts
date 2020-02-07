package com.msudenver.nighttrain.rtd_rider_alerts

import java.util.*

class RTDAlert(
    var category: String = "",
    var info: String = "",
    var startDate: String = "",
    var endDate: String = ""
) {
    override fun toString() : String {
        return "Info" + info + "startDate: " + startDate + "endDate: " + endDate
    }

}