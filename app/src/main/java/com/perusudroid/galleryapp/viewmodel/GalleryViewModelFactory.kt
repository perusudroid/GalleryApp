package com.perusudroid.galleryapp.viewmodel

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import com.perusudroid.galleryapp.model.GalleryRepository
import io.reactivex.disposables.CompositeDisposable

class GalleryViewModelFactory(val repository: GalleryRepository,
                              val compositeDisposable: CompositeDisposable)
                                : ViewModelProvider.Factory{
    override fun <T : ViewModel?> create(modelClass: Class<T>): T = GalleryViewModel(repository,compositeDisposable) as T
}