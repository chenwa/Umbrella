package com.example.umbrella

import com.example.umbrella.model.TimeConverter
import org.junit.Assert.assertEquals
import org.junit.Test
import java.text.SimpleDateFormat
import java.util.*




/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class TimeConverterUnitTest {

    @Test
    fun isToday_isCorrect() {
        val pattern = "yyyy-MM-dd"
        val simpleDateFormat = SimpleDateFormat(pattern)
        val date: String = simpleDateFormat.format(Date())

        val time = TimeConverter(date)
        assertEquals(true, time.isToday())
    }

    @Test
    fun isToday_isWrong() {
        var dt: Date = Date()
        val c: Calendar = Calendar.getInstance()
        c.setTime(dt)
        c.add(Calendar.DATE, 1)
        dt = c.getTime()

        val pattern = "yyyy-MM-dd"
        val simpleDateFormat = SimpleDateFormat(pattern)

        val date: String = simpleDateFormat.format(dt)

        val time = TimeConverter(date)
        assertEquals(false, time.isToday())
    }


}

