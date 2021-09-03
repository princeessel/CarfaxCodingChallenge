package com.carfax.android

import android.app.Application
import com.carfax.android.api.ApiRepo

class App: Application() {

    override fun onCreate() {
        super.onCreate()

        ApiRepo.init(this)
    }
}