package com.msudenver.nighttrain.rtd_rider_alerts

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {

    private val tag = "alertTag"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        val adapter = TrainTimeAdapter(arrayOf<String>("home", "work", "school"))
        recyclerView.adapter = adapter

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
