package com.perusudroid.core.networking


import io.reactivex.Scheduler

/**
 * Created by AndroidDev on 7/16/2018.
 */
interface MyScheduler {

    fun mainThread(): Scheduler
    fun io(): Scheduler

}