package com.perusudroid.galleryapp.viewmodel

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.ViewModel
import com.perusudroid.core.extensions.toLiveData
import com.perusudroid.core.networking.Outcome
import com.perusudroid.galleryapp.common.GalleryDH
import com.perusudroid.galleryapp.model.GalleryRepository
import io.reactivex.disposables.CompositeDisposable

class GalleryViewModel (val repository: GalleryRepository,
                        val compositeDisposable: CompositeDisposable) : ViewModel(){

    val galleryOutCome: LiveData<Outcome<ArrayList<Any>>> by lazy {
        repository.galleryOutcome.toLiveData(compositeDisposable)
    }

    fun getGalleryPics(){
        if(galleryOutCome.value == null)
            repository.doGetImages()
    }

    override fun onCleared() {
        super.onCleared()
        GalleryDH.destroyGalleryComponent()
        compositeDisposable.clear()
    }
}