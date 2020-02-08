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

//https://developer.android.com/guide/components/services
class RiderAlertService : Service() {
    private var serviceLooper: Looper? = null
    private var serviceHandler: ServiceHandler? = null
    private val url = "https://www.rtd-denver.com/api/rider-alerts/routes/" //C


    private inner class ServiceHandler(looper: Looper) : Handler(looper) {
        override fun handleMessage(msg: Message?) {
            //super.handleMessage(msg)
            Log.v("FML", "in runnable")
            Process.setThreadPriority(Process.THREAD_PRIORITY_BACKGROUND)
            val cDao = RTDDatabase.invoke(applicationContext).calendarDao()
            val routeDao = RTDDatabase.invoke(applicationContext).routeDao()
            val trainRoutes = routeDao.getTrainRoutes()

            for(trainRoute in trainRoutes) {
                downloadRTDAlerts(trainRoute.name)
            }

            stopSelf(msg!!.arg1)
        }
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

    fun downloadRTDAlerts(route: String) {
        val newUrl = url + route

        // https://developer.android.com/training/volley/simple.html
        // https://tutorial.eyehunts.com/android/volley-android-example-json-parsing-kotlin/

        val requestQueue = Volley.newRequestQueue(this)

        val alertsRequest = GsonRequest(newUrl, RTDAlertData::class.java, null, Response.Listener {
            response -> processAlerts(response)
        },
            Response.ErrorListener { error -> Log.v("DL ERROR", error.toString()) }
        )
        requestQueue.add(alertsRequest)
    }

    private fun processAlerts(response:RTDAlertData) {
        val stopTimeDao = RTDDatabase.invoke(applicationContext).stopTimeDao()
        val cancelDao = RTDDatabase.invoke(applicationContext).cancelledTripDao()

        for (trains in response.data.attributes.alerts) {
            try {
                val simpleDateFormat = SimpleDateFormat("MMMM dd, yyyy hh:mmaa", Locale.US)
                val alertStartDate = simpleDateFormat.parse(trains.startDate)
                val cancelledTrains = RiderAlertUtils.toRTDStationTimeObj(trains.info, response.data.id)
                for (cancelled in cancelledTrains) {
                    val cancelledTrip = CancelledTripEntity(tripId=-1)
                    cancelledTrip.tripId = stopTimeDao.getCancelledTrip(dayOfWeek = RiderAlertUtils.getDayOfWeek(alertStartDate),
                        startTime = Date(cancelled.startTime.time-60000),
                        endTime = Date(cancelled.startTime.time+60000),
                        startStation = cancelled.fromStation,
                        routeName = response.data.id)
                    cancelledTrip.dayDate=alertStartDate
                    cancelDao.insertAll(cancelledTrip)
                }
            } catch (e : ParseException) {
                null
            }
        }
    }


}