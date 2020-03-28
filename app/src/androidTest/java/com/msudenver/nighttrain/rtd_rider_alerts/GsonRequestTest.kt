package com.msudenver.nighttrain.rtd_rider_alerts

import androidx.test.ext.junit.runners.AndroidJUnit4
import com.android.volley.Header
import com.android.volley.NetworkResponse
import com.android.volley.Response
import com.google.common.truth.Truth
import com.google.gson.JsonSyntaxException
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class GsonRequestTest2 {

    @Test
    fun gsonRequestSuccessTest() {
        val responseData = "{\"data\":{\"type\":\"alert-routes\",\"id\":\"C\",\"attributes\":{\"routeId\":\"C\",\"routeName\":\"C\",\"displayName\":\"C Line\",\"routeType\":\"rail\",\"alerts\":[{\"category\":\"Rider Alert\",\"info\":\"C Line Trip 4:41 pm from Littleton / Mineral Ave Station to Union Station Track 11 and 7 other trips cancelled today due to operator shortage.<br><br>Affected trips:<br>4:41 pm from Littleton / Mineral Ave Station to Union Station Track 11<br>5:19 pm from Union Station Track 12 to Littleton / Mineral Ave Station<br>7:26 pm from Littleton / Mineral Ave Station to Union Station Track 11<br>8:04 pm from Union Station Track 12 to Littleton / Mineral Ave Station<br>8:56 pm from Littleton / Mineral Ave Station to Union Station Track 11<br>9:34 pm from Union Station Track 12 to Littleton / Mineral Ave Station<br>10:11 pm from Littleton / Mineral Ave Station to Union Station Track 11<br>10:51 pm from Union Station Track 12 to Littleton / Mineral Ave Station\",\"startDate\":\"March 10, 2020 4:41PM\",\"endDate\":\"March 11, 2020 12:21AM\",\"affectedRoutes\":[]},{\"category\":\"Rider Alert\",\"info\":\"C Line Trip 3:56 pm from Littleton / Mineral Ave Station to Union Station Track 11 and 11 other trips cancelled on Fri Mar 13 due to operator shortage.<br><br>Affected trips:<br>3:56 pm from Littleton / Mineral Ave Station to Union Station Track 11<br>4:34 pm from Union Station Track 12 to Littleton / Mineral Ave Station<br>6:41 pm from Littleton / Mineral Ave Station to Union Station Track 11<br>7:19 pm from Union Station Track 12 to Littleton / Mineral Ave Station<br>8:11 pm from Littleton / Mineral Ave Station to Union Station Track 11<br>8:49 pm from Union Station Track 12 to Littleton / Mineral Ave Station<br>9:41 pm from Littleton / Mineral Ave Station to Union Station Track 11<br>10:21 pm from Union Station Track 12 to Littleton / Mineral Ave Station<br>11:11 pm from Littleton / Mineral Ave Station to Union Station Track 11<br>11:51 pm from Union Station Track 12 to Littleton / Mineral Ave Station<br>12:41 am from Littleton / Mineral Ave Station to Union Station Track 11<br>1:21 am from Union Station Track 12 to Littleton / Mineral Ave Station\",\"startDate\":\"March 13, 2020 3:56PM\",\"endDate\":\"March 14, 2020 2:51AM\",\"affectedRoutes\":[]},{\"category\":\"Rider Alert\",\"info\":\"C Line Trip 6:05 pm from Littleton / Mineral Ave Station to Union Station Track 11 and 9 other trips cancelled on Sat Mar 14 due to operator shortage.<br><br>Affected trips:<br>6:05 pm from Littleton / Mineral Ave Station to Union Station Track 11<br>6:42 pm from Union Station Track 12 to Littleton / Mineral Ave Station<br>7:35 pm from Littleton / Mineral Ave Station to Union Station Track 11<br>8:12 pm from Union Station Track 12 to Littleton / Mineral Ave Station<br>9:11 pm from Littleton / Mineral Ave Station to Union Station Track 11<br>9:42 pm from Union Station Track 12 to Littleton / Mineral Ave Station<br>10:41 pm from Littleton / Mineral Ave Station to Union Station Track 11<br>11:20 pm from Union Station Track 12 to Littleton / Mineral Ave Station<br>12:11 am from Littleton / Mineral Ave Station to Union Station Track 11<br>12:50 am from Union Station Track 12 to Littleton / Mineral Ave Station\",\"startDate\":\"March 14, 2020 6:05PM\",\"endDate\":\"March 15, 2020 2:58AM\",\"affectedRoutes\":[]}]}}}"
        val gsonRequest = gsonRequestResponseHelper()
        val response2 = gsonRequest.testParseNetworkResponse(createNetworkResponse(responseData))
        Truth.assertThat(response2.result.data.id).isEqualTo("C")
    }

    @Test
    fun gsonRequestJsonError() {
        val responseData = "{\"data\"type\":\"alert-routes\",\"id\":\"C\",\"attributes\":{\"routeId\":\"C\",\"routeName\":\"C\",\"displayName\":\"C Line\",\"routeType\":\"rail\",\"alerts\":[{\"category\":\"Rider Alert\",\"info\":\"C Line Trip 4:41 pm from Littleton / Mineral Ave Station to Union Station Track 11 and 7 other trips cancelled today due to operator shortage.<br><br>Affected trips:<br>4:41 pm from Littleton / Mineral Ave Station to Union Station Track 11<br>5:19 pm from Union Station Track 12 to Littleton / Mineral Ave Station<br>7:26 pm from Littleton / Mineral Ave Station to Union Station Track 11<br>8:04 pm from Union Station Track 12 to Littleton / Mineral Ave Station<br>8:56 pm from Littleton / Mineral Ave Station to Union Station Track 11<br>9:34 pm from Union Station Track 12 to Littleton / Mineral Ave Station<br>10:11 pm from Littleton / Mineral Ave Station to Union Station Track 11<br>10:51 pm from Union Station Track 12 to Littleton / Mineral Ave Station\",\"startDate\":\"March 10, 2020 4:41PM\",\"endDate\":\"March 11, 2020 12:21AM\",\"affectedRoutes\":[]},{\"category\":\"Rider Alert\",\"info\":\"C Line Trip 3:56 pm from Littleton / Mineral Ave Station to Union Station Track 11 and 11 other trips cancelled on Fri Mar 13 due to operator shortage.<br><br>Affected trips:<br>3:56 pm from Littleton / Mineral Ave Station to Union Station Track 11<br>4:34 pm from Union Station Track 12 to Littleton / Mineral Ave Station<br>6:41 pm from Littleton / Mineral Ave Station to Union Station Track 11<br>7:19 pm from Union Station Track 12 to Littleton / Mineral Ave Station<br>8:11 pm from Littleton / Mineral Ave Station to Union Station Track 11<br>8:49 pm from Union Station Track 12 to Littleton / Mineral Ave Station<br>9:41 pm from Littleton / Mineral Ave Station to Union Station Track 11<br>10:21 pm from Union Station Track 12 to Littleton / Mineral Ave Station<br>11:11 pm from Littleton / Mineral Ave Station to Union Station Track 11<br>11:51 pm from Union Station Track 12 to Littleton / Mineral Ave Station<br>12:41 am from Littleton / Mineral Ave Station to Union Station Track 11<br>1:21 am from Union Station Track 12 to Littleton / Mineral Ave Station\",\"startDate\":\"March 13, 2020 3:56PM\",\"endDate\":\"March 14, 2020 2:51AM\",\"affectedRoutes\":[]},{\"category\":\"Rider Alert\",\"info\":\"C Line Trip 6:05 pm from Littleton / Mineral Ave Station to Union Station Track 11 and 9 other trips cancelled on Sat Mar 14 due to operator shortage.<br><br>Affected trips:<br>6:05 pm from Littleton / Mineral Ave Station to Union Station Track 11<br>6:42 pm from Union Station Track 12 to Littleton / Mineral Ave Station<br>7:35 pm from Littleton / Mineral Ave Station to Union Station Track 11<br>8:12 pm from Union Station Track 12 to Littleton / Mineral Ave Station<br>9:11 pm from Littleton / Mineral Ave Station to Union Station Track 11<br>9:42 pm from Union Station Track 12 to Littleton / Mineral Ave Station<br>10:41 pm from Littleton / Mineral Ave Station to Union Station Track 11<br>11:20 pm from Union Station Track 12 to Littleton / Mineral Ave Station<br>12:11 am from Littleton / Mineral Ave Station to Union Station Track 11<br>12:50 am from Union Station Track 12 to Littleton / Mineral Ave Station\",\"startDate\":\"March 14, 2020 6:05PM\",\"endDate\":\"March 15, 2020 2:58AM\",\"affectedRoutes\":[]}]}}}"
        val gsonRequest = gsonRequestResponseHelper()
        val response2 = gsonRequest.testParseNetworkResponse(createNetworkResponse(responseData))
        Truth.assertThat(response2.error).hasCauseThat().isInstanceOf(JsonSyntaxException::class.java)
    }

    @Test
    fun testGetHeaders() {
        val gsonRequest = gsonRequestResponseHelper()
        Truth.assertThat(gsonRequest.headers).isEmpty()
    }

    @Test
    fun testDeliverResponse() {
        val requestListener = Response.Listener {
                data : RTDAlertData -> Truth.assertThat(data.data.id).isEqualTo("C")
        }
        val errorListener = Response.ErrorListener {
                error -> Truth.assertThat(error).hasCauseThat().isInstanceOf(Exception::class.java)
        }
        val request =  GsonRequest("abcde", RTDAlertData::class.java,null,requestListener,errorListener)
        val alert1 = RTDAlert("Rider Alert", "C line...", "March 20, 2020 6:41PM","March 21, 2020 2:51AM" )
        val alert2 = RTDAlert("Rider Alert", "C line...2", "now", "later")
        val alertList = arrayListOf<RTDAlert>(alert1, alert2)
        val attribute = RTDAlertAttribute("E", "E","E-line", "train", alertList)
        val data = RiderAlerts("D","C", attribute)
        val mainAlertData = RTDAlertData(data)
        request.testDeliverResponse(mainAlertData)
    }

    @Test
    fun testNonEmptyHeaders() {
        val requestListener = Response.Listener {
                data : RTDAlertData -> Truth.assertThat(data.data.id).isEqualTo("C")
        }
        val errorListener = Response.ErrorListener {
                error -> Truth.assertThat(error).hasCauseThat().isInstanceOf(Exception::class.java)
        }
        val headersTest : MutableMap<String, String> = HashMap<String,String>()
        headersTest["Expires"] = "1213456789"

        val gsonRequest = GsonRequest("abcde", RTDAlertData::class.java,headersTest,requestListener,errorListener)
        Truth.assertThat(gsonRequest.headers["Expires"]).isEqualTo("1213456789")
    }

    @Test
    fun testNullData() {
        val gsonRequest = gsonRequestResponseHelper()
        val headerList = ArrayList<Header>()

        val response2 = gsonRequest.testParseNetworkResponse(NetworkResponse(200,null, true, 1000, headerList))
        Truth.assertThat(response2.isSuccess).isTrue()
    }

    /* Note that this will crash android code -- not sure what the point of a null check is in
    *  demo code if it just crashes anyways
    @Test
    fun testNullResponse() {
        val gsonRequest = gsonRequestResponseHelper()
        val header = Header("Expires","123456789")
        val header2 = Header("header2","value2")
        val headerList = ArrayList<Header>()

        val response2 = gsonRequest.testParseNetworkResponse(NetworkResponse(200,null, true, 1000, null))
        Truth.assertThat(response2.isSuccess).isTrue()
    }

     */

    private fun createNetworkResponse(responseData: String) : NetworkResponse {
        val header = Header("Expires","123456789")
        val header2 = Header("header2","value2")
        val headerList = ArrayList<Header>()

        headerList.add(header)
        headerList.add(header2)
        return NetworkResponse(200,responseData.toByteArray(),true,1000,headerList)
    }

    private fun gsonRequestResponseHelper() : GsonRequest<RTDAlertData> {
        val requestListener = Response.Listener {
                data : RTDAlertData -> Truth.assertThat(data.data.id).isEqualTo("C")
        }
        val errorListener = Response.ErrorListener {
                error -> Truth.assertThat(error).hasCauseThat().isInstanceOf(Exception::class.java)
        }
        return GsonRequest("abcde", RTDAlertData::class.java,null,requestListener,errorListener)
    }
}