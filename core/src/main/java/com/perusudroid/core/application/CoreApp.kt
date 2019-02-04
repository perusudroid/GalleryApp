package com.perusudroid.core.application

import android.app.Application
import android.content.Context
import com.perusudroid.core.BuildConfig
import com.perusudroid.core.R
import com.perusudroid.core.di.AppModule
import com.perusudroid.core.di.CoreComponent
import com.perusudroid.core.di.DaggerCoreComponent

/**
 * Created by AndroidDev on 7/16/2018.
 */

open class CoreApp : Application() {

    companion object {
        lateinit var coreComponent: CoreComponent
    }
    private lateinit var context : Application

    override fun onCreate() {
        super.onCreate()
        initDI()
    }


     fun getContext(): Context = context


    private fun initDI() {
        coreComponent = DaggerCoreComponent.builder().appModule(AppModule(this)).build()
    }

}