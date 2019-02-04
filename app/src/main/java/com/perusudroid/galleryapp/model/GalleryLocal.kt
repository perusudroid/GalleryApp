package com.perusudroid.galleryapp.model

import android.content.Context
import android.database.Cursor
import android.net.Uri
import android.provider.MediaStore
import android.text.TextUtils
import android.text.format.DateFormat
import android.util.Log
import com.perusudroid.galleryapp.response.DateModel
import com.perusudroid.galleryapp.response.GalleryModel
import com.perusudroid.galleryapp.response.ImageModel
import io.reactivex.Flowable
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.LinkedHashMap

class GalleryLocal(val context: Context) : GalleryDataContract.Local {

    override fun getLocalImages(): Flowable<MutableList<Any>> {

        val creppyList: Flowable<MutableList<Any>>?

        val consolidatedList: MutableList<Any> = ArrayList()

        var displayName: String?
        var absolutePathOfImage: String?
        var dateTaken: String?

        val galleryData: ArrayList<GalleryModel>? = ArrayList()

        val uri: Uri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI
        val cursor: Cursor?

        val projection = arrayOf(MediaStore.MediaColumns.DATA, MediaStore.Images.Media.BUCKET_DISPLAY_NAME, MediaStore.Images.Media.DATE_TAKEN)

        val orderBy = MediaStore.Images.Media.DATE_TAKEN
        cursor = context.contentResolver.query(uri, projection, null, null, "$orderBy DESC")

        if (cursor != null) {

            while (cursor.moveToNext()) {

                absolutePathOfImage = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA))
                displayName = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Images.Media.BUCKET_DISPLAY_NAME))
                dateTaken = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATE_TAKEN))

                galleryData?.add(GalleryModel(absolutePathOfImage, displayName, getConvertedDate(dateTaken)))
            }

            cursor.close()

        }


        val groupedData = getGroupedData(galleryData!!)

        for (key in groupedData.keys) {
            val value = groupedData[key]
            consolidatedList.add(DateModel(key))
            if (value != null) {
                for (i in value.indices) {

                    Log.d("LocalX", "absolutePathOfImage ${value[i].absolutePathOfImage} displayName ${value[i].displayName}")
                    consolidatedList.add(ImageModel(value[i].absolutePathOfImage, value[i].displayName))

                }
            }
        }


        Log.d("Local", "Size ${consolidatedList.size}")


        return Flowable.just(consolidatedList)
    }

    private fun getConvertedDate(time: String): String {

        if (!TextUtils.isEmpty(time)) {
            val cal = Calendar.getInstance(Locale.ENGLISH)
            cal.timeInMillis = java.lang.Long.parseLong(time)
            return DateFormat.format("dd-MM-yyyy", cal).toString()
        }

        return ""

    }


    private fun getGroupedData(realPicsData: List<GalleryModel>): HashMap<String, List<GalleryModel>> {
        val groupedHashMap = LinkedHashMap<String, List<GalleryModel>>()

        for (galleryModel in realPicsData) {

            val hashMapKey = galleryModel.dateTaken

            if (groupedHashMap.containsKey(hashMapKey)) {
                (groupedHashMap[hashMapKey] as MutableList).add(GalleryModel(galleryModel.absolutePathOfImage, galleryModel.dateTaken, galleryModel.displayName))
            } else {
                // The key is not there in the HashMap; create a new key-value pair
                val list = java.util.ArrayList<GalleryModel>()
                list.add(GalleryModel(galleryModel.absolutePathOfImage, galleryModel.dateTaken, galleryModel.displayName))
                groupedHashMap[hashMapKey] = list
            }
        }

        return groupedHashMap
    }

}