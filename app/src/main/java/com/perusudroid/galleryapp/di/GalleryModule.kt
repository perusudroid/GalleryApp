package com.perusudroid.galleryapp.di

import android.content.Context
import com.perusudroid.core.networking.MyScheduler
import com.perusudroid.galleryapp.adapter.GalleryAdapter
import com.perusudroid.galleryapp.model.GalleryLocal
import com.perusudroid.galleryapp.model.GalleryRepository
import com.perusudroid.galleryapp.viewmodel.GalleryViewModelFactory
import dagger.Module
import dagger.Provides
import io.reactivex.disposables.CompositeDisposable


@Module
@GalleryScope
class GalleryModule {

    @Provides
    @GalleryScope
    fun galleryAdapter() : GalleryAdapter = GalleryAdapter()

    @Provides
    @GalleryScope
    fun galleryViewModelFactory(repository: GalleryRepository,
                              compositeDisposable: CompositeDisposable): GalleryViewModelFactory {
        return GalleryViewModelFactory(repository, compositeDisposable)
    }

    @Provides
    @GalleryScope
    fun galleryRepo(local: GalleryLocal,
                  myScheduler: MyScheduler,
                  compositeDisposable: CompositeDisposable): GalleryRepository {
        return GalleryRepository(local, myScheduler, compositeDisposable)
    }


    @Provides
    @GalleryScope
    fun galleryLocal(context: Context): GalleryLocal = GalleryLocal(context)

    @Provides
    @GalleryScope
    fun compositeDisposable(): CompositeDisposable = CompositeDisposable()

}