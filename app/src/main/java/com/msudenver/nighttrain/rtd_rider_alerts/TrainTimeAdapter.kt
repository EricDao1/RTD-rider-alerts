package com.msudenver.nighttrain.rtd_rider_alerts

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class TrainTimeAdapter (private val myDataset: Array<String>) :
        RecyclerView.Adapter<TrainTimeAdapter.MyViewHolder>() {
    class MyViewHolder(val view: View) : RecyclerView.ViewHolder(view) {

        fun bindItems(user : String)
        {
            val textDestination = view.findViewById<TextView>(R.id.textDestination)
            val textLine = view.findViewById<TextView>(R.id.textLine)
            val textTime = view.findViewById<TextView>(R.id.textTime)

            textDestination.text = user
            textTime.text = "1010pm"
            textLine.text = "F"
            textLine.setBackgroundColor(Color.RED)
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