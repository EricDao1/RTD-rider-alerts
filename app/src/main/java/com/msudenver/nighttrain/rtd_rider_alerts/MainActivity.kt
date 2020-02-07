package com.msudenver.nighttrain.rtd_rider_alerts

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {

    private val url = "https://www.rtd-denver.com/api/rider-alerts/routes/C"
    private val tag = "alertTag"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        val adapter = TrainTimeAdapter(arrayOf<String>("home", "work", "school"))
        recyclerView.adapter = adapter

        // https://developer.android.com/training/volley/simple.html
        // https://tutorial.eyehunts.com/android/volley-android-example-json-parsing-kotlin/

        /*val requestQueue = Volley.newRequestQueue(this)

        val alertsRequest = GsonRequest(url, RTDAlertData::class.java, null, Response.Listener {
            response -> Log.v("DL", RTDStationTimeAlert.toRTDStationTime(response.toString(),"C").map(a -> a.toString()))
        },
            Response.ErrorListener { error -> textView.text = error.toString() }
        )*/

        startService(Intent(this,RiderAlertService::class.java))
        Log.v("FML", "started runnable??")
        /*val stringRequest = JsonObjectRequest(Request.Method.GET, url, null, Response.Listener { response ->
        val stringRequest = JsonObjectRequest(Request.Method.GET, url, null, Response.Listener { response ->
                textView.text = response.toString().substring(0, 500)
            },
            Response.ErrorListener { error -> textView.text = "That didn't work!" }
        )
        stringRequest.tag = tag
        requestQueue.add(alertsRequest)*/

    }
}
