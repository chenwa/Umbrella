package com.example.umbrella.view

import android.app.Activity
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.text.InputType
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.example.umbrella.R
import com.example.umbrella.model.DataCurrentWeather
import com.example.umbrella.model.DataWeather
import com.example.umbrella.viewmodel.CurrentWeatherViewModel
import com.example.umbrella.viewmodel.WeatherViewModel
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    var m_zip = MutableLiveData<String>()
    var units = "imperial"
    val SETTINGS_REQUEST = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        m_zip.setValue("94040") // Set default zip Montain View

        // Current weather card view
        val card1ViewModel = ViewModelProvider(
            this,
            object : ViewModelProvider.Factory {
                override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                    return CurrentWeatherViewModel() as T
                }
            }
        ).get(CurrentWeatherViewModel::class.java)

        card1ViewModel.getCurrentWeather()
            .observe(this,
                Observer<DataCurrentWeather> {t ->
                    // Set background color according to temperature
                    if ((t.main.temp.toFloat() < 60.00 && units=="imperial") ||
                        (t.main.temp.toFloat() < 15.55 && units=="metric")) {
                        card_current_weather.setBackgroundColor(getColor(R.color.colorCold))
                    } else {
                        card_current_weather.setBackgroundColor(getColor(R.color.colorWarm))
                    }

                    // Add a degree symbol to the string
                    val temp = t.main.temp + "°"

                    // Set text view values
                    tv_city.text = t.name
                    tv_temp.text = temp
                    tv_weather.text = t.weather[0].main
                })
        card1ViewModel.getCurrentWeather(m_zip.value!!, units)

        // Today's Weather card view
        val weatherViewModel = ViewModelProvider(
            this,
            object : ViewModelProvider.Factory {
                override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                    return WeatherViewModel() as T
                }
            }
        ).get(WeatherViewModel::class.java)

        weatherViewModel.getWeatherList()
            .observe(this,
                Observer<List<DataWeather>> { t ->
                    recycler_view.layoutManager = GridLayoutManager(
                        this@MainActivity,
                        4
                    )
                    recycler_view.adapter = CustomAdapter(t!!)
                })

        weatherViewModel.getWeather(m_zip.value!!, units)

        // Alert Dialog for Zip code

        val builder = AlertDialog.Builder(this)
        builder.setTitle("Enter your Zip:")

        // Set up the input
        val input = EditText(this)

        // Specify the type of input expected; this, for example, sets the input as a password, and will mask the text
        input.inputType = InputType.TYPE_CLASS_TEXT
        builder.setView(input)

        // Set up the buttons
        builder.setPositiveButton("OK",
            DialogInterface.OnClickListener { dialog, which ->
                m_zip.setValue(input.text.toString())
            })

        builder.setNegativeButton("Cancel",
            DialogInterface.OnClickListener {dialog, which ->

            })

        val d = builder.show()
        // TODO: Enforce that it fits the requirement of a zipcode
        d.getButton(AlertDialog.BUTTON_POSITIVE).isEnabled = true

        // register an observer to be notified on every state change.
        m_zip.observe(this, Observer {
            //here you should bind state to view
            //text_view.setText(m_zip.value)
            weatherViewModel.getWeather(m_zip.value!!, units)
            card1ViewModel.getCurrentWeather(m_zip.value!!, units)
        })



        iv_gear.setOnClickListener { v ->
            val intent = Intent(this, SettingsActivity::class.java)
            intent.putExtra("zip", m_zip.value.toString())
            intent.putExtra("units", units)
            startActivityForResult(intent, SETTINGS_REQUEST)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        // Check which request we're responding to
        if (requestCode == SETTINGS_REQUEST) {
            // Make sure the request was successful
            if (resultCode == Activity.RESULT_OK) {
                m_zip.value = data?.getStringExtra("zip")
                units = data?.getStringExtra("units")!!
            }
        }
    }
}
