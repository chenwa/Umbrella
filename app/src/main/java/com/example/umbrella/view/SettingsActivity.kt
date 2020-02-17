package com.example.umbrella.view

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import com.example.umbrella.R
import kotlinx.android.synthetic.main.activity_settings.*

class SettingsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)

        var zip: String = intent.getStringExtra("zip")
        et_zip.setText(zip)

        var unitsString = intent.getStringExtra("units")
        var unitsPosition = 0
        if (unitsString == "imperial") {
            unitsPosition = 0
        } else {
           unitsPosition = 1
        }

        // access the items of the list
        val unitsArray = resources.getStringArray(R.array.units)

        // access the spinner
        val spinner = findViewById<Spinner>(R.id.spinner)
        if (spinner != null) {
            val adapter = ArrayAdapter(this,
                android.R.layout.simple_spinner_item, unitsArray)
            spinner.adapter = adapter

            spinner.setSelection(unitsPosition)

            spinner.onItemSelectedListener = object :
                AdapterView.OnItemSelectedListener {
                override fun onItemSelected(parent: AdapterView<*>,
                                            view: View, position: Int, id: Long) {
                    unitsString = unitsArray[position]
                }

                override fun onNothingSelected(parent: AdapterView<*>) {
                    // write code to perform some action
                }
            }
        }

        btn_save.setOnClickListener{v->
            zip = et_zip.text.toString()

            val resultIntent = Intent();
            resultIntent.putExtra("zip", zip);
            resultIntent.putExtra("units", unitsString)

            setResult(Activity.RESULT_OK, resultIntent);
            finish()
        }
    }
}
