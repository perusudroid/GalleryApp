package com.perusudroid.core.extensions

import com.perusudroid.core.networking.MyScheduler
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

/**
 * Created by AndroidDev on 7/16/2018.
 */
/**
 * Extension function to subscribe on the background thread and observe on the main thread for a [Completable]
 * */
fun Completable.performOnBackOutOnMain(MyScheduler: MyScheduler): Completable {
    return this.subscribeOn(MyScheduler.io())
            .observeOn(MyScheduler.mainThread())
}

/**
 * Extension function to subscribe on the background thread and observe on the main thread for a [Flowable]
 * */
fun <T> Flowable<T>.performOnBackOutOnMain(MyScheduler: MyScheduler): Flowable<T> {
    return this.subscribeOn(MyScheduler.io())
            .observeOn(MyScheduler.mainThread())
}

/**
 * Extension function to subscribe on the background thread and observe on the main thread  for a [Single]
 * */
fun <T> Single<T>.performOnBackOutOnMain(MyScheduler: MyScheduler): Single<T> {
    return this.subscribeOn(MyScheduler.io())
            .observeOn(MyScheduler.mainThread())
}

/**
 * Extension function to subscribe on the background thread and observe on the main thread for a [Observable]
 * */
fun <T> Observable<T>.performOnBackOutOnMain(MyScheduler: MyScheduler): Observable<T> {
    return this.subscribeOn(MyScheduler.io())
            .observeOn(MyScheduler.mainThread())
}

/**
 * Extension function to add a Disposable to a CompositeDisposable
 */
fun Disposable.addTo(compositeDisposable: CompositeDisposable) {
    compositeDisposable.add(this)
}

/**
 * Extension function to subscribe on the background thread for a Flowable
 * */
fun <T> Flowable<T>.performOnBack(MyScheduler: MyScheduler): Flowable<T> {
    return this.subscribeOn(MyScheduler.io())
}

/**
 * Extension function to subscribe on the background thread for a Completable
 * */
fun Completable.performOnBack(MyScheduler: MyScheduler): Completable {
    return this.subscribeOn(MyScheduler.io())
}

/**
 * Extension function to subscribe on the background thread for a Observable
 * */
fun <T> Observable<T>.performOnBack(MyScheduler: MyScheduler): Observable<T> {
    return this.subscribeOn(MyScheduler.io())
}