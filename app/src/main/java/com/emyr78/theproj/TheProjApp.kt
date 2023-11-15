package com.emyr78.theproj

import android.app.Application
import android.util.Log
import com.emyr78.theproj.constants.Constants
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class TheProjApp: Application() {
    override fun onCreate() {
        super.onCreate()
        Log.d(Constants.TAG,"App Created")
    }
}