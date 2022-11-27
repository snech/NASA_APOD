package com.example.nasa

import android.app.Application
import android.content.Context
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class NasaApplication : Application() {
    fun getAppContext(): Context {
        return this
    }
}