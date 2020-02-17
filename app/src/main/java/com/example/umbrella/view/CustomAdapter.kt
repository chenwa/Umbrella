package com.example.umbrella.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.umbrella.R
import com.example.umbrella.model.DataWeather
import com.squareup.picasso.Picasso
import org.json.JSONObject

class CustomAdapter(val dataSet : List<DataWeather>) :
    RecyclerView.Adapter<CustomAdapter.CustomViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolder =
        CustomViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(
                    R.layout.item_layout,
                    parent,
                    false
                )
        )


    override fun getItemCount(): Int = dataSet.size


    override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {
        holder.onBind(dataSet[position])
    }

    class CustomViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView) {
        val tvTime : TextView = itemView.findViewById(R.id.tv_time)
        val tvtemp : TextView = itemView.findViewById(R.id.tv_temp)
        val ivWeather : ImageView = itemView.findViewById(R.id.iv_weather)

        fun onBind(data : DataWeather){
            // Build icon url
            val baseUrl = "http://openweathermap.org/img/wn/"
            val endUrl = "@2x.png"
            val iconUrl = baseUrl + data.weather[0].icon + endUrl

            tvTime.text = data.dt_txt
            tvtemp.text = data.main.temp
            Picasso.get().load(iconUrl).into(ivWeather)
        }
    }
}