package com.msudenver.nighttrain.rtd_rider_alerts

class RTDAlert(
    var category: String = "",
    var info: String = "",
    var startDate: String = "",
    var endDate: String = ""
) {
    override fun toString() : String {
        return "Info" + info + "startDate: " + startDate + "endDate: " + endDate
    }

    /*fun parseStations() : List<RTDStationTimeAlert> {
        val stations : List<String> = info.split("<br>")
    }*/
}