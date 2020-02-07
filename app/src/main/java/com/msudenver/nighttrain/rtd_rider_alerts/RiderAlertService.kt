package com.msudenver.nighttrain.rtd_rider_alerts

import android.app.Service
import android.content.Intent
import android.os.*
import android.util.Log
import com.msudenver.nighttrain.rtd_rider_alerts.db.CalendarEntity
import com.msudenver.nighttrain.rtd_rider_alerts.db.RTDDatabase
import com.msudenver.nighttrain.rtd_rider_alerts.db.RouteDao
import com.msudenver.nighttrain.rtd_rider_alerts.db.RouteEntity

//https://developer.android.com/guide/components/services
class RiderAlertService : Service() {
    private var serviceLooper: Looper? = null
    private var serviceHandler: ServiceHandler? = null

    private inner class ServiceHandler(looper: Looper) : Handler(looper) {
        override fun handleMessage(msg: Message?) {
            //super.handleMessage(msg)
            Log.v("FML", "in runnable")
            Process.setThreadPriority(Process.THREAD_PRIORITY_BACKGROUND)
            val cDao = RTDDatabase.invoke(applicationContext).calendarDao()
            //val routeDao = RTDDatabase.invoke(applicationContext).routeDao()
            //insertStations(routeDao)
            val calendar0 = CalendarEntity(id="DPSWK", monday = 1, tuesday = 1, wednesday = 1, thursday = 1, friday = 1, saturday = 0, sunday = 0)
            val calendar1 = CalendarEntity(id="FR", monday = 0, tuesday = 0, wednesday = 0, thursday = 0, friday = 1, saturday = 0, sunday = 0)
            val calendar2 = CalendarEntity(id="MT", monday = 1, tuesday = 1, wednesday = 1, thursday = 1, friday = 0, saturday = 0, sunday = 0)
            val calendar3 = CalendarEntity(id="SA", monday = 0, tuesday = 0, wednesday = 0, thursday = 0, friday = 0, saturday = 1, sunday = 0)
            val calendar4 = CalendarEntity(id="SU", monday = 0, tuesday = 0, wednesday = 0, thursday = 0, friday = 0, saturday = 0, sunday = 1)
            val calendar5 = CalendarEntity(id="WK", monday = 1, tuesday = 1, wednesday = 1, thursday = 1, friday = 1, saturday = 0, sunday = 0)

            //cDao.insertAll(calendar0,calendar1,calendar2,calendar3,calendar4,calendar5)
            val c = cDao.getAll()

            Log.v("FML",c.toString())
            stopSelf(msg!!.arg1)
        }
    }

    fun insertStations(routeDao: RouteDao) {
        val route1 = RouteEntity(id="0", name="0",type=3, backgroundColor=0x0076CE, textColor=0xFFFFFF)
        val route2 = RouteEntity(id="0L", name="0L",type=3, backgroundColor=0x0076CE, textColor=0xFFFFFF)
        val route3 = RouteEntity(id="1", name="1",type=3, backgroundColor=0x0076CE, textColor=0xFFFFFF)
        val route4 = RouteEntity(id="10", name="10",type=3, backgroundColor=0x0076CE, textColor=0xFFFFFF)
        val route5 = RouteEntity(id="100", name="100",type=3, backgroundColor=0x0076CE, textColor=0xFFFFFF)
        val route6 = RouteEntity(id="101C", name="C",type=0, backgroundColor=0xF79239, textColor=0xFFFFFF)
        val route7 = RouteEntity(id="101D", name="D",type=0, backgroundColor=0x8348, textColor=0xFFFFFF)
        val route8 = RouteEntity(id="101E", name="E",type=0, backgroundColor=0x552683, textColor=0xFFFFFF)
        val route9 = RouteEntity(id="101F", name="F",type=0, backgroundColor=0xEE3E33, textColor=0xFFFFFF)
        val route10 = RouteEntity(id="101H", name="H",type=0, backgroundColor=0x0075BE, textColor=0xFFFFFF)
        val route11 = RouteEntity(id="103W", name="W",type=0, backgroundColor=0x009DAA, textColor=0xFFFFFF)
        val route12 = RouteEntity(id="104", name="104",type=3, backgroundColor=0x0076CE, textColor=0xFFFFFF)
        val route13 = RouteEntity(id="104X", name="104X",type=3, backgroundColor=0x64A70B, textColor=0xFFFFFF)
        val route14 = RouteEntity(id="105", name="105",type=3, backgroundColor=0x0076CE, textColor=0xFFFFFF)
        val route15 = RouteEntity(id="107R", name="R",type=0, backgroundColor=0xC4D600, textColor=0x0)
        val route16 = RouteEntity(id="109L", name="L",type=0, backgroundColor=0xFFCE00, textColor=0x0)
        val route17 = RouteEntity(id="11", name="11",type=3, backgroundColor=0x0076CE, textColor=0xFFFFFF)
        val route18 = RouteEntity(id="112", name="112",type=3, backgroundColor=0x0076CE, textColor=0xFFFFFF)
        val route19 = RouteEntity(id="113B", name="B",type=2, backgroundColor=0x4E9D2D, textColor=0xFFFFFF)
        val route20 = RouteEntity(id="113G", name="G",type=2, backgroundColor=0xF6B221, textColor=0xFFFFFF)
        val route21 = RouteEntity(id="116X", name="116X",type=3, backgroundColor=0x64A70B, textColor=0xFFFFFF)
        val route22 = RouteEntity(id="12", name="12",type=3, backgroundColor=0x0076CE, textColor=0xFFFFFF)
        val route23 = RouteEntity(id="120", name="120",type=3, backgroundColor=0x0076CE, textColor=0xFFFFFF)
        val route24 = RouteEntity(id="120X", name="120X",type=3, backgroundColor=0x64A70B, textColor=0xFFFFFF)
        val route25 = RouteEntity(id="121", name="121",type=3, backgroundColor=0x0076CE, textColor=0xFFFFFF)
        val route26 = RouteEntity(id="122X", name="122X",type=3, backgroundColor=0x64A70B, textColor=0xFFFFFF)
        val route27 = RouteEntity(id="125", name="125",type=3, backgroundColor=0x0076CE, textColor=0xFFFFFF)
        val route28 = RouteEntity(id="128", name="128",type=3, backgroundColor=0x0076CE, textColor=0xFFFFFF)
        val route29 = RouteEntity(id="130", name="130",type=3, backgroundColor=0x0076CE, textColor=0xFFFFFF)
        val route30 = RouteEntity(id="131", name="131",type=3, backgroundColor=0x0076CE, textColor=0xFFFFFF)
        val route31 = RouteEntity(id="133", name="133",type=3, backgroundColor=0x0076CE, textColor=0xFFFFFF)
        val route32 = RouteEntity(id="135", name="135",type=3, backgroundColor=0x0076CE, textColor=0xFFFFFF)
        val route33 = RouteEntity(id="139", name="139",type=3, backgroundColor=0x0076CE, textColor=0xFFFFFF)
        val route34 = RouteEntity(id="14", name="14",type=3, backgroundColor=0x0076CE, textColor=0xFFFFFF)
        val route35 = RouteEntity(id="145X", name="145X",type=3, backgroundColor=0x64A70B, textColor=0xFFFFFF)
        val route36 = RouteEntity(id="15", name="15",type=3, backgroundColor=0x0076CE, textColor=0xFFFFFF)
        val route37 = RouteEntity(id="153", name="153",type=3, backgroundColor=0x0076CE, textColor=0xFFFFFF)
        val route38 = RouteEntity(id="157", name="157",type=3, backgroundColor=0x0076CE, textColor=0xFFFFFF)
        val route39 = RouteEntity(id="15L", name="15L",type=3, backgroundColor=0x0076CE, textColor=0xFFFFFF)
        val route40 = RouteEntity(id="16", name="16",type=3, backgroundColor=0x0076CE, textColor=0xFFFFFF)
        val route41 = RouteEntity(id="169", name="169",type=3, backgroundColor=0x0076CE, textColor=0xFFFFFF)
        val route42 = RouteEntity(id="169L", name="169L",type=3, backgroundColor=0x0076CE, textColor=0xFFFFFF)
        val route43 = RouteEntity(id="16L", name="16L",type=3, backgroundColor=0x0076CE, textColor=0xFFFFFF)
        val route44 = RouteEntity(id="19", name="19",type=3, backgroundColor=0x0076CE, textColor=0xFFFFFF)
        val route45 = RouteEntity(id="1W", name="1W",type=3, backgroundColor=0x0076CE, textColor=0xFFFFFF)
        val route46 = RouteEntity(id="20", name="20",type=3, backgroundColor=0x0076CE, textColor=0xFFFFFF)
        val route47 = RouteEntity(id="204", name="204",type=3, backgroundColor=0x0076CE, textColor=0xFFFFFF)
        val route48 = RouteEntity(id="205", name="205",type=3, backgroundColor=0x0076CE, textColor=0xFFFFFF)
        val route49 = RouteEntity(id="205T", name="205T",type=3, backgroundColor=0x0076CE, textColor=0xFFFFFF)
        val route50 = RouteEntity(id="206", name="206",type=3, backgroundColor=0x0076CE, textColor=0xFFFFFF)
        val route51 = RouteEntity(id="206F", name="206F",type=3, backgroundColor=0x0076CE, textColor=0xFFFFFF)
        val route52 = RouteEntity(id="206S", name="206S",type=3, backgroundColor=0x0076CE, textColor=0xFFFFFF)
        val route53 = RouteEntity(id="208", name="208",type=3, backgroundColor=0x0076CE, textColor=0xFFFFFF)
        val route54 = RouteEntity(id="208F", name="208F",type=3, backgroundColor=0x0076CE, textColor=0xFFFFFF)
        val route55 = RouteEntity(id="209", name="209",type=3, backgroundColor=0x0076CE, textColor=0xFFFFFF)
        val route56 = RouteEntity(id="21", name="21",type=3, backgroundColor=0x0076CE, textColor=0xFFFFFF)
        val route57 = RouteEntity(id="225", name="225",type=3, backgroundColor=0x0076CE, textColor=0xFFFFFF)
        val route58 = RouteEntity(id="225D", name="225D",type=3, backgroundColor=0x0076CE, textColor=0xFFFFFF)
        val route59 = RouteEntity(id="225T", name="225T",type=3, backgroundColor=0x0076CE, textColor=0xFFFFFF)
        val route60 = RouteEntity(id="228", name="228",type=3, backgroundColor=0x0076CE, textColor=0xFFFFFF)
        val route61 = RouteEntity(id="236", name="236",type=3, backgroundColor=0x0076CE, textColor=0xFFFFFF)
        val route62 = RouteEntity(id="24", name="24",type=3, backgroundColor=0x0076CE, textColor=0xFFFFFF)
        val route63 = RouteEntity(id="27", name="27",type=3, backgroundColor=0x0076CE, textColor=0xFFFFFF)
        val route64 = RouteEntity(id="28", name="28",type=3, backgroundColor=0x0076CE, textColor=0xFFFFFF)
        val route65 = RouteEntity(id="29", name="29",type=3, backgroundColor=0x0076CE, textColor=0xFFFFFF)
        val route66 = RouteEntity(id="3", name="3",type=3, backgroundColor=0x0076CE, textColor=0xFFFFFF)
        val route67 = RouteEntity(id="30", name="30",type=3, backgroundColor=0x0076CE, textColor=0xFFFFFF)
        val route68 = RouteEntity(id="30L", name="30L",type=3, backgroundColor=0x0076CE, textColor=0xFFFFFF)
        val route69 = RouteEntity(id="31", name="31",type=3, backgroundColor=0x0076CE, textColor=0xFFFFFF)
        val route70 = RouteEntity(id="32", name="32",type=3, backgroundColor=0x0076CE, textColor=0xFFFFFF)
        val route71 = RouteEntity(id="323", name="323",type=3, backgroundColor=0x0076CE, textColor=0xFFFFFF)
        val route72 = RouteEntity(id="324", name="324",type=3, backgroundColor=0x0076CE, textColor=0xFFFFFF)
        val route73 = RouteEntity(id="326", name="326",type=3, backgroundColor=0x0076CE, textColor=0xFFFFFF)
        val route74 = RouteEntity(id="327", name="327",type=3, backgroundColor=0x0076CE, textColor=0xFFFFFF)
        val route75 = RouteEntity(id="34", name="34",type=3, backgroundColor=0x0076CE, textColor=0xFFFFFF)
        val route76 = RouteEntity(id="35", name="35",type=3, backgroundColor=0x0076CE, textColor=0xFFFFFF)
        val route77 = RouteEntity(id="36", name="36",type=3, backgroundColor=0x0076CE, textColor=0xFFFFFF)
        val route78 = RouteEntity(id="36L", name="36L",type=3, backgroundColor=0x0076CE, textColor=0xFFFFFF)
        val route79 = RouteEntity(id="37", name="37",type=3, backgroundColor=0x0076CE, textColor=0xFFFFFF)
        val route80 = RouteEntity(id="38", name="38",type=3, backgroundColor=0x0076CE, textColor=0xFFFFFF)
        val route81 = RouteEntity(id="39L", name="39L",type=3, backgroundColor=0x0076CE, textColor=0xFFFFFF)
        val route82 = RouteEntity(id="3L", name="3L",type=3, backgroundColor=0x0076CE, textColor=0xFFFFFF)
        val route83 = RouteEntity(id="4", name="4",type=3, backgroundColor=0x0076CE, textColor=0xFFFFFF)
        val route84 = RouteEntity(id="40", name="40",type=3, backgroundColor=0x0076CE, textColor=0xFFFFFF)
        val route85 = RouteEntity(id="401", name="401",type=3, backgroundColor=0x0076CE, textColor=0xFFFFFF)
        val route86 = RouteEntity(id="402L", name="402L",type=3, backgroundColor=0x0076CE, textColor=0xFFFFFF)
        val route87 = RouteEntity(id="403", name="403",type=3, backgroundColor=0x0076CE, textColor=0xFFFFFF)
        val route88 = RouteEntity(id="42", name="42",type=3, backgroundColor=0x0076CE, textColor=0xFFFFFF)
        val route89 = RouteEntity(id="43", name="43",type=3, backgroundColor=0x0076CE, textColor=0xFFFFFF)
        val route90 = RouteEntity(id="44", name="44",type=3, backgroundColor=0x0076CE, textColor=0xFFFFFF)
        val route91 = RouteEntity(id="45", name="45",type=3, backgroundColor=0x0076CE, textColor=0xFFFFFF)
        val route92 = RouteEntity(id="46", name="46",type=3, backgroundColor=0x0076CE, textColor=0xFFFFFF)
        val route93 = RouteEntity(id="48", name="48",type=3, backgroundColor=0x0076CE, textColor=0xFFFFFF)
        val route94 = RouteEntity(id="483", name="483",type=3, backgroundColor=0x0076CE, textColor=0xFFFFFF)
        val route95 = RouteEntity(id="51", name="51",type=3, backgroundColor=0x0076CE, textColor=0xFFFFFF)
        val route96 = RouteEntity(id="52", name="52",type=3, backgroundColor=0x0076CE, textColor=0xFFFFFF)
        val route97 = RouteEntity(id="53", name="53",type=3, backgroundColor=0x0076CE, textColor=0xFFFFFF)
        val route98 = RouteEntity(id="55", name="55",type=3, backgroundColor=0x0076CE, textColor=0xFFFFFF)
        val route99 = RouteEntity(id="59", name="59",type=3, backgroundColor=0x0076CE, textColor=0xFFFFFF)
        val route100 = RouteEntity(id="6", name="6",type=3, backgroundColor=0x0076CE, textColor=0xFFFFFF)
        val route101 = RouteEntity(id="65", name="65",type=3, backgroundColor=0x0076CE, textColor=0xFFFFFF)
        val route102 = RouteEntity(id="66", name="66",type=3, backgroundColor=0x0076CE, textColor=0xFFFFFF)
        val route103 = RouteEntity(id="67", name="67",type=3, backgroundColor=0x0076CE, textColor=0xFFFFFF)
        val route104 = RouteEntity(id="72", name="72",type=3, backgroundColor=0x0076CE, textColor=0xFFFFFF)
        val route105 = RouteEntity(id="72W", name="72W",type=3, backgroundColor=0x0076CE, textColor=0xFFFFFF)
        val route106 = RouteEntity(id="73", name="73",type=3, backgroundColor=0x0076CE, textColor=0xFFFFFF)
        val route107 = RouteEntity(id="76", name="76",type=3, backgroundColor=0x0076CE, textColor=0xFFFFFF)
        val route108 = RouteEntity(id="77", name="77",type=3, backgroundColor=0x0076CE, textColor=0xFFFFFF)
        val route109 = RouteEntity(id="8", name="8",type=3, backgroundColor=0x0076CE, textColor=0xFFFFFF)
        val route110 = RouteEntity(id="80", name="80",type=3, backgroundColor=0x0076CE, textColor=0xFFFFFF)
        val route111 = RouteEntity(id="80L", name="80L",type=3, backgroundColor=0x0076CE, textColor=0xFFFFFF)
        val route112 = RouteEntity(id="83D", name="83D",type=3, backgroundColor=0x0076CE, textColor=0xFFFFFF)
        val route113 = RouteEntity(id="83L", name="83L",type=3, backgroundColor=0x0076CE, textColor=0xFFFFFF)
        val route114 = RouteEntity(id="87L", name="87L",type=3, backgroundColor=0x0076CE, textColor=0xFFFFFF)
        val route115 = RouteEntity(id="88", name="88",type=3, backgroundColor=0x0076CE, textColor=0xFFFFFF)
        val route116 = RouteEntity(id="9", name="9",type=3, backgroundColor=0x0076CE, textColor=0xFFFFFF)
        val route117 = RouteEntity(id="92", name="92",type=3, backgroundColor=0x0076CE, textColor=0xFFFFFF)
        val route118 = RouteEntity(id="99", name="99",type=3, backgroundColor=0x0077CE, textColor=0xFFFFFF)
        val route119 = RouteEntity(id="99L", name="99L",type=3, backgroundColor=0x0076CE, textColor=0xFFFFFF)
        val route120 = RouteEntity(id="A", name="A",type=2, backgroundColor=0x57C1E9, textColor=0xFFFFFF)
        val route121 = RouteEntity(id="AA", name="AA",type=3, backgroundColor=0x57C1E9, textColor=0xFFFFFF)
        val route122 = RouteEntity(id="AB1", name="AB1",type=3, backgroundColor=0x57C1E9, textColor=0xFFFFFF)
        val route123 = RouteEntity(id="AB2", name="AB2",type=3, backgroundColor=0x57C1E9, textColor=0xFFFFFF)
        val route124 = RouteEntity(id="AT", name="AT",type=3, backgroundColor=0x57C1E9, textColor=0xFFFFFF)
        val route125 = RouteEntity(id="ATA", name="ATA",type=3, backgroundColor=0x57C1E9, textColor=0xFFFFFF)
        val route126 = RouteEntity(id="BOLT", name="BOLT",type=3, backgroundColor=0x64A70B, textColor=0xFFFFFF)
        val route127 = RouteEntity(id="BOND", name="BOUND",type=3, backgroundColor=0x0076CE, textColor=0xFFFFFF)
        val route128 = RouteEntity(id="CS", name="CS",type=3, backgroundColor=0x64A70B, textColor=0xFFFFFF)
        val route129 = RouteEntity(id="CV", name="CV",type=3, backgroundColor=0x64A70B, textColor=0xFFFFFF)
        val route130 = RouteEntity(id="DASH", name="DASH",type=3, backgroundColor=0x0076CE, textColor=0xFFFFFF)
        val route131 = RouteEntity(id="ES", name="ES",type=3, backgroundColor=0x64A70B, textColor=0xFFFFFF)
        val route132 = RouteEntity(id="EV", name="EV",type=3, backgroundColor=0x64A70B, textColor=0xFFFFFF)
        val route133 = RouteEntity(id="EX", name="EX",type=3, backgroundColor=0x64A70B, textColor=0xFFFFFF)
        val route134 = RouteEntity(id="FF1", name="FF1",type=3, backgroundColor=0x5798, textColor=0xFFFFFF)
        val route135 = RouteEntity(id="FF2", name="FF2",type=3, backgroundColor=0x5798, textColor=0xFFFFFF)
        val route136 = RouteEntity(id="FF3", name="FF3",type=3, backgroundColor=0x5798, textColor=0xFFFFFF)
        val route137 = RouteEntity(id="FF4", name="FF4",type=3, backgroundColor=0x5798, textColor=0xFFFFFF)
        val route138 = RouteEntity(id="FF5", name="FF5",type=3, backgroundColor=0x5798, textColor=0xFFFFFF)
        val route139 = RouteEntity(id="FF6", name="FF6",type=3, backgroundColor=0x5798, textColor=0xFFFFFF)
        val route140 = RouteEntity(id="FF7", name="FF7",type=3, backgroundColor=0x5798, textColor=0xFFFFFF)
        val route141 = RouteEntity(id="FMR", name="METRORIDE",type=3, backgroundColor=0x00A1A5, textColor=0xFFFFFF)
        val route142 = RouteEntity(id="GS", name="GS",type=3, backgroundColor=0x64A70B, textColor=0xFFFFFF)
        val route143 = RouteEntity(id="J", name="J",type=3, backgroundColor=0x64A70B, textColor=0xFFFFFF)
        val route144 = RouteEntity(id="JUMP", name="JUMP",type=3, backgroundColor=0x0076CE, textColor=0xFFFFFF)
        val route145 = RouteEntity(id="LD1", name="LD1",type=3, backgroundColor=0x64A70B, textColor=0xFFFFFF)
        val route146 = RouteEntity(id="LD2", name="LD2",type=3, backgroundColor=0x64A70B, textColor=0xFFFFFF)
        val route147 = RouteEntity(id="LD3", name="LD3",type=3, backgroundColor=0x64A70B, textColor=0xFFFFFF)
        val route148 = RouteEntity(id="LX1", name="LX1",type=3, backgroundColor=0x64A70B, textColor=0xFFFFFF)
        val route149 = RouteEntity(id="LX2", name="LX2",type=3, backgroundColor=0x64A70B, textColor=0xFFFFFF)
        val route150 = RouteEntity(id="MALL", name="MALLRIDE",type=3, backgroundColor=0x675DC6, textColor=0xFFFFFF)
        val route151 = RouteEntity(id="NB1", name="NB1",type=3, backgroundColor=0x64A70B, textColor=0xFFFFFF)
        val route152 = RouteEntity(id="NB2", name="NB2",type=3, backgroundColor=0x64A70B, textColor=0xFFFFFF)
        val route153 = RouteEntity(id="P", name="P",type=3, backgroundColor=0x64A70B, textColor=0xFFFFFF)
        val route154 = RouteEntity(id="RC", name="RC",type=3, backgroundColor=0x64A70B, textColor=0xFFFFFF)
        val route155 = RouteEntity(id="RX", name="RX",type=3, backgroundColor=0x64A70B, textColor=0xFFFFFF)
        val route156 = RouteEntity(id="SKIP", name="SKIP",type=3, backgroundColor=0x0076CE, textColor=0xFFFFFF)
        val route157 = RouteEntity(id="TRLY", name="ENWD TROLLEY",type=3, backgroundColor=0xFFFF00, textColor=0x0)
        val route158 = RouteEntity(id="Y", name="Y",type=3, backgroundColor=0x64A70B, textColor=0xFFFFFF)

        routeDao.insertAll(route1,route2,route3,route4,route5,route6,route7,route8,route9,route10,
            route11,route12,route13,route14,route15,route16,route17,route18,route19,route20,
            route21,route22,route23,route24,route25,route26,route27,route28,route29,route30,
            route31,route32,route33,route34,route35,route36,route37,route38,route39,route40,
            route41,route42,route43,route44,route45,route46,route47,route48,route49,route50,
            route51,route52,route53,route54,route55,route56,route57,route58,route59,route60,
            route61,route62,route63,route64,route65,route66,route67,route68,route69,route70,
            route71,route72,route73,route74,route75,route76,route77,route78,route79,route80,
            route81,route82,route83,route84,route85,route86,route87,route88,route89,route90,
            route91,route92,route93,route94,route95,route96,route97,route98,route99,route100,
            route101,route102,route103,route104,route105,route106,route107,route108,route109,route110,
            route111,route112,route113,route114,route115,route116,route117,route118,route119,route120,
            route121,route122,route123,route124,route125,route126,route127,route128,route129,route130,
            route131,route132,route133,route134,route135,route136,route137,route138,route139,route140,
            route141,route142,route143,route144,route145,route146,route147,route148,route149,route150,
            route151,route152,route153,route154,route155,route156,route157,route158)

    }

    override fun onCreate() {
        Log.v("FML", "in onCreate of service")
        HandlerThread("ServiceStartArguments", Process.THREAD_PRIORITY_BACKGROUND).apply {
            start()
            serviceLooper = looper
            serviceHandler = ServiceHandler(looper)
        }
    }

    override fun onBind(intent: Intent): IBinder? {
        // We don't provide binding, so return null
        return null
    }

    override fun onStartCommand(intent: Intent, flags: Int, startId: Int) : Int {
        Log.v("FML", "onStartCommand RTDAlertService")
        serviceHandler?.obtainMessage()?.also {msg ->
            msg.arg1 = startId
            serviceHandler?.sendMessage(msg)
        }
        return START_STICKY
    }


}