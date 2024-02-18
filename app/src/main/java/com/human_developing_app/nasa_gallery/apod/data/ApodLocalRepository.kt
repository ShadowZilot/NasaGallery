package com.human_developing_app.nasa_gallery.apod.data

import android.net.Uri

interface ApodLocalRepository {

  suspend fun savePhoto(photoName: String, file: ByteArray)

  suspend fun apodPhoto(photoName: String): Uri
}