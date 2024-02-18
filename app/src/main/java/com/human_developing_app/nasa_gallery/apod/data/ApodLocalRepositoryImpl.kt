package com.human_developing_app.nasa_gallery.apod.data

import android.content.Context
import android.net.Uri
import androidx.core.content.FileProvider
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.File

class ApodLocalRepositoryImpl(
  private val context: Context
) : ApodLocalRepository {
  private val apodDir = File(context.filesDir, "apods").apply {
    if (!exists()) mkdir()
  }

  override suspend fun savePhoto(photoName: String, file: ByteArray) = withContext(Dispatchers.IO) {
    val newApod = File(apodDir, photoName)
    if (!newApod.exists()) newApod.createNewFile()
    newApod.writeBytes(file)
    clearFilesFromApod()
  }

  override suspend fun apodPhoto(photoName: String): Uri {
    val apodImage = File(apodDir, photoName)
    if (apodImage.exists()) {
      return FileProvider.getUriForFile(
        context,
        context.applicationContext.packageName + ".provider",
        apodImage
      )
    } else throw ApodImageNotFound()
  }

  private fun clearFilesFromApod() {
    val apodsFile = apodDir.listFiles()
    val currentTime = System.currentTimeMillis()
    if (!apodsFile.isNullOrEmpty()) {
      for (apod in apodsFile) {
        if (currentTime - apod.lastModified() >= DELETE_TIMEOUT) apod.delete()
      }
    }
  }

  companion object {
    const val DELETE_TIMEOUT = 10_800_000
  }
}