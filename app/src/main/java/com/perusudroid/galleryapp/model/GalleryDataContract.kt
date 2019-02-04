package com.perusudroid.galleryapp.model

import io.reactivex.Single
import io.reactivex.subjects.PublishSubject
import com.perusudroid.core.networking.Outcome
import io.reactivex.Flowable
import io.reactivex.Observable

interface GalleryDataContract {

    interface Repository{
        val galleryOutcome : PublishSubject<Outcome<ArrayList<Any>>>
        fun doGetImages()
        fun handleEmpty()
    }

    interface Local{
        fun getLocalImages() : Flowable<MutableList<Any>>
    }

}