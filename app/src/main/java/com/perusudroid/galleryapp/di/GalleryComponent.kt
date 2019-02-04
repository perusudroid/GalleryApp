package com.perusudroid.galleryapp.di

import com.perusudroid.core.di.CoreComponent
import com.perusudroid.core.networking.MyScheduler
import com.perusudroid.galleryapp.view.GalleryActivity
import dagger.Component

@GalleryScope
@Component(dependencies = [CoreComponent::class], modules = [GalleryModule::class])
interface GalleryComponent {

    fun myScheduler(): MyScheduler
    fun inject(galleryActivity: GalleryActivity)

}
