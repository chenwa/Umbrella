package com.example.umbrella.view

import android.app.Application
import android.content.Context

class CustomApplication : Application(){
    companion object{
        private var myCustomApplicaiton : Context? = null

        fun getApplication(): Context{
            return myCustomApplicaiton!!
        }
    }

    override fun onCreate() {
        super.onCreate()
    }

}