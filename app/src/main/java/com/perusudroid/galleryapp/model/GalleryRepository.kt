package com.perusudroid.galleryapp.model

import android.util.Log
import com.perusudroid.core.extensions.performOnBackOutOnMain
import com.perusudroid.core.extensions.success
import com.perusudroid.core.networking.MyScheduler
import com.perusudroid.core.networking.Outcome
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.functions.Consumer
import io.reactivex.subjects.PublishSubject
import java.util.*

class GalleryRepository(val local: GalleryDataContract.Local,
                        val myScheduler: MyScheduler,
                        val compositeDisposable: CompositeDisposable
) : GalleryDataContract.Repository {

    override val galleryOutcome: PublishSubject<Outcome<ArrayList<Any>>> = PublishSubject.create<Outcome<ArrayList<Any>>>()

    override fun doGetImages() {
        local.getLocalImages()
                .performOnBackOutOnMain(myScheduler)
                .subscribe(
                        {
                            handleSuccess(it)
                        },
                        { error ->
                            handleError(error)
                        }
                )
    }

    private fun handleSuccess(it: MutableList<Any>?) {
        Log.e("GalleryRepo", "Success ${it?.size}")
        galleryOutcome.success(it as ArrayList<Any>)
    }

    private fun handleError(error: Throwable?) {
        Log.e("GalleryRepo", "Error")
    }

    override fun handleEmpty() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }


}