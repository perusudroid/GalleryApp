package com.perusudroid.core.di

import android.content.Context
import com.perusudroid.core.networking.AppMyScheduler
import com.perusudroid.core.networking.MyScheduler
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/**
 * Created by AndroidDev on 7/16/2018.
 */
@Module
class AppModule(val context: Context) {

    @Provides
    @Singleton
    fun providesContext() : Context{
        return context
    }

    @Provides
    @Singleton
    fun scheduler() : MyScheduler {
        return AppMyScheduler()
    }

}