package com.mmfsin.grisaceo.base

import android.app.Application
import androidx.appcompat.app.AppCompatDelegate
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class App : Application() {

    override fun onCreate() {
        super.onCreate()

//        getFCMToken()
        disableNightMode()
    }

    private fun getFCMToken() {
//        FirebaseMessaging.getInstance().token.addOnCompleteListener {
//            if (it.isSuccessful) Log.i("**** FCM **** ", it.result)
//            else Log.i("FCM", "no token")
//        }
    }

    private fun disableNightMode() =
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
}