package com.msudenver.nighttrain.rtd_rider_alerts

import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

class RTDStationTimeAlert {
    var fromStation : String = ""
    var routeName : String = ""
    var startTime : Date = Date()

    companion object {
        //Function to split large delay into individual strings of delayed trains:
        /*Input :
        "C Line Trip 6:11 pm from Littleton / Mineral Ave Station to Union Station Track 11 and 9 other trips cancelled today due to operator shortage.<br>
        <br>Affected trips:<br>6:11 pm from Littleton / Mineral Ave Station to Union Station Track 11<br>6:49 pm from Union Station Track 12 to Littleton / Mineral Ave Station<br>7:41 pm from Littleton / Mineral Ave Station to Union Station Track 11<br>8:19 pm from Union Station Track 12 to Littleton / Mineral Ave Station<br>9:11 pm from Littleton / Mineral Ave Station to Union Station Track 11<br>9:49 pm from Union Station Track 12 to Littleton / Mineral Ave Station<br>10:41 pm from Littleton / Mineral Ave Station to Union Station Track 11<br>11:21 pm from Union Station Track 12 to Littleton / Mineral Ave Station<br>12:11 am from Littleton / Mineral Ave Station to Union Station Track 11<br>12:51 am from Union Station Track 12 to Littleton / Mineral Ave Station"
        */
        fun toRTDStationTime(info: String, routeName: String): List<RTDStationTimeAlert> {
            val stationsList = ArrayList<RTDStationTimeAlert>()
            val stationsString: List<String> = info.split("Affected trips:")
            if (stationsString.size > 1) {
                val stationsStringList: List<String> = stationsString[1].split("<br>")
                for (i in 1 until stationsStringList.size) {
                    val parsedAlert = parseStationAlert(stationsStringList[i])
                    parsedAlert.routeName = routeName
                    stationsList.add(parsedAlert)
                }
            }
            return stationsList
        }

        //Function to split ONE delayed train string and return info of delayed train that can be passed into DB
        //input: 6:49 pm from Union Station Track 12 to Littleton / Mineral Ave Station
        fun parseStationAlert(info: String): RTDStationTimeAlert {
            val alert = RTDStationTimeAlert()
            val startTimeMore = info.split(" from ")
            try {
                val simpleDateFormat = SimpleDateFormat("hh:mm aa")
                alert.startTime = simpleDateFormat.parse(startTimeMore[0])
            } catch (e : ParseException) {
                null
            }
            if(startTimeMore.size > 1) {
                val fromStationMore = startTimeMore[1].split(" to ")
                val stripTrack = fromStationMore[0].split(" Track")
                alert.fromStation = stripTrack[0]
            }

            return alert

        }
    }
}
