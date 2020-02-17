package com.msudenver.nighttrain.rtd_rider_alerts

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.msudenver.nighttrain.rtd_rider_alerts.db.ScheduledTrain

class TrainTimeAdapter (private val myDataset: List<ScheduledTrain>) :
        RecyclerView.Adapter<TrainTimeAdapter.MyViewHolder>() {
    class MyViewHolder(val view: View) : RecyclerView.ViewHolder(view) {

        fun bindItems(scheduledTrain : ScheduledTrain)
        {
            val textDestination = view.findViewById<TextView>(R.id.textDestination)
            val textLine = view.findViewById<TextView>(R.id.textLine)
            val textTime = view.findViewById<TextView>(R.id.textTime)

            textDestination.text = scheduledTrain.tripHeader
            textTime.text = scheduledTrain.time.toString()
            textLine.text = scheduledTrain.trainName
            textLine.setBackgroundColor(scheduledTrain.routeColor)
            textLine.setTextColor(scheduledTrain.routeTextColor)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, p1: Int): TrainTimeAdapter.MyViewHolder {

        val view = LayoutInflater.from(parent.context).inflate(R.layout.train_time_card, parent, false)
        return MyViewHolder(view)
    }

    override fun getItemCount(): Int {

       return myDataset.size
    }

    override fun onBindViewHolder(holder: TrainTimeAdapter.MyViewHolder, p1: Int) {
        holder.bindItems((myDataset[p1]))
    }

}