package com.human_developing_app.nasa_gallery.apod.data

import android.net.Uri
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale


class ApodRepositoryImpl(
  private val apodApi: ApodApi,
  private val localRepository: ApodLocalRepository
) : ApodRepository {
  companion object {
    const val APOD_DATE_PAGINATION_OFFSET = 432_000_000L
  }

  override suspend fun getApod(date: Date): List<ApodCloudModel> {
    return apodApi.getApod(
      (date - APOD_DATE_PAGINATION_OFFSET).toApodDate(),
      (date + APOD_DATE_PAGINATION_OFFSET).toApodDate()
    )
  }

  override suspend fun getCurrentApod(): List<ApodCloudModel> {
    val date = Date(System.currentTimeMillis())
    return apodApi.getCurrentApod((date - APOD_DATE_PAGINATION_OFFSET).toApodDate())
  }

  override suspend fun getApodFileUri(apodImageUrl: String): Uri {
    val apodImageName = apodImageUrl.substring(apodImageUrl.lastIndexOf('/') + 1)
    return try {
      localRepository.apodPhoto(apodImageName)
    } catch (e: ApodImageNotFound) {
      val downloadedImage = apodApi.downloadApodImage(apodImageUrl)
      if (downloadedImage.isSuccessful) {
        localRepository.savePhoto(apodImageName, downloadedImage.body()!!.bytes())
      } else {
        throw ApodImageDownloadException()
      }
      localRepository.apodPhoto(apodImageName)
    }
  }
}

fun Date.toApodDate(): String {
  return SimpleDateFormat(
    "yyyy-MM-dd", Locale.getDefault()
  ).format(this)
}

operator fun Date.plus(timeOffset: Long): Date {
  return Date(time + timeOffset)
}

operator fun Date.minus(timeOffset: Long): Date {
  return Date(time - timeOffset)
}
