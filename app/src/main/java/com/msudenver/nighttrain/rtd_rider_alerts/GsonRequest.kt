package com.msudenver.nighttrain.rtd_rider_alerts

import android.util.Log
import androidx.annotation.VisibleForTesting
import com.android.volley.NetworkResponse
import com.android.volley.ParseError
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.HttpHeaderParser
import com.google.gson.Gson
import com.google.gson.JsonSyntaxException
import java.io.UnsupportedEncodingException
import java.nio.charset.Charset

//https://developer.android.com/training/volley/request-custom
class GsonRequest<T>(
    url:String,
    private val clazz: Class<T>,
    private val headers: MutableMap<String, String>?,
    private val listener: Response.Listener<T>,
    errorListener: Response.ErrorListener
) : Request<T>(Method.GET, url, errorListener) {

    override fun getHeaders() : MutableMap<String, String> = headers ?: super.getHeaders()
    override fun deliverResponse(response: T) = listener.onResponse(response)

    override fun parseNetworkResponse(response: NetworkResponse?): Response<T> {
        return implementParseNetworkResponse(response)
    }

    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    fun testDeliverResponse(response: T) = deliverResponse(response)

    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    fun testParseNetworkResponse(response: NetworkResponse?) : Response<T> {
        return parseNetworkResponse(response)
    }

    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    fun implementParseNetworkResponse(response: NetworkResponse?): Response<T> {
        return try {
            val json = String(
                response?.data ?: ByteArray(0),
                Charset.forName(HttpHeaderParser.parseCharset(response?.headers))
            )
            Response.success(
                Gson().fromJson(json, clazz),
                HttpHeaderParser.parseCacheHeaders(response)
            )
        } catch (e: JsonSyntaxException) {
            Response.error(ParseError(e))
        }
    }

}