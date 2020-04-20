package com.msudenver.nighttrain.rtd_rider_alerts.ui

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.VisibleForTesting
import androidx.recyclerview.widget.RecyclerView
import com.msudenver.nighttrain.rtd_rider_alerts.R
import com.msudenver.nighttrain.rtd_rider_alerts.db.ScheduledTrain
import java.text.SimpleDateFormat
import java.util.*

class TrainTimeAdapter (
    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    val myDataset: List<ScheduledTrain>
) : RecyclerView.Adapter<TrainTimeAdapter.MyViewHolder>() {
    class MyViewHolder(val view: View) : RecyclerView.ViewHolder(view) {

        fun bindItems(scheduledTrain : ScheduledTrain)
        {
            val textDestination = view.findViewById<TextView>(R.id.textDestination)
            val textLine = view.findViewById<TextView>(R.id.textLine)
            val textTime = view.findViewById<TextView>(R.id.textTime)
            val formatter = SimpleDateFormat("hh:mm aa")
            var mtDate = Calendar.getInstance()
            mtDate.timeInMillis = scheduledTrain.time.time + 7*60*60*1000 //7 hours in milliseconds :(

            textDestination.text = scheduledTrain.tripHeader
            if(scheduledTrain.cancelledAlert > 0) {
                textTime.text = "CANCELLED"
                textTime.setTextColor(Color.RED)
            } else {
                textTime.text = formatter.format(mtDate.time)
                textTime.setTextColor(Color.parseColor(String.format("#424242")))
            }
            textLine.text = scheduledTrain.trainName
            textLine.setBackgroundColor(Color.parseColor(String.format("#%06X", scheduledTrain.routeColor)))
            textLine.setTextColor(Color.parseColor(String.format("#%06X",scheduledTrain.routeTextColor)))
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, p1: Int): MyViewHolder {

        val view = LayoutInflater.from(parent.context).inflate(R.layout.train_time_card, parent, false)
        return MyViewHolder(
            view
        )
    }

    override fun getItemCount(): Int {
       return myDataset.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, p1: Int) {
        holder.bindItems((myDataset[p1]))
    }

}