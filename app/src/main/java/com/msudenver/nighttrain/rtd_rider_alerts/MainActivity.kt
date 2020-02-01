package com.msudenver.nighttrain.rtd_rider_alerts

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import androidx.room.Room
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.msudenver.nighttrain.rtd_rider_alerts.db.RTDDatabase

class MainActivity : AppCompatActivity() {

    private val url = "https://www.rtd-denver.com/api/rider-alerts/routes/C"
    private val tag = "alertTag"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val textView = findViewById<TextView>(R.id.textView)

        // https://developer.android.com/training/volley/simple.html
        // https://tutorial.eyehunts.com/android/volley-android-example-json-parsing-kotlin/

        val requestQueue = Volley.newRequestQueue(this)

        val alertsRequest = GsonRequest(url, RTDAlertData::class.java, null, Response.Listener {
            response -> textView.text =response.toString()
        },
            Response.ErrorListener { error -> textView.text = error.toString() }
        )

        val db = RTDDatabase(this)
        /*val stringRequest = JsonObjectRequest(Request.Method.GET, url, null, Response.Listener { response ->
                textView.text = response.toString().substring(0, 500)
            },
            Response.ErrorListener { error -> textView.text = "That didn't work!" }
        )
        stringRequest.tag = tag*/
        requestQueue.add(alertsRequest)

    }

    override fun onStop() {
        super.onStop()
        //requestQueue.cancelAll(tag)
    }
}
