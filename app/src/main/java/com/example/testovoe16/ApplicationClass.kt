package com.example.testovoe16

import android.app.Application
import com.onesignal.OneSignal

const val ONESIGNAL_APP_ID = "42d04bfc-0fec-49c4-a3fc-f56705105d42"

class ApplicationClass : Application() {
    override fun onCreate() {
        super.onCreate()

        // Logging set to help debug issues, remove before releasing your app.
        OneSignal.setLogLevel(OneSignal.LOG_LEVEL.VERBOSE, OneSignal.LOG_LEVEL.NONE)

        // OneSignal Initialization
        OneSignal.initWithContext(this)
        OneSignal.setAppId(ONESIGNAL_APP_ID)
    }
}