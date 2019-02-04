package com.perusudroid.core.di

import android.content.Context
import android.content.SharedPreferences
import com.perusudroid.core.networking.MyScheduler
import dagger.Component
import javax.inject.Singleton

/**
 * Created by AndroidDev on 7/16/2018.
 */
@Singleton
@Component(modules = [AppModule::class])
interface CoreComponent {

    fun context(): Context

    fun scheduler(): MyScheduler

//
//    fun preferenceHelper(): PreferenceHelper


}