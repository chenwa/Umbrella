package com.example.umbrella.model

import java.text.SimpleDateFormat
import java.util.*


class TimeConverter (var time: String) {
    // Example time:
    // 2020-02-18 03:00:00

    val sdf = SimpleDateFormat("yyyy-MM-dd") // HH:mm:ss
    val strDate: Date = sdf.parse(time)

    fun isToday() : Boolean {
        println(strDate)
        if (Date().day.toString() == strDate.day.toString()) {
            return true
        }
        return false
    }

    fun isTomorrow() : Boolean {
        if (System.currentTimeMillis() > strDate.getTime()) {
            return true
        }

        return false
    }
}