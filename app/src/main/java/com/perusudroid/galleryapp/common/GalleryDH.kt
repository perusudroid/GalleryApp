package com.perusudroid.galleryapp.common

import com.perusudroid.core.application.CoreApp
import com.perusudroid.galleryapp.di.DaggerGalleryComponent
import com.perusudroid.galleryapp.di.GalleryComponent
import javax.inject.Singleton

@Singleton
object GalleryDH {

    private var galleryComponent: GalleryComponent? = null

    fun galleryComponent(): GalleryComponent {
        if (galleryComponent == null)
            galleryComponent = DaggerGalleryComponent.builder().coreComponent(CoreApp.coreComponent).build()
        //galleryComponent = null
        return galleryComponent as GalleryComponent
    }

    fun destroyGalleryComponent() {
        galleryComponent = null
    }

}