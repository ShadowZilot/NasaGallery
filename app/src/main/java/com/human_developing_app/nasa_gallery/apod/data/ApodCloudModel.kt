package com.human_developing_app.nasa_gallery.apod.data

import com.google.gson.annotations.SerializedName

data class ApodCloudModel(
  @SerializedName("code")
  val code: Int?,
  @SerializedName("msg")
  val errorMessage: String?,
  @SerializedName("title")
  val title: String?,
  @SerializedName("explanation")
  val explanation: String?,
  @SerializedName("hdurl")
  val hdurl: String?,
  @SerializedName("thumbnail_url")
  val thumbnailUrl: String?,
  @SerializedName("date")
  val date: String
)