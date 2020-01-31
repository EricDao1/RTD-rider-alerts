package com.msudenver.nighttrain.rtd_rider_alerts

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.widget.LinearLayout
import android.widget.TextView
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley

class MainActivity : AppCompatActivity() {

    private val url = "https://www.rtd-denver.com/api/rider-alerts/routes/C"
    private val tag = "alertTag"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this, LinearLayout.VERTICAL, false)
        val adapter = TrainTimeAdapter(arrayOf<String>("home", "work", "school"))
        recyclerView.adapter = adapter

        // https://developer.android.com/training/volley/simple.html
        // https://tutorial.eyehunts.com/android/volley-android-example-json-parsing-kotlin/

        /*val requestQueue = Volley.newRequestQueue(this)

        val alertsRequest = GsonRequest(url, RTDAlertData::class.java, null, Response.Listener {
            response -> textView.text =response.toString()
        },
            Response.ErrorListener { error -> textView.text = error.toString() }
        )
        val stringRequest = JsonObjectRequest(Request.Method.GET, url, null, Response.Listener { response ->
                textView.text = response.toString().substring(0, 500)
            },
            Response.ErrorListener { error -> textView.text = "That didn't work!" }
        )
        stringRequest.tag = tag
        requestQueue.add(alertsRequest)*/

    }

    override fun onStop() {
        super.onStop()
        //requestQueue.cancelAll(tag)
    }
}
