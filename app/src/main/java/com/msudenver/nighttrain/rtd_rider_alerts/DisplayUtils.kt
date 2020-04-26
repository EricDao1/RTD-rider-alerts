package com.msudenver.nighttrain.rtd_rider_alerts

import android.graphics.Color
import androidx.annotation.VisibleForTesting
import com.msudenver.nighttrain.rtd_rider_alerts.db.ScheduledTrain
import java.text.SimpleDateFormat
import java.util.*

object DisplayUtils {

    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    fun getTrainTime(train : ScheduledTrain) : String {
        val formatter = SimpleDateFormat("hh:mm aa")
        var mtDate = Calendar.getInstance()//7 hours in milliseconds :(

        if(train.cancelledAlert > 0) {
            return "CANCELLED"
        }

        mtDate.timeInMillis = train.time.time + 7*60*60*1000
        return formatter.format(mtDate.time)
    }

    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    fun getTimeColor(train: ScheduledTrain) : Int {
        if(train.cancelledAlert > 0) {
            return Color.RED
        }

        return Color.BLACK
    }
}