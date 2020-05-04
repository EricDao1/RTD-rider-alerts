package com.msudenver.nighttrain.rtd_rider_alerts.ui

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CompoundButton
import android.widget.Switch
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.msudenver.nighttrain.rtd_rider_alerts.R
import com.msudenver.nighttrain.rtd_rider_alerts.classes.FavoriteStation

class StationPickerAdapter(private var stations : List<FavoriteStation>, val fx : (id : Int, value: Boolean) -> Unit ) : RecyclerView.Adapter<StationPickerAdapter.ViewHolder>() {

    inner class ViewHolder(private val view: View, val fx : (id : Int, value: Boolean) -> Unit ) : RecyclerView.ViewHolder(view) {
        fun bindItems(favoriteStation: FavoriteStation) {
            val stationTextView = view.findViewById<TextView>(R.id.stationTextView)
            stationTextView.text = favoriteStation.stationName

            val stationFavoriteSwitch = view.findViewById<Switch>(R.id.favoriteSwitch)
            stationFavoriteSwitch.isChecked = favoriteStation.isFavorite
            stationFavoriteSwitch.setOnCheckedChangeListener { _: CompoundButton, b: Boolean -> fx(favoriteStation.id, b)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, p1: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.card_favorite_station, parent, false)
        return ViewHolder(view,fx)
    }

    override fun getItemCount(): Int {
        return stations.size
    }

    override fun onBindViewHolder(holder: ViewHolder, p1: Int) {
        holder.bindItems((stations[p1]))
    }
}