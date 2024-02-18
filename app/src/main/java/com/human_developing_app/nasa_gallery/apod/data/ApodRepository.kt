package com.human_developing_app.nasa_gallery.apod.data

import android.net.Uri
import java.util.Date

interface ApodRepository {

  suspend fun getApod(date: Date): List<ApodCloudModel>

  suspend fun getCurrentApod(): List<ApodCloudModel>

  suspend fun getApodFileUri(apodImageUrl: String): Uri
}