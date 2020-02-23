package com.msudenver.nighttrain.rtd_rider_alerts

import android.app.Service
import android.content.Intent
import android.os.*
import android.util.Log
import com.android.volley.Response
import com.android.volley.toolbox.Volley
import com.msudenver.nighttrain.rtd_rider_alerts.db.CancelledTripEntity
import com.msudenver.nighttrain.rtd_rider_alerts.db.RTDDatabase
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*
import kotlinx.coroutines.*


//https://developer.android.com/guide/components/services
class RiderAlertService : Service() {
    private var serviceLooper: Looper? = null
    private var serviceHandler: ServiceHandler? = null
    private val TAG = "riderService"
    private val url = "https://www.rtd-denver.com/api/rider-alerts/routes/" //C


    private inner class ServiceHandler(looper: Looper) : Handler(looper) {
        override fun handleMessage(msg: Message?) {
            //super.handleMessage(msg)
            Process.setThreadPriority(Process.THREAD_PRIORITY_BACKGROUND)
            val db = RTDDatabase.invoke(applicationContext)
            GlobalScope.async {
                val routeDao = db.routeDao()
                val trainRoutes = routeDao.getTrainRoutes()
                for(trainRoute in trainRoutes) {
                    downloadRTDAlerts(trainRoute.name, db)
                }
            }
            Thread.sleep(10000)
            //stopSelf(msg!!.arg1)
        }
    }

    override fun onCreate() {
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
        serviceHandler?.obtainMessage()?.also {msg ->
            msg.arg1 = startId
            serviceHandler?.sendMessage(msg)
        }
        return START_STICKY
    }


    suspend fun downloadRTDAlerts(route: String, db: RTDDatabase) {
        val newUrl = url + route
        // https://developer.android.com/training/volley/simple.html
        // https://tutorial.eyehunts.com/android/volley-android-example-json-parsing-kotlin/

        val requestQueue = Volley.newRequestQueue(applicationContext)

        val alertsRequest = GsonRequest(newUrl, RTDAlertData::class.java, null, Response.Listener {
                response -> GlobalScope.launch {processAlerts(response, db)}
        },
            Response.ErrorListener { error -> Log.v(TAG, "download error $error") }
        )
        requestQueue.add(alertsRequest)
    }

    private suspend fun processAlerts(response:RTDAlertData, db: RTDDatabase?) {
        val stopTimeDao = db?.stopTimeDao()
        val cancelDao = db?.cancelledTripDao()

        for (trains in response.data.attributes.alerts) {
            try {
                val simpleDateFormat = SimpleDateFormat("MMMM dd, yyyy hh:mmaa", Locale.US)
                val alertStartDate = simpleDateFormat.parse(trains.startDate)
                val cancelledTrains = RiderAlertUtils.toRTDStationTimeObj(trains.info, response.data.id)
                for (cancelled in cancelledTrains) {
                   val tripID = stopTimeDao?.getCancelledTrip(dayOfWeek = RiderAlertUtils.getDayOfWeek(alertStartDate),
                            startTime = Date(cancelled.startTime.time-60000),
                            endTime = Date(cancelled.startTime.time+60000),
                            startStation = cancelled.fromStation,
                            routeName = response.data.id)
                    if(tripID!=null) {
                        var cancelledTrip = CancelledTripEntity(
                            tripId = tripID,
                            dayDate = alertStartDate
                        )
                        when (cancelledTrip.tripId > 0) {
                            true -> cancelDao?.insertAll(cancelledTrip)
                            false -> Log.v(TAG, "no trip found: input of: $cancelled")
                        }
                    }
                }
            } catch (e : ParseException) {
                null
            }
        }
    }

}