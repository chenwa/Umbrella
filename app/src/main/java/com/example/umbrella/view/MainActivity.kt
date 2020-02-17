package com.example.umbrella.view

import android.content.DialogInterface
import android.os.Bundle
import android.text.InputType
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.umbrella.R
import com.example.umbrella.model.DataWeather
import com.example.umbrella.viewmodel.WeatherViewModel
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    var m_zip = MutableLiveData<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        m_zip.setValue("94040") // Set default zip

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
                    recycler_view.layoutManager = LinearLayoutManager(
                        this@MainActivity,
                        LinearLayoutManager.HORIZONTAL,
                        false
                    )
                    recycler_view.adapter = CustomAdapter(t!!)
                })

        weatherViewModel.getWeather(m_zip.value!!, "imperial")

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

        val d = builder.show()
        // TODO: Enforce that it fits the requirement of a zipcode
        d.getButton(AlertDialog.BUTTON_POSITIVE).isEnabled = true

        // register an observer to be notified on every state change.
        m_zip.observe(this, Observer {
            //here you should bind state to view
            text_view.setText(m_zip.value)

            weatherViewModel.getWeather(m_zip.value!!, "imperial")
        })
    }
}
