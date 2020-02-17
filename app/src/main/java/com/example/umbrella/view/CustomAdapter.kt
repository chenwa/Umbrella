package com.example.umbrella.view

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.umbrella.R
import com.example.umbrella.model.DataWeather
import com.squareup.picasso.Picasso
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*


class CustomAdapter(val dataSet : List<DataWeather>) :
    RecyclerView.Adapter<CustomAdapter.CustomViewHolder>() {


    // Find lowest and highest temps
    var highestIndex : Int = 0
    var lowestIndex : Int = 0

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolder {
        // This only works for when we place 8 for Today and Tomorrow
        for (i in 1..7) {
            if (dataSet[i].main.temp.toFloat() > dataSet[highestIndex].main.temp.toFloat()) {
                highestIndex = i
            }
            if (dataSet[i].main.temp.toFloat() < dataSet[lowestIndex].main.temp.toFloat()) {
                lowestIndex = i
            }
        }

        var viewHolder = CustomViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(
                    R.layout.item_layout,
                    parent,
                    false
                )
        )
        return viewHolder
    }

    override fun getItemCount(): Int = dataSet.size


    override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {
        holder.onBind(dataSet[position], position, highestIndex, lowestIndex)
    }

    class CustomViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView) {
        val tvTime : TextView = itemView.findViewById(R.id.tv_time)
        val tvTemp : TextView = itemView.findViewById(R.id.tv_temp)
        val ivWeather : ImageView = itemView.findViewById(R.id.iv_weather)

        fun onBind(data : DataWeather, position: Int, highestIndex: Int, lowestIndex: Int){
            // Build icon url
            val baseUrl = "http://openweathermap.org/img/wn/"
            val endUrl = "@2x.png"
            val iconUrl = baseUrl + data.weather[0].icon + endUrl

            // Set temperature to display format
            val temperature = data.main.temp + "°"

            // Set dt_txt to match display format
            val inputFormat: DateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.US)
            val outputFormat: DateFormat = SimpleDateFormat("h:mm a", Locale.US)
            val time = outputFormat.format(inputFormat.parse(data.dt_txt)!!)

            tvTime.text = time
            tvTemp.text = temperature
            Picasso.get().load(iconUrl).into(ivWeather)

            // Set low and high temps
            if (highestIndex == lowestIndex) {
                // Don't tint
            } else if (position == lowestIndex) {
                tvTemp.setTextColor(Color.parseColor("#14A7F4")) // colorCold
                tvTime.setTextColor(Color.parseColor("#14A7F4")) // colorCold
                ivWeather.setColorFilter(Color.parseColor("#14A7F4"))
            } else if (position == highestIndex) {
                tvTemp.setTextColor(Color.parseColor("#FE9900")) // colorCold
                tvTime.setTextColor(Color.parseColor("#FE9900")) // colorCold
                ivWeather.setColorFilter(Color.parseColor("#FE9900"))
            }
        }
    }
}