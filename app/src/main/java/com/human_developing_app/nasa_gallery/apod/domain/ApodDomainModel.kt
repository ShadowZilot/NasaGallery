package com.human_developing_app.nasa_gallery.apod.domain

import android.net.Uri
import com.human_developing_app.nasa_gallery.apod.data.ApodCloudModel
import com.human_developing_app.nasa_gallery.base.LCEState
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

data class ApodDomainModel(
  val title: String,
  val description: String,
  val imageUrl: String,
  val dateUi: String,
  val date: Date,
  val shareLce: LCEState
) {

  constructor(apodCloudModel: ApodCloudModel, dateConverter: (date: Date) -> String) : this(
    apodCloudModel.title!!,
    apodCloudModel.explanation!!,
    apodCloudModel.hdurl ?: apodCloudModel.thumbnailUrl!!,
    dateConverter.invoke(apodCloudModel.date.toDate()),
    apodCloudModel.date.toDate(),
    LCEState(isLoading = false, isError = false)
  )

  fun toShareModel(imageUri: Uri) = ApodShareModel(title, description, imageUri)

  fun changeShareLceState(newState: LCEState) = ApodDomainModel(
    title,
    description,
    imageUrl,
    dateUi,
    date,
    newState
  )
}

fun String.toDate(): Date {
  return SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
    .parse(this)!!
}